package yaroslav.max.currencyexchangeapp.dto;

import jakarta.validation.constraints.NotNull;
import yaroslav.max.currencyexchangeapp.entity.Role;

public record UserRequestDto(
        @NotNull
        String username,
        @NotNull
        String password,
        @NotNull
        Role role
) {}
