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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;

public class DomHelper {

	public static Document createNewDocument() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder db;
		try {
			db = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
        return db.newDocument();
	}
	
    public static String writeToString(Node node) {
    	try {
	    	DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();

	    	DOMImplementationLS impl = 
	    	    (DOMImplementationLS)registry.getDOMImplementation("LS");
	    	LSSerializer writer = impl.createLSSerializer();
	    	return writer.writeToString(node);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}	

    public static Document parseXml(String xml) {
        try {
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();

            DOMImplementationLS impl = 
                (DOMImplementationLS)registry.getDOMImplementation("LS");
            LSInput input = impl.createLSInput();
            input.setStringData(xml);
            LSParser parser = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, 
                    null);
           
            return parser.parse(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }   
    

}
