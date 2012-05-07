package de.groothues.mysaml.assertion.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.assertion.ConditionAbstractType;
import de.groothues.mysaml.assertion.ConditionsBuilder;
import de.groothues.mysaml.assertion.ConditionsType;
import de.groothues.mysaml.assertion.impl.ConditionsBuilderImpl;
import de.groothues.mysaml.impl.SamlContextImpl;


public class ConditionsBuilderImplTest {

	private ConditionsBuilder conditionsBuilder;
	
	@Before
	public void setUp() {
		conditionsBuilder = new ConditionsBuilderImpl(new SamlContextImpl());
	}
	
	@Test
	public void testBuildWithoutRuntimeProperties() {
		ConditionsType conditions = conditionsBuilder.build(null);
		
		assertNotNull(conditions);
		assertNotNull(conditions.getNotBefore());
		List<ConditionAbstractType> audienceRestrictions = 
			conditions.getConditionOrAudienceRestrictionOrOneTimeUse();
		assertNotNull(audienceRestrictions);
		assertEquals(1, audienceRestrictions.size());
	}
	
	@Test
	public void testBuildWithRuntimeProperties() {
		Map<String, String> runtimeProperties = new HashMap<String, String>();
		
		ConditionsType conditions = conditionsBuilder.build(runtimeProperties);
		
		assertNotNull(conditions);
	}

}
