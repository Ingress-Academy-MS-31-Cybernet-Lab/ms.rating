package az.ingress.service.concretes;

import az.ingress.dao.entity.ProductRatingSummary;
import az.ingress.dao.entity.Rating;
import az.ingress.dao.repository.RatingRepository;
import az.ingress.dao.repository.RatingSummaryRepository;
import az.ingress.mapper.RatingMapper;
import az.ingress.mapper.RatingSummaryMapper;
import az.ingress.model.enums.RatingStatus;
import az.ingress.model.request.RatingCreateRequest;
import az.ingress.model.response.OrderResponse;
import az.ingress.model.response.RatingCommonResponse;
import az.ingress.model.response.RatingResponse;
import az.ingress.service.abstracts.RatingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public abstract class RatingServiceImpl implements RatingService {

    private final ResourcePatternResolver resourcePatternResolver;
    private RatingRepository ratingRepository;
    private RatingSummaryRepository productRatingSummaryRepository;
    private RatingMapper ratingMapper;
    private ObjectMapper objectMapper;
    private RatingSummaryMapper ratingSummaryMapper;

    @Override
    @Transactional
    public void createRating(RatingCreateRequest ratingCreateRequest) {

        checkOrder(getOrder(ratingCreateRequest.productId, ratingCreateRequest.userId));

        if (ratingRepository.existsRatingByProductIdAndUserIdAndActiveStatus(ratingCreateRequest.productId, ratingCreateRequest.userId, RatingStatus.ACTIVE)) {
            log.error("User: {} Rating already exists:{}", ratingCreateRequest.userId, ratingCreateRequest.productId);
            throw new RuntimeException("Rating already exists");
        }
        ratingRepository.save(ratingMapper.CreateRequestToRating(ratingCreateRequest));


        productRatingSummaryRepository.findById(UUID.fromString(ratingCreateRequest.productId)).
                ifPresentOrElse(value -> {

                            value.setRatingSum(value.getRatingSum() + ratingCreateRequest.rating);
                            value.setRatingCount(value.getRatingCount() + 1);

                            productRatingSummaryRepository.save(value);
                        },
                        () -> {

                            productRatingSummaryRepository.save(
                                    ProductRatingSummary.builder()
                                            .productId(ratingCreateRequest.productId)
                                            .ratingCount(1)
                                            .ratingSum(ratingCreateRequest.rating)
                                            .build());
                        }
                );

        log.info("User: {} Rating has been created for Product: {} ", ratingCreateRequest.userId, ratingCreateRequest.productId);
    }

    @Override
    public RatingResponse getRating(UUID ratingId) {
        Rating foundedRating = getRatingById(ratingId);
        return ratingMapper.RatingToResponse(foundedRating);
    }

    @Override
    @Transactional
    public void deleteRating(UUID ratingId) {
        Rating toBeInActivatedRating = getRatingById(ratingId);
        toBeInActivatedRating.setStatus(RatingStatus.INACTIVE);
        ratingRepository.save(toBeInActivatedRating);

        ProductRatingSummary summary =
                productRatingSummaryRepository.findById(UUID.fromString(toBeInActivatedRating.getProductId()))
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Rating summary not found for product " + toBeInActivatedRating.getProductId()
                                )
                        );


        summary.setRatingSum(
                summary.getRatingSum() - toBeInActivatedRating.getRatingValue()
        );

        summary.setRatingCount(
                summary.getRatingCount() - 1
        );

        productRatingSummaryRepository.save(summary);


        log.info("User: {} Rating has been deleted for Product: {}", toBeInActivatedRating.getUserId(), toBeInActivatedRating.getProductId());
    }

    @Override
    public List<RatingResponse> getProductRatings(UUID productId) {
//        TODO bu da partition istifade elemelisen

        List<Rating> productRatingData = ratingRepository.getRatingByProductId(productId);
        if (productRatingData.isEmpty()) {
            return new ArrayList<>();
        }

        return productRatingData.stream().map(ratingMapper::RatingToResponse).collect(Collectors.toList());

    }



    @Override
    public RatingCommonResponse getProductAverageRating(UUID productId) {

        return productRatingSummaryRepository.findById(productId)
                .map(ratingSummaryMapper::createRatingCommonResponse)
                .orElseGet(() ->
                        RatingCommonResponse.builder()
                                .productId(productId.toString())
                                .averageRating(0.0)
                                .ratingCount(0)
                                .build()
                );
    }





    public Rating getRatingById(UUID ratingId) {
        Rating rating = ratingRepository.findRatingById(ratingId);
        if (rating == null) {
            log.error("User: {} Rating does not exist", ratingId);
            throw new RuntimeException("Rating does not exist");
        }
        return rating;
    }

    public String getOrder(String productId, String userId) {
        return "{\n" +
                "  \"orderId\": \"ORD-123456\",\n" +
                "  \"status\": \"DELIVERED\"\n" +
                "}";

    }

    public void checkOrderStatus(OrderResponse response) {
        if (response == null) {
            log.error("User want to give stars without Order");
            throw new RuntimeException("For Rating you need to have an order");
        } else if (!response.orderStatus.equals("DELIVERED")) {
            log.error("User want to give stars even Product is not delivered");
            throw new RuntimeException("Your Order is not delivered yet");
        }

    }

    public void checkOrder(String jsonResponseFromOrder) {
        OrderResponse orderResponse;
        try {
            orderResponse = objectMapper.readValue(jsonResponseFromOrder, OrderResponse.class);
            checkOrderStatus(orderResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
