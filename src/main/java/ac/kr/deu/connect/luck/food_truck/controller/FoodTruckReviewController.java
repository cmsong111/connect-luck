package ac.kr.deu.connect.luck.food_truck.controller;

import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckReviewRequest;
import ac.kr.deu.connect.luck.food_truck.service.FoodTruckReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/food-truck/{foodTruckId}/review")
@AllArgsConstructor
public class FoodTruckReviewController {

    private final FoodTruckReviewService foodTruckReviewService;

    @GetMapping
    public String getFoodTruckReview(
            @PathVariable("foodTruckId") Long foodTruckId,
            Model model
    ) {
        model.addAttribute("reviewRequestForm", new FoodTruckReviewRequest("", 0, null));
        return "food_truck/food-truck-review-form";
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String postFoodTruckReview(
            @PathVariable("foodTruckId") Long foodTruckId,
            FoodTruckReviewRequest reviewRequest,
            Principal principal
    ) {

        foodTruckReviewService.saveFoodTruckReview(foodTruckId, reviewRequest, principal.getName());
        return "redirect:/food-truck/{foodTruckId}";
    }
}
