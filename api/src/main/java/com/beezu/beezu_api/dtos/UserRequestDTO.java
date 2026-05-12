package com.beezu.beezu_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(@NotBlank String name, @NotBlank @Email String email, @NotBlank String password) {

}
