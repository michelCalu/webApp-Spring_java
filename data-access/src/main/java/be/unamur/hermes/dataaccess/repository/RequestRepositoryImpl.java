package be.unamur.hermes.dataaccess.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import be.unamur.hermes.dataaccess.entity.Citizen;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.dataaccess.entity.Request;

@Repository
public class RequestRepositoryImpl implements RequestRepository {

    // queries
    private static final String queryById = //
	    "SELECT req.requestID, req.requestTypeID, req.employeeID, req.citizenID, req.status FROM t_requests req WHERE req.requestID = ?";
    private static final String queryByCitizenId = //
	    "SELECT req.requestID, req.requestTypeID, req.employeeID, req.citizenID, req.status FROM t_requests req WHERE req.citizenID = ?";
    private static final String queryByCitizenIdAndRequestType = queryByCitizenId //
	    + " AND req.requestTypeID = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert inserter;
    private final CitizenRepository citizenRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public RequestRepositoryImpl(JdbcTemplate jdbcTemplate, CitizenRepository citizenRepository,
	    EmployeeRepository employeeRepository) {
	super();
	this.jdbcTemplate = jdbcTemplate;
	this.inserter = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("t_requests")
		.usingGeneratedKeyColumns("requestID");
	this.citizenRepository = citizenRepository;
	this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Request> findByCitizen(long citizenId) {
	List<Request> requests = jdbcTemplate.query(queryByCitizenId, new Object[] { citizenId },
		new RequestRowMapper());
	requests.stream().forEach(this::fillRequest);
	return requests;
    }

    @Override
    public List<Request> findByCitizen(long citizenId, long requestTypeId) {
	List<Request> requests = jdbcTemplate.query(queryByCitizenIdAndRequestType,
		new Object[] { citizenId, requestTypeId }, new RequestRowMapper());
	requests.stream().forEach(this::fillRequest);
	return requests;
    }

    @Override
    public Request findById(long id) {
	Request result = jdbcTemplate.queryForObject(queryById, new Object[] { id }, new RequestRowMapper());
	fillRequest(result);
	return result;
    }

    @Override
    public long create(Request newRequest) {
	Map<String, Object> parameters = new HashMap<>();
	parameters.put("requestTypeID", newRequest.getTypeId());
	parameters.put("citizenID", newRequest.getCitizen().getId());
	Long employeeId = newRequest.getAssignee() == null ? null : newRequest.getAssignee().getId();
	parameters.put("employeeID", employeeId);
	parameters.put("status", newRequest.getStatus());
	return (Long) inserter.executeAndReturnKey(parameters);
    }

    private void fillRequest(Request request) {
	Citizen citizen = citizenRepository.findById(request.getCitizenId());
	request.setCitizen(citizen);
	if (request.getEmployeeId() > 0) {
	    Employee assignee = employeeRepository.findById(request.getEmployeeId());
	    request.setAssignee(assignee);
	}
    }

    private static class RequestRowMapper implements RowMapper<Request> {
	@Override
	public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
	    Request request = new Request(rs.getLong(1), rs.getLong(2));
	    request.setEmployeeId(rs.getLong(3));
	    request.setCitizenId(rs.getLong(4));
	    request.setStatus(rs.getInt(5));
	    return request;
	}
    }
}