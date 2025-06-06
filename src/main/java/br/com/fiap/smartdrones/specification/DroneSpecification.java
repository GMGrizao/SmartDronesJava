package br.com.fiap.smartdrones.specification;

import br.com.fiap.smartdrones.filter.DroneFilter;
import br.com.fiap.smartdrones.model.Drone;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class DroneSpecification {

    public static Specification<Drone> byFilter(DroneFilter filter) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (filter.getModelo() != null && !filter.getModelo().isEmpty()) {
                predicate = builder.and(predicate, builder.like(builder.lower(root.get("modelo")), "%" + filter.getModelo().toLowerCase() + "%"));
            }
            if (filter.getStatus() != null && !filter.getStatus().isEmpty()) {
                predicate = builder.and(predicate, builder.equal(builder.lower(root.get("status")), filter.getStatus().toLowerCase()));
            }
            return predicate;
        };
    }
}