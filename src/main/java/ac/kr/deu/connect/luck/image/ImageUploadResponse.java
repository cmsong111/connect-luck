package ac.kr.deu.connect.luck.image;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ImageUploadResponse {
    private Data data;
    private boolean success;
    private int status;

    @Getter
    @Setter
    @ToString
    public static class Data {
        private String id;
        private String title;
        private String urlViewer;
        private String url;
        private String displayUrl;
        private String width;
        private String height;
        private String size;
        private String time;
        private String expiration;
        private ImageInfo image;
        private ImageInfo thumb;
        private ImageInfo medium;
        private String deleteUrl;
    }

    @Getter
    @Setter
    @ToString
    public static class ImageInfo {
        private String filename;
        private String name;
        private String mime;
        private String extension;
        private String url;
    }
}
