package br.com.fiap.smartdrones.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorFilter {
    private String tipo;
    private Long droneId;
}