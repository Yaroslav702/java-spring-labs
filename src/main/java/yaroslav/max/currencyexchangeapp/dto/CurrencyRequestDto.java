package yaroslav.max.currencyexchangeapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CurrencyRequestDto(
    @NotBlank(message = "Currency code is required")
    @Size(min=3, max=3, message = "Currency code must be exactly 3 characters")
    String code,

    @NotBlank(message = "Display name is required")
    @Size(max = 100, message = "Display name must be less than 100 characters")
    String displayName
) {}
