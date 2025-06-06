package br.com.fiap.smartdrones.dto;

import jakarta.validation.constraints.NotBlank;

public record Credentials(
    @NotBlank(message = "O username é obrigatório")
    String username,
    @NotBlank(message = "A senha é obrigatória")
    String password
) {}