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

import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.AssertionType;
import de.groothues.mysaml.validator.ValidationResult;

public class AssertionAgeValidator extends AssertionValidator {

    public AssertionAgeValidator(SamlContext samlContext) {
        super(samlContext);
    }

    @Override
    public ValidationResult validate(AssertionType assertion, 
            Map<String, String> runtimeProperties) {
        
        boolean isEnabled = Boolean.parseBoolean(getProperty(VALIDATOR_AGE_ENABLED, runtimeProperties));
        
        if (isEnabled && !validateAssertionAge(assertion, runtimeProperties)) {
            return validationFailureResult("SAML Assertion maximum age has expired");
        }

        return validationSuccessResult();
    }

    private boolean validateAssertionAge(AssertionType assertion,
            Map<String, String> runtimeProperties) {
        long nowInMillis = new GregorianCalendar().getTimeInMillis();
        
        XMLGregorianCalendar issued = assertion.getIssueInstant();
        long issuedInMillis = issued.toGregorianCalendar().getTimeInMillis();
        
        long maxAgeInMillis = Long.parseLong(getProperty(VALIDATOR_AGE_MAXAGESECONDS, runtimeProperties)) * 1000;
        if (nowInMillis - issuedInMillis <= maxAgeInMillis) {
            return true;
        }
        return false;
    }
    
}
