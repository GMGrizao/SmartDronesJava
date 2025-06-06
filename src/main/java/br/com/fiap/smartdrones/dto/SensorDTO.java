package br.com.fiap.smartdrones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDTO {
    private Long id;
    @NotBlank(message = "O tipo do sensor é obrigatório")
    private String tipo;
    @NotNull(message = "O ID do drone associado é obrigatório")
    private Long droneId;
}