package be.unamur.hermes.business.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.business.parameters.ClaimParameterSet;
import be.unamur.hermes.business.parameters.ClaimType;
import be.unamur.hermes.business.parameters.ParameterParser;
import be.unamur.hermes.common.enums.ClaimId;

@Service
public class ParameterServiceImpl implements ParameterService {

    private static Logger logger = LoggerFactory.getLogger(ParameterServiceImpl.class);

    private Map<String, ClaimParameterSet> parameterCache;

    @Override
    public boolean isActivated(String municipality, String claimId) {
	ClaimId id = ClaimId.getClaimId(claimId);
	if (id == null)
	    throw new BusinessException("Unknown claim type '" + claimId + "'");
	ClaimParameterSet parameterSet = getSet(municipality);
	return parameterSet == null ? false : parameterSet.isActivated(id);
    }

    @Override
    public String getParameter(String municipality, String claimId, String parameterId) {
	ClaimType claimType = getClaimType(municipality, claimId);
	return claimType == null ? null : claimType.getParameter(parameterId);
    }

    @Override
    public ClaimType getClaimType(String municipality, String claimId) {
	ClaimId id = ClaimId.getClaimId(claimId);
	if (id == null)
	    throw new BusinessException("Unknown claim type '" + claimId + "'");
	ClaimParameterSet parameterSet = getSet(municipality);
	return parameterSet == null ? null : parameterSet.getClaimType(id);
    }

    @Override
    public List<ClaimType> getClaimTypes(String municipality) {
	ClaimParameterSet parameterSet = getSet(municipality);
	return parameterSet == null ? Collections.emptyList() : parameterSet.getClaimTypes();
    }

    private ClaimParameterSet getSet(String municipality) {
	return checkCache() ? parameterCache.get(municipality) : null;
    }

    private boolean checkCache() {
	if (parameterCache != null)
	    return true;

	try (InputStream is = getClass().getClassLoader().getResourceAsStream("parameters.xml");) {
	    ParameterParser parser = new ParameterParser();
	    parameterCache = parser.parse(is);
	} catch (IOException e) {
	    logger.error("File 'parameters.xml' not found", e);
	    throw new BusinessException("File 'parameters.xml' not found", e);
	}
	return true;
    }
}
