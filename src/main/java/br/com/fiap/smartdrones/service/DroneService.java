package br.com.fiap.smartdrones.service;

import br.com.fiap.smartdrones.dto.DroneDTO;
import br.com.fiap.smartdrones.exception.ResourceNotFoundException;
import br.com.fiap.smartdrones.filter.DroneFilter;
import br.com.fiap.smartdrones.model.Drone;
import br.com.fiap.smartdrones.repository.DroneRepository;
import br.com.fiap.smartdrones.specification.DroneSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;

    public Drone createDrone(DroneDTO droneDTO) {
        Drone drone = new Drone();
        drone.setModelo(droneDTO.getModelo());
        drone.setStatus(droneDTO.getStatus());
        return droneRepository.save(drone);
    }

    public Page<Drone> findAll(DroneFilter filter, Pageable pageable) {
        Specification<Drone> spec = DroneSpecification.byFilter(filter);
        return droneRepository.findAll(spec, pageable);
    }

    public Drone findDroneById(Long id) {
        return droneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drone não encontrado com ID: " + id));
    }

    public Drone updateDrone(Long id, DroneDTO droneDTO) {
        Drone drone = findDroneById(id);
        drone.setModelo(droneDTO.getModelo());
        drone.setStatus(droneDTO.getStatus());
        return droneRepository.save(drone);
    }

    public void deleteDrone(Long id) {
        Drone drone = findDroneById(id);
        droneRepository.delete(drone);
    }
}