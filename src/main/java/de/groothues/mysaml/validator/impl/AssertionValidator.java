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

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.AssertionType;
import de.groothues.mysaml.impl.SamlContextAware;
import de.groothues.mysaml.validator.ValidationResult;
import de.groothues.mysaml.validator.Validator;

public abstract class AssertionValidator extends SamlContextAware implements Validator<AssertionType> {

    public AssertionValidator(SamlContext samlContext) {
        super(samlContext);
    }

    @Override
    public ValidationResult validate(AssertionType object) {
        return validate(object, null);
    }
    
    private static final ValidationResult VALIDATION_SUCCESS_RESULT = 
            new ValidationResult(true, "SAML Assertion validation successful");
    
    protected ValidationResult validationSuccessResult() {
        return VALIDATION_SUCCESS_RESULT;
    }

    protected ValidationResult validationFailureResult(String message) {
        return new ValidationResult(false, message);
    }

}
