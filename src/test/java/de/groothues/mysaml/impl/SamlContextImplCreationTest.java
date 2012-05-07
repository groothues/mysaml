package de.groothues.mysaml.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.assertion.impl.AssertionBuilderImpl;
import de.groothues.mysaml.impl.PropertiesHelper;
import de.groothues.mysaml.impl.SamlContextImpl;

public class SamlContextImplCreationTest {
	
	private static final String DEFAULT_SAML_VERSION = "2.0";
	private static final String OVERWRITE_SAML_VERSION = "2.1";
	
	private Map<String, String> defaultProperties;
	
	@Before
	public void setUp() throws Exception {
		defaultProperties = PropertiesHelper.toStringMap(
				PropertiesHelper.loadProperties("samlDefault.properties"));
	}
	
	@Test
	public void testPropertiesAvailable() {
		assertFalse(defaultProperties.isEmpty());
		assertEquals(DEFAULT_SAML_VERSION, defaultProperties.get(AssertionBuilderImpl.SAML_VERSION_KEY));
	}
	
	@Test
	public void testCreateSamlContextWithDefaultProperties() {
		SamlContextImpl samlContext = new SamlContextImpl();
		Map<String, String> samlContextProperties = samlContext.getProperties();
		assertEquals(defaultProperties, samlContextProperties);
	}

	@Test
	public void testCreateSamlContextWithOverwriteProperties() {
		SamlContextImpl samlContext = new SamlContextImpl(createOverwriteProperties());
		Map<String, String> samlContextProperties = samlContext.getProperties();
		assertEquals(OVERWRITE_SAML_VERSION, samlContextProperties.get(AssertionBuilderImpl.SAML_VERSION_KEY));
	}

	@Test
	public void testCreateSamlContextWithNullProperties() {
		SamlContextImpl samlContext = new SamlContextImpl(null);
		Map<String, String> samlContextProperties = samlContext.getProperties();
		assertEquals(DEFAULT_SAML_VERSION, samlContextProperties.get(AssertionBuilderImpl.SAML_VERSION_KEY));
	}
	
	private Map<String, String> createOverwriteProperties() {
		Map<String, String> overwriteProperties = new HashMap<String, String>();
		overwriteProperties.put(AssertionBuilderImpl.SAML_VERSION_KEY, OVERWRITE_SAML_VERSION);
		return overwriteProperties;
	}
	
}
