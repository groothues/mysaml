package de.groothues.mysaml.assertion.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import de.groothues.mysaml.assertion.AssertionType;
import de.groothues.mysaml.assertion.AudienceRestrictionType;
import de.groothues.mysaml.assertion.ConditionAbstractType;
import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.assertion.SignedAssertionBuilder;
import de.groothues.mysaml.assertion.SubjectConfirmationType;
import de.groothues.mysaml.impl.SamlContextImpl;

public class SignedAssertionBuilderImplTest {
	
	private static final String TEST_ISSUER = "http://issuer.test";
	private static final String TEST_SUBJECT = "testSubjectName";
	private static final String TEST_AUDIENCE = "http://audience.test";
	private static final String TEST_RECIPIENT = "http://recipient.test";
	
	private SignedAssertionBuilder signedAssertionBuilder;

	@Before
	public void setUp() throws Exception {
		signedAssertionBuilder = new SamlContextImpl().getSignedAssertionBuilder();
	}
	
	@Test
	public void testBuildWithoutRuntimeProperties() {
		Document signedAssertionDoc = signedAssertionBuilder.build(null);
		
		assertNotNull(signedAssertionDoc);
		AssertionType signedAssertion = signedAssertionBuilder.unmarshal(signedAssertionDoc);
		assertNotNull(signedAssertion.getSignature());

	}
	
	@Test
	public void testBuild() {
		Map<String, String> runtimeProperties = new HashMap<String, String>();
		runtimeProperties.put(PropertyKeys.ISSUER_VALUE_KEY, TEST_ISSUER);
		runtimeProperties.put(PropertyKeys.SUBJECT_NAMEID_VALUE_KEY, TEST_SUBJECT);
		runtimeProperties.put(
				createIndexedKey(PropertyKeys.CONDITIONS_AUDIENCERESTRICTION_AUDIENCE_KEY, 1), 
				TEST_AUDIENCE);
		runtimeProperties.put(
				createIndexedKey(PropertyKeys.SUBJECT_CONFIRMATION_DATA_RECIPIENT_KEY, 1), 
				TEST_RECIPIENT);
		
		Document signedAssertionDoc = signedAssertionBuilder.build(runtimeProperties);
		
		assertNotNull(signedAssertionDoc);

		AssertionType signedAssertion = signedAssertionBuilder.unmarshal(signedAssertionDoc);
		assertNotNull(signedAssertion.getSignature());
		
		
		assertEquals(TEST_ISSUER, signedAssertion.getIssuer().getValue());
		assertEquals(TEST_SUBJECT, signedAssertion.getSubject().getNameID().getValue());
		
		List<ConditionAbstractType> audienceRestrictions = signedAssertion.getConditions().
				getConditionOrAudienceRestrictionOrOneTimeUse();
		assertEquals(1, audienceRestrictions.size());
		AudienceRestrictionType audienceRestriction = 
			(AudienceRestrictionType) audienceRestrictions.iterator().next();
		List<String> audiences = audienceRestriction.getAudience();
		assertEquals(1, audiences.size());
		assertEquals(TEST_AUDIENCE, audiences.iterator().next());
		
		List<SubjectConfirmationType> subjectConfirmations = signedAssertion.getSubject().
				getNameIdSubjectConfirmation();
		assertEquals(1, subjectConfirmations.size());
		assertEquals(TEST_RECIPIENT, subjectConfirmations.iterator().next().
				getSubjectConfirmationData().getRecipient());
	}

	private String createIndexedKey(String key, int index) {
		return key + "." + index;
	}
	
}
