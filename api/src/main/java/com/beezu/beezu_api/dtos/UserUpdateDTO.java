package com.beezu.beezu_api.dtos;

import jakarta.validation.constraints.Email;

public record UserUpdateDTO( String name,  @Email String email) {

}
