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
package de.groothues.mysaml.protocol.impl;

import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.impl.SamlContextAware;
import de.groothues.mysaml.impl.DomHelper;
import de.groothues.mysaml.protocol.ResponseBuilder;
import de.groothues.mysaml.protocol.ResponseType;

public class ResponseBuilderImpl extends SamlContextAware implements ResponseBuilder {
	
    public static final String PREFIX_XMLDSIG = "ds";
    public static final String PREFIX_XMLENC = "xenc";
    public static final String PREFIX_SAMLP = "samlp";
    
	public ResponseBuilderImpl(SamlContext samlContext) {
		super(samlContext);
	}

	public Document build(Map<String, String> runtimeProperties) {
        ResponseType response = buildResponse(runtimeProperties);
        Document responseDoc = marshal(response);
        
        Document assertionDoc = buildAssertion(runtimeProperties);
        Node importedAssertion = responseDoc.importNode(
                assertionDoc.getDocumentElement(), true);
        responseDoc.getDocumentElement().appendChild(importedAssertion);
		return responseDoc;
	}

    @Override
	public Document marshal(ResponseType response) {
		try {
	        Marshaller marshaller = jaxbContext.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marshaller.setProperty( Marshaller.JAXB_SCHEMA_LOCATION, 
	        		"urn:oasis:names:tc:SAML:2.0:protocol saml-schema-protocol-2.0.xsd");
	        
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
	        
	        JAXBElement<ResponseType> responseElement = 
	                getProtocolObjectFactory().createResponse(response);
	        Document responseDoc = DomHelper.createNewDocument();
	        marshaller.marshal(responseElement, responseDoc);
	        return responseDoc;
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ResponseType unmarshal(Document responseDoc) {
		try {
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		
			@SuppressWarnings("unchecked")
			JAXBElement<ResponseType> responseElement = 
				(JAXBElement<ResponseType>)unmarshaller.unmarshal(responseDoc);
			return responseElement.getValue();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}	
	}
	
	private ResponseType buildResponse(Map<String, String> runtimeProperties) {
		ResponseType response = getProtocolObjectFactory().createResponseType();
		response.setVersion(getProperty(SAML_VERSION_KEY, runtimeProperties));
		response.setID(UUID.randomUUID().toString());
		response.setIssueInstant(createXmlCalendar());
		return response;
	}

    private Document buildAssertion(Map<String, String> runtimeProperties) {
        return getSamlContext().getSignedAssertionBuilder().build(runtimeProperties);
    }
	
}

