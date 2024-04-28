package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

// BEGIN
@Component
public class ProductSpecification {

    public Specification<Product> build(ProductParamsDTO productParamsDTO) {
        return withTitleCont(productParamsDTO.getTitleCont())
        .and(priceLessThan(productParamsDTO.getPriceLt()))
        .and(priceGreaterThan(productParamsDTO.getPriceGt()))
        .and(ratingGreaterThan(productParamsDTO.getRatingGt()))
        .and(categoryEqual(productParamsDTO.getCategoryId()));
    } 

    private Specification<Product> withTitleCont(String title) {
        return (root, query, cb) -> title == null ? cb.conjunction() : cb.like(cb.lower(root.get("title")), title.toLowerCase());
    }

    private Specification<Product> priceGreaterThan(Integer priceGt) {
        return (root, query, cb) -> priceGt == null ? cb.conjunction() : cb.greaterThan(root.get("price"), priceGt);
    }

    private Specification<Product> priceLessThan(Integer priceLt) {
        return (root, query, cb) -> priceLt == null ? cb.conjunction() : cb.lessThan(root.get("price"), priceLt);
    }

    private Specification<Product> ratingGreaterThan(Double ratingGt) {
        return (root, query, cb) -> ratingGt == null ? cb.conjunction() : cb.greaterThan(root.get("rating"), ratingGt);
    }

    private Specification<Product> categoryEqual(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? cb.conjunction() : cb.equal(root.get("category").get("id"), categoryId);
    }
}
// END
