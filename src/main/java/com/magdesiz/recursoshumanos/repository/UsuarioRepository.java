package com.magdesiz.recursoshumanos.repository;

import com.magdesiz.recursoshumanos.config.DatabaseConnection;
import com.magdesiz.recursoshumanos.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {

    public Usuario findByEmail(String email) throws SQLException {
        String sql = "select u.id_usuario, u.nombre, u.apellido, u.email, u.password, r.nombre_rol "
                + "from usuarios u inner join rol r on r.id_rol = u.id_rol where u.email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new Usuario(
                            result.getInt("id_usuario"),
                            result.getString("nombre"),
                            result.getString("apellido"),
                            result.getString("email"),
                            result.getString("password"),
                            result.getString("nombre_rol"));
                }
                return null;
            }
        }
    }

    public void save(Usuario user) throws SQLException {
        String sql = "insert into usuarios (nombre, apellido, email, password, id_rol) "
                + "values (?, ?, ?, ?, (select id_rol from rol where nombre_rol = ?))";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());
            statement.executeUpdate();
            
        }
    }
}
