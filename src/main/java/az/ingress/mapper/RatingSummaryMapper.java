package az.ingress.mapper;

import az.ingress.dao.entity.ProductRatingSummary;
import az.ingress.dao.entity.Rating;
import az.ingress.model.response.RatingCommonResponse;

import java.util.Optional;

public enum RatingSummaryMapper {
    RATING_SUMMARY_MAPPER;


    public  RatingCommonResponse createRatingCommonResponse(ProductRatingSummary summary) {

        return RatingCommonResponse.builder().
        ratingCount(summary.getRatingCount()).
                averageRating(summary.getAverageRating()).
                productId(summary.getProductId()).
                build();


    }


}
