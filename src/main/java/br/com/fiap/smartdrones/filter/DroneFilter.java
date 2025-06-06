package br.com.fiap.smartdrones.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneFilter {
    private String modelo;
    private String status;
}