DROP TABLE IF EXISTS t_departments_skills;
DROP TABLE IF EXISTS t_skills;
DROP TABLE IF EXISTS t_departments_employees;
DROP TABLE IF EXISTS t_departments;
DROP TABLE IF EXISTS t_municipalities;
DROP TABLE IF EXISTS t_requests;
DROP TABLE IF EXISTS t_request_types;
DROP TABLE IF EXISTS t_employees;
DROP TABLE IF EXISTS t_citizens;
DROP TABLE IF EXISTS t_addresses;



CREATE TABLE t_addresses (
  addressID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  country VARCHAR(255)    NOT NULL,
  state VARCHAR(255)      NOT NULL,
  zipCode INT             NOT NULL,
  street VARCHAR(255)     NOT NULL,
  streetNb INT            NOT NULL
);


CREATE TABLE t_citizens (
  citizenID  INT PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  firstName     VARCHAR(255)      NOT NULL,
  lastName      VARCHAR(255)      NOT NULL,
  addressID     INT               NOT NULL,
  mail          VARCHAR(255)      NOT NULL,
  phone         VARCHAR(255),
  nationalRegisterNb VARCHAR(255) NOT NULL,
  birthdate     DATE,
  activated     BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (addressID) REFERENCES t_addresses(addressID)
);

CREATE TABLE t_employees (
  employeeID    INT PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  firstName     VARCHAR(255)      NOT NULL,
  lastName      VARCHAR(255)      NOT NULL,
  addressID     INT               NOT NULL,
  mail          VARCHAR(255)      NOT NULL,
  phone         VARCHAR(255)      NOT NULL,
  nationalRegisterNb VARCHAR(255) NOT NULL,
  birthdate     DATE              NOT NULL,
  accountNumber VARCHAR(255)      NOT NULL,
  arrivalDate   DATE              NOT NULL,
  gender        CHAR(1)           NOT NULL,
  civilStatus   VARCHAR(255)      NOT NULL,
  dependentChildren INT           NOT NULL,
  dependentPeople   INT           NOT NULL,
  FOREIGN KEY (addressID)  REFERENCES t_addresses(addressID)
);

CREATE TABLE t_request_types (
	requestTypeID INT PRIMARY KEY NOT NULL,
	description VARCHAR(255),
	CONSTRAINT UC_Description UNIQUE (description)
);

CREATE TABLE t_requests (
  requestID		INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  requestTypeID	INT 			NOT NULL,
  citizenID		INT				NOT NULL,
  employeeID	INT						,
  status 		INT				NOT NULL,
  
  FOREIGN KEY(requestTypeID)
    REFERENCES t_request_types(requestTypeID),
  FOREIGN KEY(citizenID)
    REFERENCES t_citizens(citizenID),
  FOREIGN KEY(employeeID)
    REFERENCES t_employees(employeeID)
);

CREATE TABLE t_municipalities (
  municipalityID  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name    VARCHAR(255)  NOT NULL UNIQUE
);

CREATE TABLE t_departments (
  departmentID        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  municipalityID      INT NOT NULL,
  name                VARCHAR(255)  NOT NULL,
  headOfDepartmentID  INT NOT NULL,
  parentDepartmentID  INT,

  FOREIGN KEY(headOfDepartmentID)
    REFERENCES t_employees(employeeID),
  FOREIGN KEY(parentDepartmentID)
    REFERENCES t_departments(departmentID),
  FOREIGN KEY(municipalityID)
    REFERENCES t_municipalities (municipalityID)
);

CREATE TABLE t_departments_employees (
  departmentID  INT NOT NULL,
  employeeID    INT NOT NULL,

  PRIMARY KEY (departmentID,employeeID),
  FOREIGN KEY (departmentID) REFERENCES t_departments(departmentID),
  FOREIGN KEY (employeeID) REFERENCES t_employees(employeeID),
  UNIQUE (departmentID,employeeID)
);

CREATE TABLE t_skills (
  skillID       VARCHAR(255) PRIMARY KEY NOT NULL,
  description   VARCHAR(255) UNIQUE
);

CREATE TABLE t_departments_skills (
  departmentID  INT NOT NULL,
  skillID       INT NOT NULL,

  PRIMARY KEY (departmentID, skillID),
  FOREIGN KEY (departmentID) REFERENCES t_departments(departmentID),
  FOREIGN KEY (skillID) REFERENCES t_skills(skillID),
  UNIQUE (departmentID, skillID)
)

/*
-- test data
insert into t_claim_types values(1,"Certificat de nationalité");
insert into t_people values(null,"Thomas","Elskens");
insert into t_people values(null,"Fabian","Germeau");
insert into t_citizens values (null, 1, 1);
insert into t_employees values(null,2);
insert into t_claims values(null,1,1,1);
*/
