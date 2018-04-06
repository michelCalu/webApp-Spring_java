package be.unamur.hermes.business.service;

import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitizenServiceImpl implements CitizenService {

    private CitizenRepository citizenRepository;

    @Autowired
    public CitizenServiceImpl(CitizenRepository citizenRepository){
        this.citizenRepository = citizenRepository;
    }

    @Override
    @Transactional
    public Citizen findByName(String firstName, String lastname) {
        return citizenRepository.findByName(firstName,lastname);
    }

    @Override
    public Citizen findById(Long citizenId) {
        return citizenRepository.findById(citizenId);
    }

    @Override
    public List<Citizen> findAll() {
        return citizenRepository.findAll();
    }

    @Override
    @Transactional
    public void register(String firstname, String lastname) {
        citizenRepository.create(firstname,lastname);
    }
}
