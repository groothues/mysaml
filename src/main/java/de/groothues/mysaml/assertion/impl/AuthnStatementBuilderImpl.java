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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.AuthnContextType;
import de.groothues.mysaml.assertion.AuthnStatementBuilder;
import de.groothues.mysaml.assertion.AuthnStatementType;
import de.groothues.mysaml.impl.SamlContextAware;

public class AuthnStatementBuilderImpl extends SamlContextAware implements AuthnStatementBuilder {

	public AuthnStatementBuilderImpl(SamlContext samlContext) {
		super(samlContext);
	}

	@Override
	public List<AuthnStatementType> build(Map<String, String> runtimeProperties) {
		List<AuthnStatementType> authnStatements = new ArrayList<AuthnStatementType>();
		
		int number = getNumberOfAuthnStatements(runtimeProperties);
		
		for(int index = 1; index <= number; index++) {
			AuthnStatementType authnStatement = getAssertionObjectFactory().createAuthnStatementType();
			
			authnStatement.setAuthnInstant(createXmlCalendar());

			AuthnContextType authnContext = 
				getSamlContext().getAuthnContextBuilder().build(runtimeProperties, index);
			authnStatement.setAuthnContext(authnContext);
			
			authnStatements.add(authnStatement);
		}
		
		return authnStatements;
	}

	private int getNumberOfAuthnStatements(Map<String, String> runtimeProperties) {
		String numberString = getProperty(AUTHNSTATEMENT_NUMBER_OF_KEY, runtimeProperties);
		return Integer.parseInt(numberString);
	}

}
