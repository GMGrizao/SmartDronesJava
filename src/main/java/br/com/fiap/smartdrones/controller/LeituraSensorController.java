package br.com.fiap.smartdrones.controller;

import br.com.fiap.smartdrones.dto.LeituraSensorDTO;
import br.com.fiap.smartdrones.filter.LeituraSensorFilter;
import br.com.fiap.smartdrones.model.LeituraSensor;
import br.com.fiap.smartdrones.service.LeituraSensorService;
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
@RequestMapping("/api/leituras")
public class LeituraSensorController {

    @Autowired
    private LeituraSensorService leituraSensorService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<LeituraSensor> createLeituraSensor(@Valid @RequestBody LeituraSensorDTO leituraSensorDTO) {
        LeituraSensor createdLeitura = leituraSensorService.createLeituraSensor(leituraSensorDTO);
        return new ResponseEntity<>(createdLeitura, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Page<LeituraSensor>> getAllLeituras(
            @ParameterObject LeituraSensorFilter filter,
            @ParameterObject @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<LeituraSensor> leituras = leituraSensorService.findAll(filter, pageable);
        return ResponseEntity.ok(leituras);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<LeituraSensor> getLeituraSensorById(@PathVariable Long id) {
        LeituraSensor leitura = leituraSensorService.findLeituraSensorById(id);
        return ResponseEntity.ok(leitura);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<LeituraSensor> updateLeituraSensor(@PathVariable Long id, @Valid @RequestBody LeituraSensorDTO leituraSensorDTO) {
        LeituraSensor updatedLeitura = leituraSensorService.updateLeituraSensor(id, leituraSensorDTO);
        return ResponseEntity.ok(updatedLeitura);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteLeituraSensor(@PathVariable Long id) {
        leituraSensorService.deleteLeituraSensor(id);
        return ResponseEntity.noContent().build();
    }
}