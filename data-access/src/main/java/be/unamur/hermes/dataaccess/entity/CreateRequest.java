package be.unamur.hermes.dataaccess.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CreateRequest {
    private Long requestTypeId;
    private String type;
    private Long citizen;

    public CreateRequest() {
    }

    public String getType() {
	return type;
    }

    public Long getCitizen() {
	return citizen;
    }

    public void setType(String type) {
	this.type = type;
    }

    public void setCitizen(Long citizen) {
	this.citizen = citizen;
    }

    @JsonIgnore
    public Long getRequestTypeId() {
	return requestTypeId;
    }

    public void setRequestTypeId(Long requestTypeId) {
	this.requestTypeId = requestTypeId;
    }
}