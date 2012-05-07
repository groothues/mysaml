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

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.IssuerBuilder;
import de.groothues.mysaml.assertion.NameIDType;
import de.groothues.mysaml.impl.SamlContextAware;

public class IssuerBuilderImpl extends SamlContextAware implements IssuerBuilder {

	public IssuerBuilderImpl(SamlContext samlContext) {
		super(samlContext);
	}

	@Override
	public NameIDType build(Map<String, String> runtimeProperties) {
		NameIDType issuer = getAssertionObjectFactory().createNameIDType();
		String issuerValue = getProperty(ISSUER_VALUE_KEY, runtimeProperties);
		issuer.setValue(issuerValue);
		return issuer;
	}
	
}
