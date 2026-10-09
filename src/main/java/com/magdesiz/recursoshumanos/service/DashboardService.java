package com.magdesiz.recursoshumanos.service;

import com.magdesiz.recursoshumanos.dto.ApplicantDto;
import com.magdesiz.recursoshumanos.exception.ApplicantException;
import com.magdesiz.recursoshumanos.model.Applicant;
import com.magdesiz.recursoshumanos.repository.ApplicantRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardService {

    private ApplicantRepository applicantRepository;

    public DashboardService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public List<ApplicantDto> findAll() {
        try {
            List<ApplicantDto> applicants = new ArrayList<>();
            for (Applicant applicant : applicantRepository.findAll()) {
                applicants.add(new ApplicantDto(
                        applicant.getId(),
                        applicant.getFirstName(),
                        applicant.getLastName(),
                        applicant.getAddress(),
                        applicant.getAge()));
            }
            return applicants;
        } catch (SQLException e) {
            throw new ApplicantException("Error de base de datos: " + e.getMessage(), e);
        }
    }

    public void add(ApplicantDto dto) {
        Applicant applicant = validate(dto);
        try {
            applicantRepository.save(applicant);
        } catch (SQLException e) {
            throw new ApplicantException("Error de base de datos: " + e.getMessage(), e);
        }
    }

    public void update(ApplicantDto dto) {
        Applicant applicant = validate(dto);
        try {
            if (applicantRepository.update(applicant) == 0) {
                throw new ApplicantException("El aspirante no existe");
            }
        } catch (SQLException e) {
            throw new ApplicantException("Error de base de datos: " + e.getMessage(), e);
        }
    }

    public void delete(int id) {
        try {
            if (applicantRepository.delete(id) == 0) {
                throw new ApplicantException("El aspirante no existe");
            }
        } catch (SQLException e) {
            throw new ApplicantException("Error de base de datos: " + e.getMessage(), e);
        }
    }

    private Applicant validate(ApplicantDto dto) {
        String firstName = dto.getFirstName().trim();
        String lastName = dto.getLastName().trim();
        String address = dto.getAddress().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty()) {
            throw new ApplicantException("Todos los campos son obligatorios");
        }
        if (dto.getAge() < 18 || dto.getAge() > 100) {
            throw new ApplicantException("La edad debe ser mayor a 18");
        }
        return new Applicant(dto.getId(), firstName, lastName, address, dto.getAge());
    }
}

