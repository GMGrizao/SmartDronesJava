package br.com.fiap.smartdrones.specification;

import br.com.fiap.smartdrones.filter.SensorFilter;
import br.com.fiap.smartdrones.model.Sensor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class SensorSpecification {

    public static Specification<Sensor> byFilter(SensorFilter filter) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (filter.getTipo() != null && !filter.getTipo().isEmpty()) {
                predicate = builder.and(predicate, builder.like(builder.lower(root.get("tipo")), "%" + filter.getTipo().toLowerCase() + "%"));
            }
            if (filter.getDroneId() != null) {
                predicate = builder.and(predicate, builder.equal(root.get("drone").get("id"), filter.getDroneId()));
            }
            return predicate;
        };
    }
}