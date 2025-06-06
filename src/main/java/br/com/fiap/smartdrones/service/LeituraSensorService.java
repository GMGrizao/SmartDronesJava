package br.com.fiap.smartdrones.service;

import br.com.fiap.smartdrones.dto.LeituraSensorDTO;
import br.com.fiap.smartdrones.exception.ResourceNotFoundException;
import br.com.fiap.smartdrones.filter.LeituraSensorFilter;
import br.com.fiap.smartdrones.model.LeituraSensor;
import br.com.fiap.smartdrones.model.Sensor;
import br.com.fiap.smartdrones.repository.LeituraSensorRepository;
import br.com.fiap.smartdrones.repository.SensorRepository;
import br.com.fiap.smartdrones.specification.LeituraSensorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class LeituraSensorService {

    @Autowired
    private LeituraSensorRepository leituraSensorRepository;

    @Autowired
    private SensorRepository sensorRepository;

    public LeituraSensor createLeituraSensor(LeituraSensorDTO leituraSensorDTO) {
        Sensor sensor = sensorRepository.findById(leituraSensorDTO.getSensorId())
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com ID: " + leituraSensorDTO.getSensorId()));

        LeituraSensor leituraSensor = new LeituraSensor();
        leituraSensor.setSensor(sensor);
        leituraSensor.setValor(leituraSensorDTO.getValor());
        leituraSensor.setTimestamp(leituraSensorDTO.getTimestamp());
        return leituraSensorRepository.save(leituraSensor);
    }

    public Page<LeituraSensor> findAll(LeituraSensorFilter filter, Pageable pageable) {
        Specification<LeituraSensor> spec = LeituraSensorSpecification.byFilter(filter);
        return leituraSensorRepository.findAll(spec, pageable);
    }

    public LeituraSensor findLeituraSensorById(Long id) {
        return leituraSensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leitura de Sensor não encontrada com ID: " + id));
    }

    public LeituraSensor updateLeituraSensor(Long id, LeituraSensorDTO leituraSensorDTO) {
        LeituraSensor leituraSensor = findLeituraSensorById(id);

        if (leituraSensorDTO.getSensorId() != null && !leituraSensor.getSensor().getId().equals(leituraSensorDTO.getSensorId())) {
            Sensor newSensor = sensorRepository.findById(leituraSensorDTO.getSensorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Novo Sensor não encontrado com ID: " + leituraSensorDTO.getSensorId()));
            leituraSensor.setSensor(newSensor);
        }

        leituraSensor.setValor(leituraSensorDTO.getValor());
        leituraSensor.setTimestamp(leituraSensorDTO.getTimestamp());
        return leituraSensorRepository.save(leituraSensor);
    }

    public void deleteLeituraSensor(Long id) {
        LeituraSensor leituraSensor = findLeituraSensorById(id);
        leituraSensorRepository.delete(leituraSensor);
    }
}