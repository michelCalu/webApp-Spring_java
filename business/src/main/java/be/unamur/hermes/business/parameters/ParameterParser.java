package be.unamur.hermes.business.parameters;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.common.enums.ClaimId;
import be.unamur.hermes.common.util.XMLUtil;

/**
 * 
 * Class which parses the parameters.xml file.
 *
 */
public class ParameterParser {

    private static final Logger logger = LoggerFactory.getLogger(ParameterParser.class);
    private static final String ATTR_ID = "id";
    private static final String TAG_ROOT = "parameters";
    private static final String TAG_PARAMETRIZABLE = "parametrizable";
    private static final String TAG_CLAIMTYPE = "claimType";
    private static final String QUERY_MUNICIPALITY = "/" + TAG_ROOT + "/" + TAG_PARAMETRIZABLE + "[@id='%s']";

    /**
     * Use this method to parse the entire contents of the inputstream.
     * 
     * Please notice that closing the stream remains the responsability of the
     * caller!
     * 
     * @param is
     *            the contents of a parameters.xml file
     * @return a Map<String,ClaimParameterSet> where the key is the name of the
     *         municipality
     */
    public Map<String, ClaimParameterSet> parse(InputStream is) {
	Document doc;
	try {
	    doc = XMLUtil.toDocument(is);
	} catch (Exception e) {
	    logger.error("Invalid parameters.xml file", e);
	    throw new BusinessException("Invalid parameters.xml file");
	}
	Map<String, ClaimParameterSet> result = new HashMap<>();
	Element root = doc.getDocumentElement();
	List<Element> municipalityElements = DomUtils.getChildElementsByTagName(root, TAG_PARAMETRIZABLE);

	for (Element municipalityElem : municipalityElements) {
	    ClaimParameterSet cps = parseMunicipality(municipalityElem);
	    result.put(cps.getMunicipality(), cps);
	}
	return result;
    }

    /**
     * Parse and retrieve the {@link ClaimParameterSet} for a single municipality at
     * a time. For future use.
     * 
     * @param doc
     *            the DOM contents of a parameters.xml file
     * @param municipality
     * @return the ClaimParameterSet corresponding to the municipality given in
     *         parameter
     */
    public ClaimParameterSet parseMunicipality(Document doc, String municipality) {
	XPathFactory xpf = XPathFactory.newInstance();
	XPath path = xpf.newXPath();
	String queryString = String.format(QUERY_MUNICIPALITY, municipality);
	try {
	    Element municipalityElem = (Element) path.evaluate(queryString, doc.getDocumentElement(),
		    XPathConstants.NODE);
	    return parseMunicipality(municipalityElem);
	} catch (XPathExpressionException ex) {
	    throw new BusinessException("Invalid Expression: " + queryString);
	}
    }

    private ClaimParameterSet parseMunicipality(Element municipalityElem) {
	String idString = municipalityElem.getAttribute(ATTR_ID);
	if (!StringUtils.hasText(idString)) {
	    throw new BusinessException(missingAttribute(municipalityElem, ATTR_ID));
	}
	ClaimParameterSet cps = new ClaimParameterSet(idString);
	List<Element> parameterElems = DomUtils.getChildElementsByTagName(municipalityElem, TAG_CLAIMTYPE);
	for (Element paramElem : parameterElems) {
	    ClaimType cp = parseClaim(paramElem);
	    cps.addClaimParameters(cp.getId(), cp);
	}
	return cps;
    }

    private ClaimType parseClaim(Element claimElem) {
	String claimIdString = claimElem.getAttribute(ATTR_ID);
	if (!StringUtils.hasText(claimIdString))
	    throw new BusinessException(missingAttribute(claimElem, ATTR_ID));
	ClaimId claimType = ClaimId.getClaimId(claimIdString);
	if (claimType == null) {
	    String message = String.format("Invalid ClaimType ID '%s' - one of '%s' expected.", claimIdString,
		    StringUtils.arrayToCommaDelimitedString(ClaimId.values()));
	    throw new BusinessException(message);
	}
	ClaimType cp = new ClaimType(claimType);
	// parse parameter nodes (if any)
	for (Element child : DomUtils.getChildElements(claimElem)) {
	    parseParams(child, cp, "");
	}
	return cp;
    }

    private void parseParams(Element elem, ClaimType params, String parentId) {
	String newId = appendXPathSegment(parentId, elem);
	List<Element> children = DomUtils.getChildElements(elem);
	// is element leaf ?
	if (children.isEmpty()) {
	    params.addParameter(newId, DomUtils.getTextValue(elem));
	} else {
	    // recurse
	    for (Element child : children) {
		parseParams(child, params, newId);
	    }
	}
    }

    private String appendXPathSegment(String parentId, Element childElem) {
	StringBuilder sb = new StringBuilder();
	if (StringUtils.hasText(parentId)) {
	    sb.append(parentId + "/");
	}
	sb.append(childElem.getTagName());
	Attr idAttr = childElem.getAttributeNode(ATTR_ID);
	if (idAttr != null) {
	    sb.append("[@id=" + StringUtils.quote(idAttr.getValue()) + "]");
	}
	return sb.toString();
    }

    private String missingAttribute(Element elem, String attr) {
	return String.format("Attribute '%s' is required on node '%s'", elem.getTagName(), attr);
    }
}
