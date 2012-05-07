package de.groothues.mysaml.impl;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.impl.PropertiesHelper;


public class PropertiesHelperTest {

	private static final String KEY_1 = "key_1";
	private static final String KEY_2 = "key_2";
	private static final String DEFAULT_VALUE_1 = "defaultValue_1";
	private static final String DEFAULT_VALUE_2 = "defaultValue_2";

	private Properties properties;
	
	@Before
	public void setUp() {
		properties = new Properties();
		properties.setProperty(KEY_1, DEFAULT_VALUE_1);
		properties.setProperty(KEY_2, DEFAULT_VALUE_2);		
	}
	
	@Test
	public void testToStringMap() {
		Map<String, String> propertyMap = PropertiesHelper.toStringMap(properties);
		assertEquals(2, propertyMap.size());
	}
}
