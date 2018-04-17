package be.unamur.hermes.business.parameters;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import be.unamur.hermes.common.enums.ClaimId;

/**
 * This class holds all parameters of all claim types for a given municipality.
 *
 */
public class RequestParameterSet {

    private final String municipality;
    private final Map<ClaimId, RequestParameters> claims;

    public RequestParameterSet(String municipality) {
	super();
	this.municipality = municipality;
	this.claims = new EnumMap<>(ClaimId.class);
    }

    public String getMunicipality() {
	return municipality;
    }

    public boolean isActivated(ClaimId claimId) {
	RequestParameters claimType = getClaimType(claimId);
	return claimType == null ? false : claimType.isActivated();
    }

    public RequestParameters getClaimType(ClaimId claimId) {
	return claims.get(claimId);
    }

    public String getParameter(ClaimId claimId, String parameterId) {
	RequestParameters claimType = getClaimType(claimId);
	return claimType == null ? null : claimType.getParameter(parameterId);
    }

    public List<RequestParameters> getClaimTypes() {
	return new ArrayList<>(claims.values());
    }

    @Override
    public String toString() {
	return "ClaimParameterSet [municipality=" + municipality + ", claims=" + claims + "]";
    }

    void addClaimParameters(ClaimId id, RequestParameters claim) {
	claims.put(id, claim);
    }
}