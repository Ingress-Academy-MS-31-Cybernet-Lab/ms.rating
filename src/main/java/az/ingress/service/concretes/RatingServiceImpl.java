package az.ingress.service.concretes;

import az.ingress.dao.repository.RatingRepository;
import az.ingress.model.request.RatingCreateRequest;
import az.ingress.model.response.RatingResponse;
import az.ingress.service.abstracts.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;


    @Override
    public void createRating(RatingCreateRequest ratingCreateRequest) {

    }

    @Override
    public RatingResponse getRating(UUID ratingId) {
        return null;
    }

    @Override
    public void deleteRating(UUID ratingId) {

    }

    @Override
    public List<RatingResponse> getProductRatings(UUID productId) {
        return List.of();
    }

    @Override
    public List<RatingResponse> getUserRating(UUID productId) {
        return List.of();
    }
}
