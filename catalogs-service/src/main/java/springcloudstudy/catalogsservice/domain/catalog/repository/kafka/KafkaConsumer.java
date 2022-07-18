package springcloudstudy.catalogsservice.domain.catalog.repository.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import springcloudstudy.catalogsservice.domain.catalog.entity.Catalog;
import springcloudstudy.catalogsservice.domain.catalog.repository.CatalogRepository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final CatalogRepository repository;

    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String kafkaMessage) {
        log.info("Kafka Message -> " + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            map = objectMapper.readValue(kafkaMessage, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Catalog entity = repository.findByProductId((String) map.get("productId")).orElse(null);

        if(entity != null) {
            entity.changeStock(entity.getStock() - (Integer) map.get("qty"));
            repository.save(entity);
        }
    }
}
