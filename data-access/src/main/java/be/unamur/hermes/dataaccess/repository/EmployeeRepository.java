package be.unamur.hermes.dataaccess.repository;

import java.util.List;

import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.UserAccount;

public interface EmployeeRepository {

    Employee findByName(String firstname, String lastname);

    Employee findById(long employeeId);

    List<Employee> findAll();

    long create(Employee employee, long userAccountId);

    public UserAccount findAccount(String nationalRegistrationNb);

    public UserAccount findAccount(long employeeId);
}
