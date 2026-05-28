package com.beezu.beezu_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;

public record LoginUserRequestDTO(@NotEmpty(message = "email is obrigatory") @Email String email, @NotEmpty(message = "password is obrigatory")String password) {

}
