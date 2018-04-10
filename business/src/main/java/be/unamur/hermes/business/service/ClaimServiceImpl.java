package be.unamur.hermes.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.unamur.hermes.dataaccess.entity.Claim;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.repository.ClaimRepository;
import be.unamur.hermes.dataaccess.repository.EmployeeRepository;
import be.unamur.hermes.dataaccess.repository.InhabitantRepository;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final EmployeeRepository employeeRepository;
    private final InhabitantRepository inhabitantRepository;

    @Autowired
    public ClaimServiceImpl(ClaimRepository claimRepository, EmployeeRepository employeeRepository,
	    InhabitantRepository inhabitantRepository) {
	super();
	this.claimRepository = claimRepository;
	this.employeeRepository = employeeRepository;
	this.inhabitantRepository = inhabitantRepository;
    }

    @Override
    public Claim find(long claimId) {
	Claim result = claimRepository.findById(claimId);
	long employeeId = result.getEmployeeId();
	Employee assignee = employeeRepository.findById(employeeId);
	result.setAssignee(assignee);
	// TODO citizen
	long peopleId = result.getPeopleId();
	return result;
    }

}
