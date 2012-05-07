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

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.NameIDType;
import de.groothues.mysaml.assertion.SubjectBuilder;
import de.groothues.mysaml.assertion.SubjectConfirmationType;
import de.groothues.mysaml.assertion.SubjectType;
import de.groothues.mysaml.impl.SamlContextAware;

public class SubjectBuilderImpl extends SamlContextAware implements SubjectBuilder {

	public SubjectBuilderImpl(SamlContext samlContext) {
		super(samlContext);
	}

	@Override
	public SubjectType build(Map<String, String> runtimeProperties) {
		SubjectType subject = getAssertionObjectFactory().createSubjectType();
		
		NameIDType nameID = getSamlContext().getNameIDBuilder().build(runtimeProperties);
		subject.setNameID(nameID);
		
		List<SubjectConfirmationType> subjectConfirmations = getSamlContext().
				getSubjectConfirmationBuilder().build(runtimeProperties);
		subject.getSubjectConfirmation().addAll(subjectConfirmations);
		
		return subject;
	}

}
