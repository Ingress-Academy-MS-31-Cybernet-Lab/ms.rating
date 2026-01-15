package az.ingress.controller;

import az.ingress.model.request.RatingCreateRequest;
import az.ingress.model.response.RatingResponse;
import az.ingress.service.abstracts.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRating(@RequestBody @Valid RatingCreateRequest ratingCreateRequest) {
        ratingService.createRating(ratingCreateRequest);
    }

    @PutMapping("/{id}")
    public  RatingResponse  getRating(@PathVariable UUID ratingId) {
        return ratingService.getRating(ratingId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRating(@PathVariable UUID ratingId) {
        ratingService.deleteRating(ratingId);
    }

    @GetMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public List<RatingResponse> getProductRatings(@PathVariable UUID productId) {
        return ratingService.getProductRatings( productId);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<RatingResponse> getUserRating(@PathVariable UUID productId) {
        return ratingService.getUserRating( productId);
    }



}
