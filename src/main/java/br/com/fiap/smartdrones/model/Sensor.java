package br.com.fiap.smartdrones.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "sensor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id")
    private Long id;

    @NotBlank(message = "O tipo do sensor é obrigatório")
    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @NotNull(message = "O drone associado ao sensor é obrigatório")
    @ManyToOne
    @JoinColumn(name = "drone_id", nullable = false)
    private Drone drone;

    @OneToMany(mappedBy = "sensor")
    private Set<LeituraSensor> leituras;
}