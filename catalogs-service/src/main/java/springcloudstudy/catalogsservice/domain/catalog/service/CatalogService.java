package springcloudstudy.catalogsservice.domain.catalog.service;

import springcloudstudy.catalogsservice.domain.catalog.entity.Catalog;

public interface CatalogService {
    Iterable<Catalog> getAllCatalog();
}
