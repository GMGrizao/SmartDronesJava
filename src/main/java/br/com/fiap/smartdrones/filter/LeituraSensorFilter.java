package br.com.fiap.smartdrones.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeituraSensorFilter {
    private Long sensorId;
    private Double valorMin;
    private Double valorMax;
    private Date timestampStart;
    private Date timestampEnd;
}