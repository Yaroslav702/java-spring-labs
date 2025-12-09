package yaroslav.max.currencyexchangeapp.dto;

import yaroslav.max.currencyexchangeapp.entity.Role;

public record UserResponseDto(
        Long id,
        String username,
        Role role
) {}
