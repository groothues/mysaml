package de.groothues.mysaml.assertion.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.assertion.SubjectConfirmationDataBuilder;
import de.groothues.mysaml.assertion.SubjectConfirmationDataType;
import de.groothues.mysaml.assertion.impl.SubjectConfirmationDataBuilderImpl;
import de.groothues.mysaml.impl.SamlContextImpl;


public class SubjectConfirmationDataBuilderImplTest {

	private static final String TEST_SUBJECT_CONFIRMATION_DATA_RECIPIENT = "http://mysaml.test.recipient";
	
	private SubjectConfirmationDataBuilder subjectConfirmationDataBuilder;
	
	@Before
	public void setUp() {
		subjectConfirmationDataBuilder = new SubjectConfirmationDataBuilderImpl(new SamlContextImpl());
	}
	
	@Test
	public void testBuildWithoutRuntimeProperties() {
		final int index = 1;
		SubjectConfirmationDataType subjectConfirmationData = subjectConfirmationDataBuilder.build(null, index);
		
		assertNotNull(subjectConfirmationData);
		assertNotNull(subjectConfirmationData.getRecipient());
	}
	
	@Test
	public void testBuildWithRuntimeProperties() {
		final int index = 1;
		Map<String, String> runtimeProperties = new HashMap<String, String>();
		runtimeProperties.put(getIndexedProperty(PropertyKeys.SUBJECT_CONFIRMATION_DATA_RECIPIENT_KEY, index), 
				TEST_SUBJECT_CONFIRMATION_DATA_RECIPIENT);
		
		SubjectConfirmationDataType subjectConfirmationData = 
			subjectConfirmationDataBuilder.build(runtimeProperties, index);
		
		assertNotNull(subjectConfirmationData);
		assertEquals(TEST_SUBJECT_CONFIRMATION_DATA_RECIPIENT, subjectConfirmationData.getRecipient());
	}

	private String getIndexedProperty(String key, final int index) {
		return key + "." + index;
	}
	
}
