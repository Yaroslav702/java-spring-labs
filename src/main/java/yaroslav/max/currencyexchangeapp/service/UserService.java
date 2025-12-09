package yaroslav.max.currencyexchangeapp.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yaroslav.max.currencyexchangeapp.dto.UserRequestDto;
import yaroslav.max.currencyexchangeapp.dto.UserResponseDto;
import yaroslav.max.currencyexchangeapp.entity.User;
import yaroslav.max.currencyexchangeapp.entity.Role;
import yaroslav.max.currencyexchangeapp.repository.UserRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setRole(dto.role());

        Long newId = userRepository.create(user);
        user.setId(newId);

        return this.mapToResponseDto(user);
    }

    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToResponseDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public List<UserResponseDto> getUsersByRole(Role role) {
        return userRepository.findByRole(role).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setRole(dto.role());

        userRepository.update(user);

        return mapToResponseDto(user);
    }

    @Transactional
    public UserResponseDto patchUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (dto.username() != null) user.setUsername(dto.username());
        if (dto.password() != null) user.setPassword(dto.password());
        if (dto.role() != null) user.setRole(dto.role());

        userRepository.update(user);

        return mapToResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    protected UserResponseDto mapToResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}