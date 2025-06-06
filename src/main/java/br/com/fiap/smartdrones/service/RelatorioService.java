package br.com.fiap.smartdrones.service;

import br.com.fiap.smartdrones.exception.ResourceNotFoundException;
import br.com.fiap.smartdrones.model.LeituraSensor;
import br.com.fiap.smartdrones.model.Sensor;
import br.com.fiap.smartdrones.model.Drone;
import br.com.fiap.smartdrones.repository.LeituraSensorRepository;
import br.com.fiap.smartdrones.repository.SensorRepository;
import br.com.fiap.smartdrones.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    @Autowired
    private LeituraSensorRepository leituraSensorRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DroneRepository droneRepository;

    public Optional<LeituraSensor> getUltimaLeituraSensor(Long sensorId) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com ID: " + sensorId));

        return leituraSensorRepository.findTopBySensorOrderByTimestampDesc(sensor);
    }

    public List<LeituraSensor> identificarAnomaliasSensorNoPeriodo(Long sensorId, Double limiteSuperior, Double limiteInferior, int periodoEmHoras) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com ID: " + sensorId));

        Date dataLimite = new Date(System.currentTimeMillis() - (long) periodoEmHoras * 60 * 60 * 1000);

        List<LeituraSensor> leiturasNoPeriodo = leituraSensorRepository.findBySensorIdAndTimestampAfter(sensorId, dataLimite);

        return leiturasNoPeriodo.stream()
                .filter(leitura -> leitura.getValor() > limiteSuperior || leitura.getValor() < limiteInferior)
                .collect(Collectors.toList());
    }

    public List<Drone> listarDronesInativosOuEmManutencao() {
        return droneRepository.findByStatusIn(List.of("Manutenção", "Desativado"));
    }

    public Map<Long, Map<String, Double>> consolidarLeiturasPorDroneETipoSensor(String tipoSensor, int periodoEmHoras) {
        Date dataLimite = new Date(System.currentTimeMillis() - (long) periodoEmHoras * 60 * 60 * 1000);

        List<LeituraSensor> leiturasRecentes = leituraSensorRepository.findBySensorTipoAndTimestampAfter(tipoSensor, dataLimite);

        return leiturasRecentes.stream()
                .collect(Collectors.groupingBy(
                        leitura -> leitura.getSensor().getDrone().getId(),
                        Collectors.teeing(
                                Collectors.averagingDouble(LeituraSensor::getValor),
                                Collectors.maxBy(Comparator.comparingDouble(LeituraSensor::getValor)),
                                (avg, maxOpt) -> {
                                    Map<String, Double> metrics = new java.util.HashMap<>();
                                    metrics.put("media", avg);
                                    maxOpt.ifPresent(leitura -> metrics.put("max_valor", leitura.getValor()));
                                    return metrics;
                                }
                        )
                ));
    }
}