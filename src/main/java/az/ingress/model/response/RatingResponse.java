package az.ingress.model.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class RatingResponse {
    public String userId;
    public int rating;
    public String productId;
    public LocalDateTime lastProccessedTime;
}
