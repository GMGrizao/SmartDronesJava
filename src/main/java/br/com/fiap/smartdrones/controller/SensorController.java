package br.com.fiap.smartdrones.controller;

import br.com.fiap.smartdrones.dto.SensorDTO;
import br.com.fiap.smartdrones.filter.SensorFilter;
import br.com.fiap.smartdrones.model.Sensor;
import br.com.fiap.smartdrones.service.SensorService;
import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Sensor> createSensor(@Valid @RequestBody SensorDTO sensorDTO) {
        Sensor createdSensor = sensorService.createSensor(sensorDTO);
        return new ResponseEntity<>(createdSensor, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Page<Sensor>> getAllSensors(
            @ParameterObject SensorFilter filter,
            @ParameterObject @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<Sensor> sensors = sensorService.findAll(filter, pageable);
        return ResponseEntity.ok(sensors);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Sensor> getSensorById(@PathVariable Long id) {
        Sensor sensor = sensorService.findSensorById(id);
        return ResponseEntity.ok(sensor);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Sensor> updateSensor(@PathVariable Long id, @Valid @RequestBody SensorDTO sensorDTO) {
        Sensor updatedSensor = sensorService.updateSensor(id, sensorDTO);
        return ResponseEntity.ok(updatedSensor);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }
}