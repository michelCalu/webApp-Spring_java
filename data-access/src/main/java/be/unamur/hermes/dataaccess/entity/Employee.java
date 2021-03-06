package be.unamur.hermes.dataaccess.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Employee extends User {

    private String accountNumber;
    private LocalDate arrivalDate;
    private char gender;
    private String civilStatus;
    private int dependentChildren;
    private int dependentPeople;
    private List<Long> departmentIds;

    Employee() {
    }

    public Employee(long id, String firstname, String lastname, Address address, String mail, String phone,
	    String nationalRegistreNb, LocalDate birthdate, String accountNumber, LocalDate arrivalDate, char gender,
	    String civilStatus, int dependentChildren, int dependentPeople) {
	super(firstname, lastname, address, mail, phone, nationalRegistreNb, birthdate);
	setId(id);
	this.accountNumber = accountNumber;
	this.arrivalDate = arrivalDate;
	this.gender = gender;
	this.civilStatus = civilStatus;
	this.dependentChildren = dependentChildren;
	this.dependentPeople = dependentPeople;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof Employee))
	    return false;
	if (!super.equals(o))
	    return false;
	Employee employee = (Employee) o;
	return gender == employee.gender && dependentChildren == employee.dependentChildren
		&& dependentPeople == employee.dependentPeople && Objects.equals(accountNumber, employee.accountNumber)
		&& Objects.equals(arrivalDate, employee.arrivalDate)
		&& Objects.equals(civilStatus, employee.civilStatus);
    }

    @Override
    public int hashCode() {

	return Objects.hash(super.hashCode(), accountNumber, arrivalDate, gender, civilStatus, dependentChildren,
		dependentPeople);
    }

    public String getAccountNumber() {
	return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
    }

    public LocalDate getArrivalDate() {
	return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
	this.arrivalDate = arrivalDate;
    }

    public char getGender() {
	return gender;
    }

    public void setGender(char gender) {
	this.gender = gender;
    }

    public String getCivilStatus() {
	return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
	this.civilStatus = civilStatus;
    }

    public int getDependentChildren() {
	return dependentChildren;
    }

    public void setDependentChildren(int dependentChildren) {
	this.dependentChildren = dependentChildren;
    }

    public int getDependentPeople() {
	return dependentPeople;
    }

    public void setDependentPeople(int dependentPeople) {
	this.dependentPeople = dependentPeople;
    }

    public List<Long> getDepartmentIds() {
	return this.departmentIds;
    }

    public void setDepartmentIds(List<Long> departmentIds) {
	this.departmentIds = departmentIds;
    }
}
