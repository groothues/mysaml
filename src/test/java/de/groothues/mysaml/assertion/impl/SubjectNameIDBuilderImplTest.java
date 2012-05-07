package de.groothues.mysaml.assertion.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.assertion.NameIDType;
import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.assertion.SubjectNameIDBuilder;
import de.groothues.mysaml.assertion.impl.SubjectNameIDBuilderImpl;
import de.groothues.mysaml.impl.SamlContextImpl;


public class SubjectNameIDBuilderImplTest {
	
	
	private static final String TEST_SUBJECT = "testSubjectName";
	private static final String TEST_SUBJECT_NAMEID_FORMAT = "urn:test:subject:nameid:format";
	
	private SubjectNameIDBuilder subjectNameIDBuilder;
	
	@Before
	public void setUp() {
		subjectNameIDBuilder = new SubjectNameIDBuilderImpl(new SamlContextImpl());
	}
	
	@Test
	public void testBuildWithoutRuntimeProperties() {
		NameIDType subjectNameID = subjectNameIDBuilder.build(null);
		
		assertNotNull(subjectNameID);
		assertNotNull(subjectNameID.getValue());
		assertNotNull(subjectNameID.getFormat());
	}
	
	@Test
	public void testBuild() {
		Map<String, String> runtimeProperties = new HashMap<String, String>();
		runtimeProperties.put(PropertyKeys.SUBJECT_NAMEID_VALUE_KEY, TEST_SUBJECT);
		runtimeProperties.put(PropertyKeys.SUBJECT_NAMEID_FORMAT_KEY, TEST_SUBJECT_NAMEID_FORMAT);
		
		NameIDType subjectNameID = subjectNameIDBuilder.build(runtimeProperties);
		
		assertEquals(TEST_SUBJECT, subjectNameID.getValue());
		assertEquals(TEST_SUBJECT_NAMEID_FORMAT, subjectNameID.getFormat());
	}

}
