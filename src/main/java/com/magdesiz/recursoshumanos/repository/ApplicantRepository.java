package com.magdesiz.recursoshumanos.repository;

import com.magdesiz.recursoshumanos.config.DatabaseConnection;
import com.magdesiz.recursoshumanos.model.Applicant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicantRepository {

    public List<Applicant> findAll() throws SQLException {
        List<Applicant> applicants = new ArrayList<>();
        String sql = "select id_aspirante, nombre, apellido, direccion, edad from aspirantes order by id_aspirante";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Applicant applicant = new Applicant(
                        result.getInt("id_aspirante"),
                        result.getString("nombre"),
                        result.getString("apellido"),
                        result.getString("direccion"),
                        result.getInt("edad"));
                applicants.add(applicant);
            }
        }
        return applicants;
    }

    public void save(Applicant applicant) throws SQLException {
        String sql = "insert into aspirantes (nombre, apellido, direccion, edad) values (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, applicant.getFirstName());
            statement.setString(2, applicant.getLastName());
            statement.setString(3, applicant.getAddress());
            statement.setInt(4, applicant.getAge());
            statement.executeUpdate();
        }
    }

    public int update(Applicant applicant) throws SQLException {
        String sql = "update aspirantes set nombre = ?, apellido = ?, direccion = ?, edad = ? where id_aspirante = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, applicant.getFirstName());
            statement.setString(2, applicant.getLastName());
            statement.setString(3, applicant.getAddress());
            statement.setInt(4, applicant.getAge());
            statement.setInt(5, applicant.getId());
            return statement.executeUpdate();
        }
    }

    public int delete(int id) throws SQLException {
        String sql = "delete from aspirantes where id_aspirante = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }
}
