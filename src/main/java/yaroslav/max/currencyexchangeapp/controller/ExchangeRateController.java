package yaroslav.max.currencyexchangeapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yaroslav.max.currencyexchangeapp.dto.ExchangeRateRequestDto;
import yaroslav.max.currencyexchangeapp.dto.ExchangeRateResponseDto;
import yaroslav.max.currencyexchangeapp.service.ExchangeRateService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/exchange-rates")
@RequiredArgsConstructor
@Tag(name = "Exchange Rates", description = "Operations related to managing currency exchange rates")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @Operation(
            summary = "Create a new exchange rate",
            description = "Adds a new exchange rate record between two existing currencies based on their IDs."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exchange rate created successfully",
                    content = @Content(schema = @Schema(implementation = ExchangeRateResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided"),
            @ApiResponse(responseCode = "404", description = "Source or Target currency not found")
    })
    @PostMapping
    public ResponseEntity<ExchangeRateResponseDto> createExchangeRate(
            @Parameter(description = "Exchange rate data", required = true)
            @Valid @RequestBody ExchangeRateRequestDto dto) {
        return new ResponseEntity<>(exchangeRateService.createExchangeRate(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get rates by source currency",
            description = "Retrieve a paginated list of exchange rates for a specific source currency code (e.g., USD)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found rates",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/source/{code}")
    public ResponseEntity<Page<ExchangeRateResponseDto>> getBySourceCurrency(
            @Parameter(description = "Currency code (3 letters)", example = "USD")
            @PathVariable String code,
            @Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(exchangeRateService.getRatesBySourceCurrency(code, pageable));
    }

    @Operation(
            summary = "Get rates by target currency",
            description = "Retrieve a list of exchange rates where the specified code is the target currency."
    )
    @GetMapping("/target/{code}")
    public ResponseEntity<List<ExchangeRateResponseDto>> getByTargetCurrency(
            @Parameter(description = "Currency code (3 letters)", example = "EUR")
            @PathVariable String code) {
        return ResponseEntity.ok(exchangeRateService.getRatesByTargetCurrency(code));
    }

    @Operation(
            summary = "Get rates by date",
            description = "Retrieve a list of all exchange rates valid for a specific date."
    )
    @GetMapping("/date/{date}")
    public ResponseEntity<List<ExchangeRateResponseDto>> getByDate(
            @Parameter(description = "Date in ISO format (YYYY-MM-DD)", example = "2023-10-01")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(exchangeRateService.getRatesByDate(date));
    }
}