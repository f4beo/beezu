package com.beezu.beezu_api.dtos;

import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequestDTO(@NotEmpty(message ="name is obrigatory") String name,
		@NotEmpty(message ="email is obrigatory") String email,@NotEmpty(message ="password is obrigatory") String password) {

}
