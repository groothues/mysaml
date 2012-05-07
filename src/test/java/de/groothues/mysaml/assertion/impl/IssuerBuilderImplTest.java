package de.groothues.mysaml.assertion.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.assertion.IssuerBuilder;
import de.groothues.mysaml.assertion.NameIDType;
import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.assertion.impl.IssuerBuilderImpl;
import de.groothues.mysaml.impl.SamlContextImpl;


public class IssuerBuilderImplTest {
	
	private static final String TEST_ISSUER = "urn:mysaml:test:issuer";
	
	private IssuerBuilder issuerBuilder;
	
	@Before
	public void setUp() {
		issuerBuilder = new IssuerBuilderImpl(new SamlContextImpl());
	}
	
	@Test
	public void testBuildWithoutRuntimeProperties() {
		NameIDType issuer = issuerBuilder.build(null);
		
		assertNotNull(issuer);
		assertNotNull(issuer.getValue());
	}
	
	@Test
	public void testBuild() {
		Map<String, String> runtimeProperties = new HashMap<String, String>();
		runtimeProperties.put(PropertyKeys.ISSUER_VALUE_KEY, TEST_ISSUER);
		
		NameIDType issuer = issuerBuilder.build(runtimeProperties);
		
		assertEquals(TEST_ISSUER, issuer.getValue());
	}	

}
