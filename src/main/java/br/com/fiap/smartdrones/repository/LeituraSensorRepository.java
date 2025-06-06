package br.com.fiap.smartdrones.repository;

import br.com.fiap.smartdrones.model.LeituraSensor;
import br.com.fiap.smartdrones.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long>, JpaSpecificationExecutor<LeituraSensor> {
    
    List<LeituraSensor> findBySensorId(Long sensorId);
    List<LeituraSensor> findBySensorIdAndTimestampBetween(Long sensorId, Date startDate, Date endDate);
    List<LeituraSensor> findBySensorIdAndTimestampAfter(Long sensorId, Date timestamp);

    Optional<LeituraSensor> findTopBySensorOrderByTimestampDesc(Sensor sensor);

    @Query("SELECT ls FROM LeituraSensor ls JOIN ls.sensor s WHERE s.tipo = ?1 AND ls.timestamp > ?2")
    List<LeituraSensor> findBySensorTipoAndTimestampAfter(String tipoSensor, Date timestamp);
}