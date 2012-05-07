package de.groothues.mysaml.assertion.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import de.groothues.mysaml.assertion.AssertionBuilder;
import de.groothues.mysaml.assertion.AssertionType;
import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.impl.SamlContextImpl;

public class AssertionMarshallerUnmarshallerImplTest {
	private static final String TEST_ISSUER = "http://issuer.test";
	private static final String TEST_SUBJECT = "testSubjectName";
	
	private AssertionBuilder assertionBuilder;

	@Before
	public void setUp() throws Exception {
		assertionBuilder = new SamlContextImpl().getAssertionBuilder();
	}
	
	@Test
	public void testMarshalUnmarshalWithoutRuntimeProperties() {
		AssertionType assertion = assertionBuilder.build(null);

		Document assertionDoc = assertionBuilder.marshal(assertion);
		
		assertNotNull(assertionDoc);
		
		AssertionType unmarshalledAssertion = assertionBuilder.unmarshal(assertionDoc);
		
		assertNotNull(unmarshalledAssertion);
		assertNotNull(unmarshalledAssertion.getVersion());
		assertNotNull(unmarshalledAssertion.getID());
		assertNotNull(unmarshalledAssertion.getIssueInstant());
		assertNotNull(unmarshalledAssertion.getIssuer());
		assertNotNull(unmarshalledAssertion.getSubject());
		assertNotNull(unmarshalledAssertion.getConditions());
		assertNotNull(unmarshalledAssertion.getStatementOrAuthnStatementOrAuthzDecisionStatement());
		assertEquals(1, unmarshalledAssertion.getStatementOrAuthnStatementOrAuthzDecisionStatement().size());
	}
	
	@Test
	public void testMarshalUnmarshal() {
		Map<String, String> runtimeProperties = new HashMap<String, String>();
		runtimeProperties.put(PropertyKeys.ISSUER_VALUE_KEY, TEST_ISSUER);
		runtimeProperties.put(PropertyKeys.SUBJECT_NAMEID_VALUE_KEY, TEST_SUBJECT);
		
		AssertionType assertion = assertionBuilder.build(runtimeProperties);
		Document assertionDoc = assertionBuilder.marshal(assertion);
		AssertionType unmarshalledAssertion = assertionBuilder.unmarshal(assertionDoc);
		
		assertEquals(TEST_ISSUER, unmarshalledAssertion.getIssuer().getValue());
		assertEquals(TEST_SUBJECT, unmarshalledAssertion.getSubject().getNameID().getValue());
	}
	
}
