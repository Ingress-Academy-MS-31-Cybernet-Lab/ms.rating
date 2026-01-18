package az.ingress.service.concretes;

import az.ingress.dao.entity.Rating;
import az.ingress.dao.repository.RatingRepository;
import az.ingress.mapper.RatingMapper;
import az.ingress.model.enums.RatingStatus;
import az.ingress.model.request.RatingCreateRequest;
import az.ingress.model.response.RatingCommonResponse;
import az.ingress.model.response.RatingResponse;
import az.ingress.service.abstracts.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private RatingMapper ratingMapper;

    @Override
    public void createRating(RatingCreateRequest ratingCreateRequest) {

       if(ratingRepository.existsRatingByProductIdAndUserIdAndStatusIs(ratingCreateRequest.productId,ratingCreateRequest.userId, RatingStatus.ACTIVE)){
           log.error("User: {} Rating already exists:{}", ratingCreateRequest.userId, ratingCreateRequest.productId);
           throw new RuntimeException("Rating already exists");
       }
       ratingRepository.save(ratingMapper.CreateRequestToRating(ratingCreateRequest));
       log.info("User: {} Rating has been created for Product: {} ", ratingCreateRequest.userId,ratingCreateRequest.productId);
    }

    @Override
    public RatingResponse getRating(UUID ratingId) {
        Rating rating=ratingRepository.findRatingById(ratingId);
        if(rating==null){
            log.error("User: {} Rating does not exist", ratingId);
            throw new RuntimeException("Rating does not exist");
        }
        return ratingMapper.RatingToResponse(rating);
    }

    @Override
    public void deleteRating(UUID ratingId) {
        RatingResponse rating = getRating(ratingId);

    }

    @Override
    public List<RatingCommonResponse> getProductRatings(UUID productId) {
        return List.of();
    }

    @Override
    public List<RatingCommonResponse> getUserRating(UUID productId) {
        return List.of();
    }
}
