package springcloudstudy.catalogsservice.api.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springcloudstudy.catalogsservice.api.catalog.controller.dto.ResponseCatalogDto;
import springcloudstudy.catalogsservice.domain.catalog.entity.Catalog;
import springcloudstudy.catalogsservice.domain.catalog.service.CatalogService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog-service")
public class CatalogController {
    private final Environment env;
    private final CatalogService catalogService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return String.format("Working!!! port = %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalogDto>> getAllCatalog(){
        Iterable<Catalog> catalogs = catalogService.getAllCatalog();
        List<ResponseCatalogDto> lists = new ArrayList<>();

        catalogs.forEach(catalog -> {
            lists.add(new ModelMapper().map(catalog, ResponseCatalogDto.class));
        });

        return ResponseEntity.status(200).body(lists);
    }

}
