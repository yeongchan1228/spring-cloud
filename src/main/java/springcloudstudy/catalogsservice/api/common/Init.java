package springcloudstudy.catalogsservice.api.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springcloudstudy.catalogsservice.domain.catalog.entity.Catalog;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Init {

    private final CatalogService catalogService;
    
    @PostConstruct
    public void init(){
        catalogService.init();
    }

    @Component
    public static class CatalogService{

        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init(){
            Catalog catalog1 = Catalog.creatTestCatalog()
                    .productId("CATALOG-1")
                    .productName("Berlin")
                    .stock(100)
                    .unitPrice(1500)
                    .createdDate(LocalDate.now())
                    .build();

            Catalog catalog2 = Catalog.creatTestCatalog()
                    .productId("CATALOG-2")
                    .productName("Tokyo")
                    .stock(110)
                    .unitPrice(1000)
                    .createdDate(LocalDate.now())
                    .build();

            Catalog catalog3 = Catalog.creatTestCatalog()
                    .productId("CATALOG-3")
                    .productName("Stockholm")
                    .stock(120)
                    .unitPrice(2000)
                    .createdDate(LocalDate.now())
                    .build();

            em.persist(catalog1);
            em.persist(catalog2);
            em.persist(catalog3);
            em.flush();
            em.clear();
        }
    }
}
