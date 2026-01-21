package az.ingress.dao.repository;

import az.ingress.dao.entity.ProductRatingSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RatingSummaryRepository extends JpaRepository<ProductRatingSummary, UUID> {

    Optional<ProductRatingSummary> findBuId(UUID productId);
}
