package de.groothues.mysaml.assertion.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.assertion.SubjectBuilder;
import de.groothues.mysaml.assertion.SubjectType;
import de.groothues.mysaml.assertion.impl.SubjectBuilderImpl;
import de.groothues.mysaml.impl.SamlContextImpl;


public class SubjectBuilderImplTest {

	private SubjectBuilder subjectBuilder;
	
	@Before
	public void setUp() {
		subjectBuilder = new SubjectBuilderImpl(new SamlContextImpl());
	}
	
	@Test
	public void testBuildWithoutRuntimeProperties() {
		SubjectType subject = subjectBuilder.build(null);
		
		assertNotNull(subject);
		assertNotNull(subject.getNameID());
		assertEquals(1, subject.getSubjectConfirmation().size());
	}
	
	@Test
	public void testBuildWithRuntimeProperties() {
		Map<String, String> runtimeProperties = new HashMap<String, String>();
		
		SubjectType subject = subjectBuilder.build(runtimeProperties);
		
		assertNotNull(subject);
	}

}
