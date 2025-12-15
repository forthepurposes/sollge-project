package com.sollge.auth.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.sollge.auth.token.JsonTokenService;
import com.sollge.auth.user.dto.auth.AuthRequestDTO;
import com.sollge.auth.user.dto.auth.AuthResponseDTO;
import com.sollge.auth.user.dto.registration.RegistrationRequestDTO;
import com.sollge.auth.user.dto.user.UserDTO;
import com.sollge.auth.user.dto.user.UserDTOMapper;
import com.sollge.auth.user.model.UserDetailsImpl;
import com.sollge.auth.user.model.UserEntity;
import com.sollge.auth.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDTOMapper mapper;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JsonTokenService jwtService;

    public @NonNull AuthResponseDTO authenticate(final @NonNull AuthRequestDTO request) {
        var authToken = UsernamePasswordAuthenticationToken.unauthenticated(request.username(), request.password());
        var authentication = authenticationManager.authenticate(authToken);

        var userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtService.generateToken(request.username(), userDetails.getUserId());

        return new AuthResponseDTO(token);
    }

    @Transactional
    public @NonNull UserDTO register(final @NonNull RegistrationRequestDTO registrationRequest) {
        Optional<UserDTO> foundUserByEmail = findByEmail(registrationRequest.email());
        if (foundUserByEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        Optional<UserDTO> foundUserByUsername = findByUsername(registrationRequest.username());
        if (foundUserByUsername.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(registrationRequest.username());
        user.setEmail(registrationRequest.email());
        user.setPassword(passwordEncoder.encode(registrationRequest.password()));

        return mapper.apply(repository.save(user));
    }

    public @NonNull Optional<UserDTO> find(final @NonNull Long id) {
        return repository.findById(id).map(mapper);
    }

    public @NonNull Optional<UserDTO> findByEmail(final @NonNull String email) {
        return repository.findByEmail(email).map(mapper);
    }

    public @NonNull Optional<UserDTO> findByUsername(final @NonNull String username) {
        return repository.findByUsername(username).map(mapper);
    }

    public void save(final @NonNull UserEntity user) {
        repository.save(user);
    }
}
