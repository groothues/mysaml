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
package de.groothues.mysaml.validator.impl;

import java.util.Map;

import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.impl.SamlContextAware;
import de.groothues.mysaml.signature.impl.X509KeySelector;
import de.groothues.mysaml.validator.Validator;
import de.groothues.mysaml.validator.ValidationResult;

public class SigatureValidator extends SamlContextAware 
        implements Validator<Document> {

	public SigatureValidator(SamlContext samlContext) {
		super(samlContext);
	}

	/* (non-Javadoc)
	 * @see de.groothues.mysaml.signature.impl.DocumentValidator#validate(org.w3c.dom.Document)
	 */
	@Override
	public ValidationResult validate(Document doc) {
		// Find Signature element.
		NodeList nl =
		    doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
		if (nl.getLength() == 0) {
		    return new ValidationResult(false, "Cannot find Signature element");
		}

		// Create a DOMValidateContext and specify a KeySelector
		// and document context.
		DOMValidateContext valContext = new DOMValidateContext
		    (new X509KeySelector(getSamlContext()), nl.item(0));

		// Unmarshal the XMLSignature.
		XMLSignatureFactory xmlSignatureFactory = XMLSignatureFactory.getInstance("DOM");
		try {
			XMLSignature signature = xmlSignatureFactory.unmarshalXMLSignature(valContext);

			// Validate the XMLSignature.
			boolean coreValidity = signature.validate(valContext);
			
			if (coreValidity) {
				return new ValidationResult(true, "SAML Assertion signature is valid");
			}
			
		} catch (Exception e) {
			return new ValidationResult(false, 
			        "Failure during signature validation: " + e.getMessage());
		}

		return new ValidationResult(false, "SAML Assertion signature is invalid");
	}

    @Override
    public ValidationResult validate(Document doc,
            Map<String, String> runtimeProperties) {
        // TODO Evaluate properties
        return validate(doc);
    }
	
}
