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

import java.util.Map;

import org.w3c.dom.Document;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.AssertionType;
import de.groothues.mysaml.assertion.SignedAssertionBuilder;
import de.groothues.mysaml.impl.SamlContextAware;

public class SignedAssertionBuilderImpl extends SamlContextAware 
	implements SignedAssertionBuilder {
	
	public static final String PREFIX_XMLDSIG = "ds";
	public static final String PREFIX_XMLENC = "xenc";
	public static final String PREFIX_SAMLP = "samlp";

	public SignedAssertionBuilderImpl(SamlContext samlContext) {
		super(samlContext);
	}

	@Override
	public Document build(Map<String, String> runtimeProperties) {
		AssertionType assertion = getSamlContext().getAssertionBuilder().build(runtimeProperties);
		Document assertionDoc = marshal(assertion);
		
		getSamlContext().getDocumentSigner().sign(assertionDoc, runtimeProperties);
		
		return assertionDoc;
	}

	@Override
	public Document marshal(AssertionType assertion) {
		return getSamlContext().getAssertionBuilder().marshal(assertion);
	}

	@Override
	public AssertionType unmarshal(Document doc) {
		return getSamlContext().getAssertionBuilder().unmarshal(doc);
	}    	

	

}
