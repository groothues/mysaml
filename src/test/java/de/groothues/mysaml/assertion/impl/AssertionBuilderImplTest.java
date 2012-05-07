package de.groothues.mysaml.assertion.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.assertion.AssertionBuilder;
import de.groothues.mysaml.assertion.AssertionType;
import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.impl.SamlContextImpl;

public class AssertionBuilderImplTest {
	private static final String VERSION_2_0 = "2.0";
	private static final String TEST_ISSUER = "http://issuer.test";
	private static final String TEST_SUBJECT = "testSubjectName";
	
	private AssertionBuilder assertionBuilder;

	@Before
	public void setUp() throws Exception {
		assertionBuilder = new AssertionBuilderImpl(new SamlContextImpl());
	}
	
	@Test
	public void testBuildWithoutRuntimeProperties() {
		AssertionType assertion = assertionBuilder.build(null);
		
		assertNotNull(assertion);
		assertNotNull(assertion.getVersion());
		assertEquals(VERSION_2_0, assertion.getVersion());
		assertNotNull(assertion.getID());
		assertNotNull(assertion.getIssueInstant());
		assertNotNull(assertion.getIssuer());
		assertNotNull(assertion.getSubject());
		assertNotNull(assertion.getConditions());
		assertNotNull(assertion.getStatementOrAuthnStatementOrAuthzDecisionStatement());
		assertEquals(1, assertion.getStatementOrAuthnStatementOrAuthzDecisionStatement().size());
	}
	
	@Test
	public void testBuild() {
		Map<String, String> runtimeProperties = new HashMap<String, String>();
		runtimeProperties.put(PropertyKeys.ISSUER_VALUE_KEY, TEST_ISSUER);
		runtimeProperties.put(PropertyKeys.SUBJECT_NAMEID_VALUE_KEY, TEST_SUBJECT);
		
		AssertionType assertion = assertionBuilder.build(runtimeProperties);
		
		assertEquals(TEST_ISSUER, assertion.getIssuer().getValue());
	}
	
}
