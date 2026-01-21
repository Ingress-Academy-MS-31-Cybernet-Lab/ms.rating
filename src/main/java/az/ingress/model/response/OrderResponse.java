package az.ingress.model.response;

import lombok.Builder;

@Builder
public class OrderResponse {
    public String orderId;
    public String orderStatus;
}
