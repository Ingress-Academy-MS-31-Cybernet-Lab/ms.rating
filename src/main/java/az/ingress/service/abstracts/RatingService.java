package az.ingress.service.abstracts;

import az.ingress.model.request.RatingCreateRequest;
import az.ingress.model.response.RatingResponse;

import java.util.List;
import java.util.UUID;

public interface  RatingService {
     void createRating(RatingCreateRequest ratingCreateRequest);

    RatingResponse getRating(UUID ratingId);

    void deleteRating(UUID ratingId);

    List<RatingResponse> getProductRatings(UUID productId);

    List<RatingResponse> getUserRating(UUID productId);
}
