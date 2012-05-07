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

import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.PropertyKeys;

public abstract class SamlContextAware implements PropertyKeys {

	public static final JAXBContext jaxbContext;
	
	private de.groothues.mysaml.assertion.ObjectFactory assertionObjectFactory;
	private de.groothues.mysaml.protocol.ObjectFactory protocolObjectFactory;
	private DatatypeFactory datatypeFactory;
	private SamlContext samlContext;

	static {
		jaxbContext = createJAXBContext();
	}
	
	public SamlContextAware(SamlContext samlContext) {
		this.samlContext = samlContext;
		this.assertionObjectFactory = createAssertionObjectFactory();
		this.protocolObjectFactory = createProtocolObjectFactory();
		this.datatypeFactory = createDatatypeFactory();
	}
	
    protected SamlContext getSamlContext() {
		return this.samlContext;
	}

	protected de.groothues.mysaml.assertion.ObjectFactory getAssertionObjectFactory() {
		return this.assertionObjectFactory;
	}

    protected de.groothues.mysaml.protocol.ObjectFactory getProtocolObjectFactory() {
        return this.protocolObjectFactory;
    }
	
	protected XMLGregorianCalendar createXmlCalendar() {
		return this.datatypeFactory.newXMLGregorianCalendar(
				new GregorianCalendar()).normalize();
	}
	
	protected Duration createDuration(long durationInSeconds) {
		return this.datatypeFactory.newDuration(durationInSeconds * 1000);
	}

	protected Map<String, String> getProperties() {
		return this.samlContext.getProperties();
	}

	protected String getProperty(String key) {
		return this.samlContext.getProperty(key);
	}
		
	protected String getProperty(String key, Map<String, String> runtimeProperties) {
		String value = null;
		if (runtimeProperties != null) {
			value = runtimeProperties.get(key);
		}
		if (value == null) {
			value = getProperty(key);
		}
		return value;
	}

	protected String getIndexedProperty(String key,	
			Map<String, String> runtimeProperties, int index) {
		
		String indexedKey = key + "." + index;
		return getProperty(indexedKey, runtimeProperties);
	}
	
	private de.groothues.mysaml.assertion.ObjectFactory createAssertionObjectFactory() {
		return new de.groothues.mysaml.assertion.ObjectFactory();
	}
	
    private de.groothues.mysaml.protocol.ObjectFactory createProtocolObjectFactory() {
        return new de.groothues.mysaml.protocol.ObjectFactory();
    }
	
	private DatatypeFactory createDatatypeFactory() {
		try {
			return DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	private static JAXBContext createJAXBContext() {
		try {
			return JAXBContext.newInstance(
			        "de.groothues.mysaml.assertion:de.groothues.mysaml.protocol");
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
}