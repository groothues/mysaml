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

public class AssertionValidityPeriodValidator extends AssertionValidator {

    public AssertionValidityPeriodValidator(SamlContext samlContext) {
        super(samlContext);
    }

    @Override
    public ValidationResult validate(AssertionType assertion, 
            Map<String, String> runtimeProperties) {
        
        boolean isEnabled = Boolean.parseBoolean(getProperty(VALIDATOR_VALIDITYPERIOD_ENABLED, runtimeProperties));
        
        if (isEnabled) {
            if (!validateValidityPeriodStart(assertion)) {
                return validationFailureResult("SAML Assertion validity period has not yet started");
            }
            if (!validateValidityPeriodEnd(assertion)) {
                return validationFailureResult("SAML Assertion validity period has expired");
            }
        }

        return validationSuccessResult();
    }

    private boolean validateValidityPeriodStart(AssertionType assertion) {
        long nowInMillis = new GregorianCalendar().getTimeInMillis();
        
        XMLGregorianCalendar notBefore = assertion.getConditions().getNotBefore();
        long notBeforeInMillis = notBefore.toGregorianCalendar().getTimeInMillis();
        
        if (nowInMillis < notBeforeInMillis) {
            return false;
        }
        
        return true;        
    }
     
    private boolean validateValidityPeriodEnd(AssertionType assertion) {
        long nowInMillis = new GregorianCalendar().getTimeInMillis();
        
        XMLGregorianCalendar notOnOrAfter = assertion.getConditions().getNotOnOrAfter();
        long notOnOrAfterInMillis = notOnOrAfter.toGregorianCalendar().getTimeInMillis();
        
        if (nowInMillis >= notOnOrAfterInMillis) {
            return false;
        }
        
        return true;
    }
    
}
