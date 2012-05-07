package de.groothues.mysaml.assertion.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.assertion.AuthnContextBuilder;
import de.groothues.mysaml.assertion.AuthnContextType;
import de.groothues.mysaml.impl.SamlContextImpl;


public class AuthnContextBuilderImplTest {

	private AuthnContextBuilder authnContextBuilder;
	
	@Before
	public void setUp() {
		authnContextBuilder = new AuthnContextBuilderImpl(new SamlContextImpl());
	}
	
	@Test
	public void testBuildWithoutRuntimeProperties() {
		final int index = 1;
		AuthnContextType authnContext = authnContextBuilder.build(null, index);
		
		assertNotNull(authnContext);
		assertNotNull(authnContext.getAuthnContextClassRef());
	}
	
//	@Test
//	public void testBuildWithRuntimeProperties() {
//		final int index = 1;
//		Map<String, String> runtimeProperties = new HashMap<String, String>();
//		runtimeProperties.put(getIndexedProperty(PropertyKeys.SUBJECT_CONFIRMATION_DATA_RECIPIENT_KEY, index), 
//				TEST_SUBJECT_CONFIRMATION_DATA_RECIPIENT);
//		
//		SubjectConfirmationDataType subjectConfirmationData = 
//			authnContextBuilder.build(runtimeProperties, index);
//		
//		assertNotNull(subjectConfirmationData);
//		assertEquals(TEST_SUBJECT_CONFIRMATION_DATA_RECIPIENT, subjectConfirmationData.getRecipient());
//	}
//
//	private String getIndexedProperty(String key, final int index) {
//		return key + "." + index;
//	}
	
}
