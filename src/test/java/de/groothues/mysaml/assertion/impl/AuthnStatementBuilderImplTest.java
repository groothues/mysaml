package de.groothues.mysaml.assertion.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.assertion.AuthnContextType;
import de.groothues.mysaml.assertion.AuthnStatementBuilder;
import de.groothues.mysaml.assertion.AuthnStatementType;
import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.impl.SamlContextImpl;


public class AuthnStatementBuilderImplTest {
		
	private static final String TEST_AUTHNCONTEXTCLASSREF = "urn:test:authncontextclassref";
	
	private AuthnStatementBuilder authnStatementBuilder;
	
	@Before
	public void setUp() {
		authnStatementBuilder = new AuthnStatementBuilderImpl(new SamlContextImpl());
	}
	
	@Test
	public void testBuildWithoutRuntimeProperties() {
		List<AuthnStatementType> authnStatements = authnStatementBuilder.build(null);
		
		assertNotNull(authnStatements);
		assertEquals(1, authnStatements.size());
		AuthnStatementType authnStatement = authnStatements.iterator().next();
		assertNotNull(authnStatement.getAuthnInstant());
		assertNotNull(authnStatement.getAuthnContext());		
	}
	
	@Test
	public void testBuild() {
		final int index = 1;
		Map<String, String> runtimeProperties = new HashMap<String, String>();
		runtimeProperties.put(createIndexedProperty(
				PropertyKeys.AUTHNSTATEMENT_AUTHNCONTEXT_AUTHNCONTEXTCLASSREF_KEY, 
				index),	TEST_AUTHNCONTEXTCLASSREF);
		
		List<AuthnStatementType> authnStatements = 
			authnStatementBuilder.build(runtimeProperties);
		
		assertNotNull(authnStatements);
		assertEquals(1, authnStatements.size());
		AuthnStatementType authnStatement = authnStatements.iterator().next();
		AuthnContextType authnContext = authnStatement.getAuthnContext();
		assertEquals(TEST_AUTHNCONTEXTCLASSREF, authnContext.getAuthnContextClassRef());
	}

	private String createIndexedProperty(String key, final int index) {
		return key + "." + index;
	}

}
