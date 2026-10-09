package com.magdesiz.recursoshumanos.service;

import com.magdesiz.recursoshumanos.dto.LoginDto;
import com.magdesiz.recursoshumanos.dto.RegisterDto;
import com.magdesiz.recursoshumanos.dto.UsuarioDto;
import com.magdesiz.recursoshumanos.exception.AuthException;
import com.magdesiz.recursoshumanos.model.Usuario;
import com.magdesiz.recursoshumanos.repository.UsuarioRepository;
import com.magdesiz.recursoshumanos.security.jbcrypt.BCrypt;
import java.sql.SQLException;

public class AuthService {

    private UsuarioRepository userRepository;

    public AuthService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UsuarioDto login(LoginDto dto) {
        String email = dto.getEmail().trim().toLowerCase();
        String password = dto.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            throw new AuthException("Todos los campos son obligatorios");
        }

        try {
            Usuario user = userRepository.findByEmail(email);
            if (user == null) {
                throw new AuthException("Correo o contraseña incorrectos");
            }
            if (!BCrypt.checkpw(password, user.getPassword())) {
                throw new AuthException("Correo o contraseña incorrectos");
            }
            return new UsuarioDto(user.getFirstName(), user.getEmail(), user.getRole());
        } catch (SQLException e) {
            throw new AuthException("Error de base de datos: " + e.getMessage(), e);
        }
    }

    public void register(RegisterDto dto) {
        String firstName = dto.getFirstName().trim();
        String lastName = dto.getLastName().trim();
        String email = dto.getEmail().trim().toLowerCase();
        String password = dto.getPassword();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new AuthException("Todos los campos son obligatorios");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new AuthException("El correo no es válido");
        }
        if (password.length() < 8) {
            throw new AuthException("La contraseña debe tener al menos 8 caracteres");
        }

        try {
            if (userRepository.findByEmail(email) != null) {
                throw new AuthException("El correo ya está registrado");
            }
            String hash = BCrypt.hashpw(password, BCrypt.gensalt());
            userRepository.save(new Usuario(0, firstName, lastName, email, hash, "reclutador"));
        } catch (SQLException e) {
            throw new AuthException("Error de base de datos: " + e.getMessage(), e);
        }
    }
}
