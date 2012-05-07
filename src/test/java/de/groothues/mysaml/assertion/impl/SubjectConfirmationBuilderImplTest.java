package de.groothues.mysaml.assertion.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.assertion.SubjectConfirmationBuilder;
import de.groothues.mysaml.assertion.SubjectConfirmationType;
import de.groothues.mysaml.assertion.impl.SubjectConfirmationBuilderImpl;
import de.groothues.mysaml.impl.SamlContextImpl;


public class SubjectConfirmationBuilderImplTest {
	
	
	private static final String TEST_SUBJECT_CONFIRMATION_METHOD = "urn:test:subject:confirmation:method";
	
	private SubjectConfirmationBuilder subjectConfirmationBuilder;
	
	@Before
	public void setUp() {
		subjectConfirmationBuilder = new SubjectConfirmationBuilderImpl(new SamlContextImpl());
	}
	
	@Test
	public void testBuildWithoutRuntimeProperties() {
		List<SubjectConfirmationType> subjectConfirmations = subjectConfirmationBuilder.build(null);
		
		assertNotNull(subjectConfirmations);
		assertEquals(1, subjectConfirmations.size());
		SubjectConfirmationType subjectConfirmation = subjectConfirmations.iterator().next();
		assertNotNull(subjectConfirmation.getMethod());
		assertNotNull(subjectConfirmation.getSubjectConfirmationData());
		
	}
	
	@Test
	public void testBuild() {
		final int index = 1;
		Map<String, String> runtimeProperties = new HashMap<String, String>();
		runtimeProperties.put(getIndexedProperty(PropertyKeys.SUBJECT_CONFIRMATION_METHOD_KEY, index), 
				TEST_SUBJECT_CONFIRMATION_METHOD);
		
		List<SubjectConfirmationType> subjectConfirmations = subjectConfirmationBuilder.build(runtimeProperties);
		
		assertNotNull(subjectConfirmations);
		assertEquals(1, subjectConfirmations.size());
		SubjectConfirmationType subjectConfirmation = subjectConfirmations.iterator().next();
		assertEquals(TEST_SUBJECT_CONFIRMATION_METHOD, subjectConfirmation.getMethod());
	}

	private String getIndexedProperty(String key, final int index) {
		return key + "." + index;
	}

}
