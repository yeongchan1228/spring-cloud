package springcloudstudy.catalogsservice.domain.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springcloudstudy.catalogsservice.domain.catalog.entity.Catalog;

import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    Optional<Catalog> findByProductId(String productId);
}
