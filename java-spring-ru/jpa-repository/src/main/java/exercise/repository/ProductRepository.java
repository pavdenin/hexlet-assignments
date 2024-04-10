package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

import org.springframework.data.domain.Sort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findAllByPriceBetween(Integer priceFrom, Integer priceTo, Sort sort);

    List<Product> findAllByPriceGreaterThanEqual(Integer priceFrom, Sort sort);

    List<Product> findAllByPriceLessThanEqual(Integer priceTo, Sort sort);
    // END
}