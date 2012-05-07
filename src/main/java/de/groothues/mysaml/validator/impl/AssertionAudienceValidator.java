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

import java.util.List;
import java.util.Map;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.AssertionType;
import de.groothues.mysaml.assertion.AudienceRestrictionType;
import de.groothues.mysaml.assertion.ConditionAbstractType;
import de.groothues.mysaml.validator.ValidationResult;

public class AssertionAudienceValidator extends AssertionValidator {

    public AssertionAudienceValidator(SamlContext samlContext) {
        super(samlContext);
    }

    @Override
    public ValidationResult validate(AssertionType assertion, 
            Map<String, String> runtimeProperties) {
        
        boolean isEnabled = Boolean.parseBoolean(getProperty(VALIDATOR_AUDIENCE_ENABLED, runtimeProperties));
        
        if (isEnabled && !validateAssertionAudience(assertion, runtimeProperties)) {
            return validationFailureResult("SAML Assertion has invalid audience");
        }

        return validationSuccessResult();
    }

    private boolean validateAssertionAudience(AssertionType assertionType,
            Map<String, String> runtimeProperties) {
        String intendedAudience = getProperty(VALIDATOR_AUDIENCE, runtimeProperties);
        
        List<ConditionAbstractType> conditions = 
                assertionType.getConditions().getConditionOrAudienceRestrictionOrOneTimeUse();
        for (ConditionAbstractType condition: conditions) {
            if (condition instanceof AudienceRestrictionType) {
                List<String> audiences = ((AudienceRestrictionType)condition).getAudience();
                for (String audience: audiences) {
                    if (audience.equals(intendedAudience)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
