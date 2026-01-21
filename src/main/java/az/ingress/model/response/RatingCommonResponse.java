package az.ingress.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public  class RatingCommonResponse {
    public String productId;
//    public String userId;
    public Double averageRating;
    public int ratingCount;
}
