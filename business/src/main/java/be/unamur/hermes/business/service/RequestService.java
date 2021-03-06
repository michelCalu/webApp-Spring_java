package be.unamur.hermes.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.dataaccess.entity.RequestField;

public interface RequestService {

    public static final String STATUS_ACCEPTED = "accepted";
    public static final String STATUS_REJECTED = "rejected";

    Request find(long requestId);

    List<Request> findByCitizenId(long citizenId);

    List<Request> findByDepartmentId(long departmentId);

    List<Request> findByAssigneeId(long assigneeId);

    List<Request> findByCompanyNb(String companyNb);

    List<Request> findByCompanyNb(String companyNb, long requestTypeId);

    List<Request> find(long citizenId, long requestTypeId);

    long create(Request newRequest, Map<String, MultipartFile> codeToFiles);

    RequestField findRequestFieldByCode(long requestId, String code);

    Request update(Request request) throws BusinessException;

    Request replace(Request request, Map<String, MultipartFile> codeToFiles) throws BusinessException;

}
