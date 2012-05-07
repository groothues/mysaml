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

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.AudienceRestrictionType;
import de.groothues.mysaml.assertion.ConditionsBuilder;
import de.groothues.mysaml.assertion.ConditionsType;
import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.impl.SamlContextAware;

public class ConditionsBuilderImpl extends SamlContextAware implements ConditionsBuilder {

	public ConditionsBuilderImpl(SamlContext samlContext) {
		super(samlContext);
	}

	@Override
	public ConditionsType build(Map<String, String> runtimeProperties) {
		ConditionsType conditions = getAssertionObjectFactory().createConditionsType();

		XMLGregorianCalendar notBefore = createXmlCalendar();
		int validityPeriodInSeconds = Integer.parseInt(getProperty(
				PropertyKeys.CONDITIONS_VALIDITY_PERIOD_IN_SECONDS_KEY, runtimeProperties));
		Duration validityPeriod = createDuration(validityPeriodInSeconds);
		XMLGregorianCalendar notOnOrAfter = createXmlCalendar();
		notOnOrAfter.add(validityPeriod);
		
		conditions.setNotBefore(notBefore);
		conditions.setNotOnOrAfter(notOnOrAfter);
		
		List<AudienceRestrictionType> audienceRestrictions = getSamlContext().
				getAudienceRestrictionBuilder().build(runtimeProperties);
		conditions.getConditionOrAudienceRestrictionOrOneTimeUse().addAll(audienceRestrictions);
		
		return conditions;
	}

}
