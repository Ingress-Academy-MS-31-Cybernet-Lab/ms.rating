package az.ingress.mapper;

import az.ingress.dao.entity.Rating;
import az.ingress.model.request.RatingCreateRequest;
import az.ingress.model.response.RatingCommonResponse;
import az.ingress.model.response.RatingResponse;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public enum RatingMapper {
    RATING_MAPPER;


    public Rating CreateRequestToRating(RatingCreateRequest request) {
        return Optional.ofNullable(
                Rating.builder().
                        ratingValue(request.rating).
                        id(UUID.randomUUID()).
                        userId(request.userId).
                        productId(request.productId).
                        createdAt(LocalDateTime.now())
                        .build()

        ).orElseThrow(() -> new RuntimeException("Rating not created"));

    }

    public RatingResponse RatingToResponse(Rating rating) {
        return Optional.ofNullable(
                RatingResponse.builder().
                        userId(rating.getUserId()).
                        productId(rating.getProductId()).
                        lastProccessedTime(Objects.requireNonNullElse(
                                rating.getUpdatedAt(),
                                rating.getCreatedAt())).
                        rating(rating.getRatingValue()).
                        build()

        ).orElseThrow(() -> new RuntimeException("Rating Response not created"));

    }


}
