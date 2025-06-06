package br.com.fiap.smartdrones.controller;

import br.com.fiap.smartdrones.dto.DroneDTO;
import br.com.fiap.smartdrones.filter.DroneFilter;
import br.com.fiap.smartdrones.model.Drone;
import br.com.fiap.smartdrones.service.DroneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drones")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping
    public ResponseEntity<Drone> createDrone(@Valid @RequestBody DroneDTO droneDTO) {
        Drone createdDrone = droneService.createDrone(droneDTO);
        return new ResponseEntity<>(createdDrone, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Drone>> getAllDrones(
            DroneFilter filter,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<Drone> drones = droneService.findAll(filter, pageable);
        return ResponseEntity.ok(drones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drone> getDroneById(@PathVariable Long id) {
        Drone drone = droneService.findDroneById(id);
        return ResponseEntity.ok(drone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Drone> updateDrone(@PathVariable Long id, @Valid @RequestBody DroneDTO droneDTO) {
        Drone updatedDrone = droneService.updateDrone(id, droneDTO);
        return ResponseEntity.ok(updatedDrone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrone(@PathVariable Long id) {
        droneService.deleteDrone(id);
        return ResponseEntity.noContent().build();
    }
}