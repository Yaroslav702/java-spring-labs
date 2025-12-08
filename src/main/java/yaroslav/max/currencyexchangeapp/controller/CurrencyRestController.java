package yaroslav.max.currencyexchangeapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import yaroslav.max.currencyexchangeapp.dto.CurrencyRequestDto;
import yaroslav.max.currencyexchangeapp.entity.Currency;
import yaroslav.max.currencyexchangeapp.service.CurrencyService;

@RestController
@RequestMapping("/api/v1/currencies")
@Tag(
        name = "Currency API",
        description = "Provides CRUD operations for managing currencies used in the exchange system."
)
public class CurrencyRestController {

    private final CurrencyService currencyService;

    public CurrencyRestController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Operation(
            summary = "Create a new currency",
            description = "Creates a new currency entity in the system using the provided currency data. " +
                    "The request body must contain all required fields defined in CurrencyRequestDto. " +
                    "If validation fails, a 400 Bad Request response is returned."
    )
    @PostMapping
    public ResponseEntity<Currency> createCurrency(
            @Valid
            @RequestBody
            @Parameter(description = "Currency data required to create a new currency.")
            CurrencyRequestDto requestDto) {
        Currency newCurrency = currencyService.createCurrency(requestDto);
        return new ResponseEntity<>(newCurrency, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get paginated list of currencies",
            description = "Returns a paginated list of all currencies. Optionally supports filtering by currency code. " +
                    "Pagination parameters such as page number, page size, and sorting are supported via Pageable."
    )
    @GetMapping
    public ResponseEntity<Page<Currency>> getAllCurrencies(
            @Parameter(
                    description = "Optional currency code filter. If provided, only currencies matching the code are returned.",
                    example = "USD"
            )
            @RequestParam(required = false) String code,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(currencyService.getAllCurrencies(code, pageable));
    }

    @Operation(
            summary = "Retrieve currency by ID",
            description = "Returns a single currency by its unique identifier. " +
                    "If the currency does not exist, a 404 Not Found response is returned."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Currency> getCurrencyById(
            @Parameter(
                    description = "Unique identifier of the currency.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {
        return currencyService.getCurrencyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update currency (Full Replace)",
            description = "Fully replaces an existing currency with the provided data. " +
                    "All fields must be supplied. If the currency does not exist, an error is returned."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Currency> updateCurrency(
            @Parameter(
                    description = "Unique identifier of the currency to update.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @Valid
            @RequestBody
            @Parameter(description = "Full currency data used to replace the existing entity.")
            CurrencyRequestDto requestDto) {
        return ResponseEntity.ok(currencyService.updateCurrency(id, requestDto));
    }

    @Operation(
            summary = "Update currency (Partial)",
            description = "Partially updates an existing currency. Only the fields present in the request body " +
                    "will be modified. Other fields remain unchanged."
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Currency> patchCurrency(
            @Parameter(
                    description = "Unique identifier of the currency to partially update.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @RequestBody
            @Parameter(description = "Currency fields to update. Only provided fields will be modified.")
            CurrencyRequestDto requestDto) {
        return ResponseEntity.ok(currencyService.patchCurrency(id, requestDto));
    }

    @Operation(
            summary = "Delete currency",
            description = "Deletes a currency by its unique identifier. " +
                    "If the currency does not exist, an error response may be returned."
    )
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCurrency(
            @Parameter(
                    description = "Unique identifier of the currency to delete.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {
        currencyService.deleteCurrency(id);
        return ResponseEntity.noContent().build();
    }
}
