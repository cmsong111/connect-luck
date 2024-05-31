package ac.kr.deu.connect.luck.food_truck.controller;

import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckReviewRequest;
import ac.kr.deu.connect.luck.food_truck.service.FoodTruckReviewService;
import ac.kr.deu.connect.luck.food_truck.service.FoodTruckService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/food-truck/{id}/review")
@AllArgsConstructor
public class FoodTruckReviewController {

    private final FoodTruckReviewService foodTruckReviewService;
    private final FoodTruckService foodTruckService;

    @GetMapping
    public String getFoodTruckReviewList(
            @PathVariable(value = "id") Long id,
            Model model
    ) {
        model.addAttribute("foodTruckDetailResponse", foodTruckService.getFoodTruck(id));
        return "food_truck/manager/review_manage";
    }

    @GetMapping("/{reviewId}/reply")
    public String getFoodTruckReviewDetail(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "reviewId") Long reviewId,
            Model model
    ) {
        log.info("reviewId: {}", foodTruckReviewService.getFoodTruckReview(reviewId).getContent());
        model.addAttribute("review", foodTruckReviewService.getFoodTruckReview(reviewId));
        model.addAttribute("foodTruckId", id);
        model.addAttribute("reviewId", reviewId);
        return "food_truck/manager/review_reply_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{reviewId}/reply")
    public String replyFoodTruckReview(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "reviewId") Long reviewId,
            String content
    ) {
        foodTruckReviewService.addReplyToReview(reviewId, content);
        return "redirect:/food-truck/" + id + "/review";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{reviewId}/delete")
    public String deleteFoodTruckReview(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "reviewId") Long reviewId
    ) {
        foodTruckReviewService.deleteFoodTruckReview(reviewId);
        return "redirect:/user";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{reviewId}/edit")
    public String editFoodTruckReview(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "reviewId") Long reviewId,
            Model model
    ) {
        model.addAttribute("review", foodTruckReviewService.getFoodTruckReviewForRequest(reviewId));
        model.addAttribute("foodTruckId", id);
        model.addAttribute("reviewId", reviewId);
        return "food_truck/review_edit_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{reviewId}/edit")
    public String editFoodTruckReview(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "reviewId") Long reviewId,
            FoodTruckReviewRequest foodTruckReview, Principal principal
    ) {
        foodTruckReviewService.saveFoodTruckReview(id, foodTruckReview, principal.getName(), reviewId);
        return "redirect:/user";
    }
}

