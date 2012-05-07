/*
 * Copyright 2012 Juergen Groothues
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.groothues.mysaml.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesHelper {

	public static Properties loadProperties(String fileName) {
		try {
			InputStream inStream = PropertiesHelper.class.getClassLoader().getResourceAsStream(fileName);
			Properties properties = new Properties();
			properties.load(inStream);
			return properties;
		} catch (Exception e) {
			throw new RuntimeException("Properties file " + fileName + " cannot be loaded.", e);
		}
	}

	public static Map<String, String> toStringMap(Properties properties) {
		Map<String, String> map = new HashMap<String, String>();
		for (Object key: properties.keySet()) {
			map.put((String) key, properties.getProperty((String) key)); 
		}
		return map;
	}
	
}
