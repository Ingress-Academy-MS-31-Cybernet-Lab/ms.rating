package az.ingress.dao.repository;

import az.ingress.dao.entity.Rating;
import az.ingress.model.enums.RatingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface  RatingRepository extends JpaRepository<Rating, UUID> {

    boolean existsRatingByProductIdAndUserIdAndActiveStatus(String productId, String userId, RatingStatus status);
    Rating findRatingById(UUID ratingId);

    List<Rating> getRatingByProductId(UUID productId);
}
