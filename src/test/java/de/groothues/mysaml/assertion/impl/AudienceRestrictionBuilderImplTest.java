package de.groothues.mysaml.assertion.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.assertion.AudienceRestrictionBuilder;
import de.groothues.mysaml.assertion.AudienceRestrictionType;
import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.assertion.impl.AudienceRestrictionBuilderImpl;
import de.groothues.mysaml.impl.SamlContextImpl;


public class AudienceRestrictionBuilderImplTest {
		
	private static final String TEST_AUDIENCE = "http://audience.test";
	
	private AudienceRestrictionBuilder audienceRestrictionBuilder;
	
	@Before
	public void setUp() {
		audienceRestrictionBuilder = new AudienceRestrictionBuilderImpl(new SamlContextImpl());
	}
	
	@Test
	public void testBuildWithoutRuntimeProperties() {
		List<AudienceRestrictionType> audienceRestrictions = audienceRestrictionBuilder.build(null);
		
		assertNotNull(audienceRestrictions);
		assertEquals(1, audienceRestrictions.size());
		AudienceRestrictionType audienceRestriction = audienceRestrictions.iterator().next();
		assertNotNull(audienceRestriction.getAudience());
		assertEquals(1, audienceRestriction.getAudience().size());
		assertNotNull(audienceRestriction.getAudience().iterator().next());
		
	}
	
	@Test
	public void testBuild() {
		final int index = 1;
		Map<String, String> runtimeProperties = new HashMap<String, String>();
		runtimeProperties.put(createIndexedProperty(PropertyKeys.CONDITIONS_AUDIENCERESTRICTION_AUDIENCE_KEY, 
				index),	TEST_AUDIENCE);
		
		List<AudienceRestrictionType> audienceRestrictions = 
			audienceRestrictionBuilder.build(runtimeProperties);
		
		assertNotNull(audienceRestrictions);
		assertEquals(1, audienceRestrictions.size());
		AudienceRestrictionType audienceRestriction = audienceRestrictions.iterator().next();
		List<String> audiences = audienceRestriction.getAudience();
		assertEquals(1, audiences.size());
		assertEquals(TEST_AUDIENCE, audienceRestriction.getAudience().iterator().next());
	}

	private String createIndexedProperty(String key, final int index) {
		return key + "." + index;
	}

}
