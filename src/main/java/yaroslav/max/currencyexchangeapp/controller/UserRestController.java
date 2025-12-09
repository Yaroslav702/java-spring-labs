package yaroslav.max.currencyexchangeapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yaroslav.max.currencyexchangeapp.dto.UserRequestDto;
import yaroslav.max.currencyexchangeapp.dto.UserResponseDto;
import yaroslav.max.currencyexchangeapp.entity.Role;
import yaroslav.max.currencyexchangeapp.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User API", description = "Users management.")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create user", description = "Creates a new user and returns its data.")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = UserResponseDto.class)))
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody UserRequestDto requestDto) {
        UserResponseDto user = userService.createUser(requestDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(summary = "Get users by Role")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsersByRole(
            @RequestParam Role role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Update user")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(userService.updateUser(id, requestDto));
    }

    @Operation(summary = "Update user (Partial)")
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> patchUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(userService.patchUser(id, requestDto));
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
