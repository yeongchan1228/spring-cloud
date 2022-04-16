package springcloudstudy.catalogsservice.domain.catalog.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * implements Serializable -> 클래스를 파일에 읽거나 쓰거나, 다른 서버로 보내거나 받을 수 있게 해준다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
public class Catalog implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String productId;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private Integer unitPrice;

    @CreatedDate
//    @Column(updatable = false, insertable = false) // 원래는 insertable = false를 해야 하지만 테스트용 아이템을 넣기 위해 주석
    @Column(updatable = false)
    private LocalDate createdDate;

    @Builder(builderMethodName = "creatCatalog")
    public Catalog(String productId, String productName, Integer stock, Integer unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.unitPrice = unitPrice;
    }
    @Builder(builderMethodName = "creatTestCatalog")
    public Catalog(String productId, String productName, Integer stock, Integer unitPrice, LocalDate createdDate) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.createdDate = createdDate;
    }
}
