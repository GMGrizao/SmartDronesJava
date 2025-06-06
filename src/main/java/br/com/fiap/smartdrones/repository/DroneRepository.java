package br.com.fiap.smartdrones.repository;

import br.com.fiap.smartdrones.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long>, JpaSpecificationExecutor<Drone> {

    List<Drone> findByStatus(String status);

    List<Drone> findByStatusIn(List<String> statuses);
}