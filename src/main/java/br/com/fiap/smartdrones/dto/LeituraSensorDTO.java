package br.com.fiap.smartdrones.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeituraSensorDTO {
    private Long id;
    @NotNull(message = "O ID do sensor da leitura é obrigatório")
    private Long sensorId;
    @NotNull(message = "O valor da leitura é obrigatório")
    @PositiveOrZero(message = "O valor da leitura deve ser positivo ou zero")
    private Double valor;
    @NotNull(message = "O timestamp da leitura é obrigatório")
    private Date timestamp;
}