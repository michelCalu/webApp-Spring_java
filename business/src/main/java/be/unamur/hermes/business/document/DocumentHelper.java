package be.unamur.hermes.business.document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.util.StringUtils;

import be.unamur.hermes.dataaccess.entity.Address;
import be.unamur.hermes.dataaccess.entity.User;

/**
 * Utility methods to be called inside ThymeLeaf. No static methods because
 * Thymeleaf has very cumbersome syntax for them.
 * 
 * @author Thomas_Elskens
 *
 */
public class DocumentHelper {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public String getAddressLine1(Address address) {
	StringBuilder sb = new StringBuilder();
	sb.append(address.getStreetNb());
	if (StringUtils.hasText(address.getNbSuffix()))
	    sb.append(address.getNbSuffix());
	sb.append(", ").append(address.getStreet());
	return sb.toString();
    }

    public String getAddressLine2(Address address) {
	return address.getZipCode() + " " + address.getMunicipality();
    }

    public String formatName(User user) {
	return user.getLastName() + " " + user.getFirstName();
    }

    /**
     * Thymeleaf syntax for formatting dates is cumbersome.
     * 
     * @param date
     * @return
     */
    public String format(LocalDate date) {
	return date.format(dtf);
    }

}