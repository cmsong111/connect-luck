package ac.kr.deu.connect.luck.security;

import ac.kr.deu.connect.luck.user.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final MyUserDetails myUserDetails;
    @Value("${security.jwt.token.secret}")
    private String secretKey;
    @Value("${security.jwt.token.expiration}")
    private long validityInMilliseconds;
    @Value("${security.jwt.token.header}")
    private String header;
    @Value("${security.jwt.token.prefix}")
    private String prefix;
    @Value("${security.jwt.token.issuer}")
    private String issuer;
    @Value("${security.jwt.token.audience}")
    private String audience;

    @Autowired
    public JwtTokenProvider(MyUserDetails myUserDetails) {
        this.myUserDetails = myUserDetails;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * 토큰 생성 메서드
     *
     * @param username 사용자 이름
     * @param roles    사용자 권한
     * @return 토큰
     */
    public String createToken(String username, List<UserRole> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).collect(Collectors.toList()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 토큰에서 사용자 이름 추출 메서드
     *
     * @param token 토큰
     * @return 사용자 이름
     */
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }


    /**
     * 토큰에서 인증 정보 추출 메서드
     *
     * @param token 토큰
     * @return 인증 정보
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 헤더에서 토큰 추출 메서드
     *
     * @param req 요청
     * @return 토큰
     */
    public String resolveToken(HttpServletRequest req) {
        // Get Token from Header
        String bearerToken = req.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith(prefix)) {
            return bearerToken.substring(7);
        }
        // Get Token from Cookie
        if (req.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals(header)) {
                String decodedToken = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                if (decodedToken.startsWith(prefix)) {
                    return decodedToken.substring(7);
                }
            }
        }
        return null;
    }

    /**
     * 토큰 유효성 검사 메서드
     *
     * @param token 토큰
     * @return 유효성 여부
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
