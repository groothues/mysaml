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
import de.groothues.mysaml.assertion.AudienceRestrictionBuilder;
import de.groothues.mysaml.assertion.AudienceRestrictionType;
import de.groothues.mysaml.impl.SamlContextAware;

public class AudienceRestrictionBuilderImpl extends SamlContextAware implements AudienceRestrictionBuilder {

	public AudienceRestrictionBuilderImpl(SamlContext samlContext) {
		super(samlContext);
	}

	@Override
	public List<AudienceRestrictionType> build(Map<String, String> runtimeProperties) {
		List<AudienceRestrictionType> audienceRestrictions = new ArrayList<AudienceRestrictionType>();
		
		int number = getNumberOfAudienceRestrictions(runtimeProperties);
		
		for(int index = 1; index <= number; index++) {
			AudienceRestrictionType audienceRestriction = getAssertionObjectFactory().createAudienceRestrictionType();
			
			String audience = getIndexedProperty(CONDITIONS_AUDIENCERESTRICTION_AUDIENCE_KEY, 
					runtimeProperties, index);
			audienceRestriction.getAudience().add(audience);
			
			audienceRestrictions.add(audienceRestriction);
		}
		
		return audienceRestrictions;
	}

	private int getNumberOfAudienceRestrictions(Map<String, String> runtimeProperties) {
		String numberString = getProperty(CONDITIONS_AUDIENCERESTRICTION_NUMBER_OF_KEY, runtimeProperties);
		return Integer.parseInt(numberString);
	}

}
