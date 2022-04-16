package springcloudstudy.catalogsservice.domain.catalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springcloudstudy.catalogsservice.domain.catalog.entity.Catalog;
import springcloudstudy.catalogsservice.domain.catalog.repository.CatalogRepository;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService{
    private final CatalogRepository catalogRepository;

    @Override
    public Iterable<Catalog> getAllCatalog() {
        return catalogRepository.findAll();
    }
}
