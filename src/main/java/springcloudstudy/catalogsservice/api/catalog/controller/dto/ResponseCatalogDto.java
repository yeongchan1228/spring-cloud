package springcloudstudy.catalogsservice.api.catalog.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseCatalogDto implements Serializable {
    private String productId;
    private String productName;
    private Integer unitPrice;
    private Integer stock;
    private LocalDate createdDate;
}
