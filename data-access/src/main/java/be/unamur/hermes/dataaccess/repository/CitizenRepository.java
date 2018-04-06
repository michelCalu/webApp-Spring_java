package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.Citizen;

import java.util.List;

public interface CitizenRepository {

    Citizen findByName(String firstname, String lastname);

    Citizen findById(Long citizenId);

    List<Citizen> findAll();

    void create(String firstname, String lastname);

}