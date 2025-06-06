package br.com.fiap.smartdrones.service;

import br.com.fiap.smartdrones.dto.SensorDTO;
import br.com.fiap.smartdrones.exception.ResourceNotFoundException;
import br.com.fiap.smartdrones.filter.SensorFilter;
import br.com.fiap.smartdrones.model.Drone;
import br.com.fiap.smartdrones.model.Sensor;
import br.com.fiap.smartdrones.repository.DroneRepository;
import br.com.fiap.smartdrones.repository.SensorRepository;
import br.com.fiap.smartdrones.specification.SensorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DroneRepository droneRepository;

    public Sensor createSensor(SensorDTO sensorDTO) {
        Drone drone = droneRepository.findById(sensorDTO.getDroneId())
                .orElseThrow(() -> new ResourceNotFoundException("Drone não encontrado com ID: " + sensorDTO.getDroneId()));

        Sensor sensor = new Sensor();
        sensor.setTipo(sensorDTO.getTipo());
        sensor.setDrone(drone);
        return sensorRepository.save(sensor);
    }

    public Page<Sensor> findAll(SensorFilter filter, Pageable pageable) {
        Specification<Sensor> spec = SensorSpecification.byFilter(filter);
        return sensorRepository.findAll(spec, pageable);
    }

    public Sensor findSensorById(Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com ID: " + id));
    }

    public Sensor updateSensor(Long id, SensorDTO sensorDTO) {
        Sensor sensor = findSensorById(id);
        sensor.setTipo(sensorDTO.getTipo());

        if (sensorDTO.getDroneId() != null && !sensor.getDrone().getId().equals(sensorDTO.getDroneId())) {
            Drone newDrone = droneRepository.findById(sensorDTO.getDroneId())
                    .orElseThrow(() -> new ResourceNotFoundException("Novo Drone não encontrado com ID: " + sensorDTO.getDroneId()));
            sensor.setDrone(newDrone);
        }
        return sensorRepository.save(sensor);
    }

    public void deleteSensor(Long id) {
        Sensor sensor = findSensorById(id);
        sensorRepository.delete(sensor);
    }
}