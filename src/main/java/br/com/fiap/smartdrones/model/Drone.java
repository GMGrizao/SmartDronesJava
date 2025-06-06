package br.com.fiap.smartdrones.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "drone")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drone_id")
    private Long id;

    @NotBlank(message = "O modelo do drone é obrigatório")
    @Column(name = "modelo", nullable = false, length = 100)
    private String modelo;

    @NotBlank(message = "O status do drone é obrigatório")
    @Pattern(regexp = "Ativo|Manutenção|Desativado", message = "Status inválido. Deve ser 'Ativo', 'Manutenção' ou 'Desativado'")
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @OneToMany(mappedBy = "drone")
    private Set<Sensor> sensores;
}