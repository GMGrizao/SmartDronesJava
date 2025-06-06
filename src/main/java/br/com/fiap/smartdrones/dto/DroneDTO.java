package br.com.fiap.smartdrones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneDTO {
    private Long id;
    @NotBlank(message = "O modelo do drone é obrigatório")
    private String modelo;
    @NotBlank(message = "O status do drone é obrigatório")
    @Pattern(regexp = "Ativo|Manutenção|Desativado", message = "Status inválido. Deve ser 'Ativo', 'Manutenção' ou 'Desativado'")
    private String status;
}