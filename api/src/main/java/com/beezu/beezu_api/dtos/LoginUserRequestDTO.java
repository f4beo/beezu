package com.beezu.beezu_api.dtos;

import jakarta.validation.constraints.NotEmpty;

public record LoginUserRequestDTO(@NotEmpty(message = "email is obrigatory") String email, @NotEmpty(message = "password is obrigatory")String password) {

}
