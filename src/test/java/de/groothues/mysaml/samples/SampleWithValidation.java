package de.groothues.mysaml.samples;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.SamlContextFactory;
import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.impl.DomHelper;

public class SampleWithValidation {

	public static void main(String[] args) {
		SamlContext samlContext = SamlContextFactory.createSamlContext(getStartupProperties());
		Document assertionDoc = samlContext.getSignedAssertionBuilder().build(getRuntimeProperties());
		
		System.out.println(DomHelper.writeToString(assertionDoc));
		
		System.out.println("Validation Result: " + samlContext.getSignatureValidator().
		        validate(assertionDoc).getResultMessage());
	}

	private static Map<String, String> getRuntimeProperties() {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(PropertyKeys.SUBJECT_NAMEID_VALUE_KEY, getCurrentUser());
		return properties;
	}

	private static String getCurrentUser() {
		return "John Doe";
	}

	private static Map<String, String> getStartupProperties() {
		Map<String, String> startupProperties = new HashMap<String, String>();
		startupProperties.put(PropertyKeys.ISSUER_VALUE_KEY, "http://issuer.icw.com");
		return startupProperties;
	}

}
