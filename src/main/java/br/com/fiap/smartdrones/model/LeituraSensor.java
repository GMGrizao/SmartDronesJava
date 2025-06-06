package br.com.fiap.smartdrones.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "leitura_sensor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeituraSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leitura_id")
    private Long id;

    @NotNull(message = "O sensor da leitura é obrigatório")
    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    @NotNull(message = "O valor da leitura é obrigatório")
    @PositiveOrZero(message = "O valor da leitura deve ser positivo ou zero")
    @Column(name = "valor", nullable = false)
    private Double valor;

    @NotNull(message = "O timestamp da leitura é obrigatório")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;
}