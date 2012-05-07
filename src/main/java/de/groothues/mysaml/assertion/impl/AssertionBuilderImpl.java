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
package de.groothues.mysaml.assertion.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Document;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.AssertionBuilder;
import de.groothues.mysaml.assertion.AssertionType;
import de.groothues.mysaml.assertion.AuthnStatementType;
import de.groothues.mysaml.assertion.ConditionsType;
import de.groothues.mysaml.assertion.NameIDType;
import de.groothues.mysaml.assertion.SubjectType;
import de.groothues.mysaml.impl.SamlContextAware;
import de.groothues.mysaml.impl.DomHelper;

public class AssertionBuilderImpl extends SamlContextAware implements AssertionBuilder {
	
	public static final String PREFIX_XMLDSIG = "ds";
	public static final String PREFIX_XMLENC = "xenc";
    public static final String PREFIX_SAMLP = "samlp";
	
	public AssertionBuilderImpl(SamlContext samlContext) {
		super(samlContext);
	}

	public AssertionType build(Map<String, String> runtimeProperties) {
		AssertionType assertion = buildAssertion(runtimeProperties);
		buildIssuer(runtimeProperties, assertion);
		buildSubject(runtimeProperties, assertion);
		buildConditions(runtimeProperties, assertion);
		buildAuthnStatement(runtimeProperties, assertion);		
		return assertion;
	}

	@Override
	public Document marshal(AssertionType assertion) {
		try {
	        Marshaller marshaller = jaxbContext.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marshaller.setProperty( Marshaller.JAXB_SCHEMA_LOCATION, 
	        		"urn:oasis:names:tc:SAML:2.0:assertion saml-schema-assertion-2.0.xsd");
	        
	        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
	        		new com.sun.xml.bind.marshaller.NamespacePrefixMapper(){ 

	            public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) 
	            { 
	                if( "http://www.w3.org/2000/09/xmldsig#".equals(namespaceUri) ) { 
	                	return PREFIX_XMLDSIG;
	                }
	                if( "http://www.w3.org/2001/04/xmlenc#".equals(namespaceUri) ) { 
	                	return PREFIX_XMLENC;
	                }
                    if( "urn:oasis:names:tc:SAML:2.0:protocol".equals(namespaceUri) ) { 
                        return PREFIX_SAMLP;
                    }
	                     
	                return suggestion; 
	             } 
	           } 
	        ); 
		
	        JAXBElement<AssertionType> assertionElement = getAssertionObjectFactory().createAssertion(assertion);
	        Document assertionDoc = DomHelper.createNewDocument();
	        marshaller.marshal(assertionElement, assertionDoc);
	        return assertionDoc;
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public AssertionType unmarshal(Document assertionDoc) {
		try {
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		
			@SuppressWarnings("unchecked")
			JAXBElement<AssertionType> assertionElement = 
				(JAXBElement<AssertionType>)unmarshaller.unmarshal(assertionDoc);
			return assertionElement.getValue();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}	
	}
	
	private AssertionType buildAssertion(Map<String, String> runtimeProperties) {
		AssertionType assertion = getAssertionObjectFactory().createAssertionType();
		assertion.setVersion(getProperty(SAML_VERSION_KEY, runtimeProperties));
		assertion.setID(UUID.randomUUID().toString());
		assertion.setIssueInstant(createXmlCalendar());
		return assertion;
	}

	private void buildIssuer(Map<String, String> runtimeProperties,
			AssertionType assertion) {
		NameIDType issuer = getSamlContext().getIssuerBuilder().build(runtimeProperties);
		assertion.setIssuer(issuer);
	}

	private void buildSubject(Map<String, String> runtimeProperties,
			AssertionType assertion) {
		SubjectType subject = getSamlContext().getSubjectBuilder().
				build(runtimeProperties);
		assertion.setSubject(subject);
	}

	private void buildConditions(Map<String, String> runtimeProperties,
			AssertionType assertion) {
		ConditionsType conditions = getSamlContext().getConditionsBuilder().
				build(runtimeProperties);
		assertion.setConditions(conditions);
	}

	private void buildAuthnStatement(Map<String, String> runtimeProperties,
			AssertionType assertion) {
		List<AuthnStatementType> authnStatements = getSamlContext().
				getAuthnStatementBuilder().build(runtimeProperties);
		assertion.getStatementOrAuthnStatementOrAuthzDecisionStatement().addAll(authnStatements);
	}
			
}

