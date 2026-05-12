package com.beezu.beezu_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record DisciplineRequestDTO(@NotBlank String name, String description, @NotBlank String professor){

}
