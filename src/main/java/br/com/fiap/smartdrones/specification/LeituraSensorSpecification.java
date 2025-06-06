package br.com.fiap.smartdrones.specification;

import br.com.fiap.smartdrones.filter.LeituraSensorFilter;
import br.com.fiap.smartdrones.model.LeituraSensor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class LeituraSensorSpecification {

    public static Specification<LeituraSensor> byFilter(LeituraSensorFilter filter) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (filter.getSensorId() != null) {
                predicate = builder.and(predicate, builder.equal(root.get("sensor").get("id"), filter.getSensorId()));
            }
            if (filter.getValorMin() != null) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("valor"), filter.getValorMin()));
            }
            if (filter.getValorMax() != null) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get("valor"), filter.getValorMax()));
            }
            if (filter.getTimestampStart() != null) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("timestamp"), filter.getTimestampStart()));
            }
            if (filter.getTimestampEnd() != null) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get("timestamp"), filter.getTimestampEnd()));
            }
            return predicate;
        };
    }
}