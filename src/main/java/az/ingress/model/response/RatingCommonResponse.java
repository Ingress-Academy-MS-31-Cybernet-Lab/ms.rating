package az.ingress.model.response;

import lombok.Builder;

@Builder
public  class RatingCommonResponse {
    public String productId;
    public String userId;
    public Double averageRating;
    public int ratingCount;
}
