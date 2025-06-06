package br.com.fiap.smartdrones.repository;

import br.com.fiap.smartdrones.model.Sensor;
import br.com.fiap.smartdrones.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long>, JpaSpecificationExecutor<Sensor> {

    List<Sensor> findByDrone(Drone drone);
}