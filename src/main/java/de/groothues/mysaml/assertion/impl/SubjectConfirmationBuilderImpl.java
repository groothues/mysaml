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
import de.groothues.mysaml.assertion.SubjectConfirmationBuilder;
import de.groothues.mysaml.assertion.SubjectConfirmationDataType;
import de.groothues.mysaml.assertion.SubjectConfirmationType;
import de.groothues.mysaml.impl.SamlContextAware;

public class SubjectConfirmationBuilderImpl extends SamlContextAware implements SubjectConfirmationBuilder {

	public SubjectConfirmationBuilderImpl(SamlContext samlContext) {
		super(samlContext);
	}

	@Override
	public List<SubjectConfirmationType> build(Map<String, String> runtimeProperties) {
		List<SubjectConfirmationType> subjectConfirmations = new ArrayList<SubjectConfirmationType>();
		
		int number = getNumberOfSubjectConfirmations(runtimeProperties);
		
		for(int index = 1; index <= number; index++) {
			SubjectConfirmationType subjectConfirmation = getAssertionObjectFactory().createSubjectConfirmationType();
			
			subjectConfirmation.setMethod(getIndexedProperty(
					SUBJECT_CONFIRMATION_METHOD_KEY, runtimeProperties, index));
			
			SubjectConfirmationDataType subjectConfirmationData = 
				getSamlContext().getSubjectConfirmationDataBuilder().build(runtimeProperties, index);
			subjectConfirmation.setSubjectConfirmationData(subjectConfirmationData);
			
			subjectConfirmations.add(subjectConfirmation);
		}
		
		return subjectConfirmations;
	}

	private int getNumberOfSubjectConfirmations(Map<String, String> runtimeProperties) {
		String numberString = getProperty(SUBJECT_CONFIRMATIONS_NUMBER_OF_KEY, runtimeProperties);
		return Integer.parseInt(numberString);
	}

}
