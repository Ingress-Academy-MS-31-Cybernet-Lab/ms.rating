package az.ingress.dao.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Table(name = "prouct_rating_summary")
public class ProductRatingSummary {
    @Id
    @EqualsAndHashCode.Include
    private String productId;
    private Double averageRating;
    private int ratingCount;
}
