package az.ingress.controller;

import az.ingress.model.request.RatingCreateRequest;
import az.ingress.service.abstracts.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


}
