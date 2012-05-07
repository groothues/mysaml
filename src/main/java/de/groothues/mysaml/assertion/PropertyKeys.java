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
package de.groothues.mysaml.assertion;

public interface PropertyKeys {
	
	// Assertion
	public static final String SAML_VERSION_KEY = "assertion.saml.version";

	// Issuer
	public static final String ISSUER_VALUE_KEY = "assertion.issuer.value";
	
	// Subject
	
	// Subject/NameID
	public static final String SUBJECT_NAMEID_FORMAT_KEY = 
		"assertion.subject.nameid.format";
	public static final String SUBJECT_NAMEID_VALUE_KEY = 
		"assertion.subject.nameid.value";
	
	// Subject/SubjectConfirmation
	public static final String SUBJECT_CONFIRMATIONS_NUMBER_OF_KEY = 
		"assertion.subject.subjectconfirmation.numberof";
	public static final String SUBJECT_CONFIRMATION_METHOD_KEY = 
		"assertion.subject.subjectconfirmation.method";

	// Subject/SubjectConfirmation/SubjectConfirmationData
	public static final String SUBJECT_CONFIRMATION_DATA_RECIPIENT_KEY = 
		"assertion.subject.subjectconfirmation.subjectconfirmationdata.recipient";

	// Conditions
	public static final String CONDITIONS_VALIDITY_PERIOD_IN_SECONDS_KEY = 
		"assertion.conditions.validity.period.seconds"; 
	
	// Conditions/AudienceRestriction
	public static final String CONDITIONS_AUDIENCERESTRICTION_NUMBER_OF_KEY = 
		"assertion.conditions.audiencerestriction.numberof";
	public static final String CONDITIONS_AUDIENCERESTRICTION_AUDIENCE_KEY = 
		"assertion.conditions.audiencerestriction.audience";
	
	// AuthnStatement
	public static final String AUTHNSTATEMENT_NUMBER_OF_KEY = 
		"assertion.authnstatement.numberof";
	
	// AuthnStatement/AuthnContext/AuthnContextClassRef
	public static final String AUTHNSTATEMENT_AUTHNCONTEXT_AUTHNCONTEXTCLASSREF_KEY = 
		"assertion.authnstatement.authncontext.authncontextclassref";

	// Keystore
	public static final String KEYSTORE_PASSWORD = "keystore.password";
	public static final String KEYSTORE_LOCATION = "keystore.location";
	public static final String KEYSTORE_KEY_PASSWORD = "keystore.key.password";
	public static final String KEYSTORE_KEY_ALIAS = "keystore.key.alias";
	public static final String KEYSTORE_CERTIFICATE_ALIAS = "keystore.certificate.alias";

	// Truststore
	public static final String TRUSTSTORE_PASSWORD = "truststore.password";
	public static final String TRUSTSTORE_LOCATION = "truststore.location";
	
	// Assertion Validators
	public static final String VALIDATOR_AGE_ENABLED = "assertion.validator.age.enabled";
	public static final String VALIDATOR_AGE_MAXAGESECONDS = "assertion.validator.age.maxageseconds";
	public static final String VALIDATOR_VALIDITYPERIOD_ENABLED = "assertion.validator.validityperiod.enabled";
	public static final String VALIDATOR_AUDIENCE_ENABLED = "assertion.validator.audience.enabled";
	public static final String VALIDATOR_AUDIENCE = "assertion.validator.audience";
}
