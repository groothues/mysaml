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
package de.groothues.mysaml;

import java.util.Map;

import org.w3c.dom.Document;

import de.groothues.mysaml.assertion.AssertionBuilder;
import de.groothues.mysaml.assertion.AssertionType;
import de.groothues.mysaml.assertion.AudienceRestrictionBuilder;
import de.groothues.mysaml.assertion.AuthnContextBuilder;
import de.groothues.mysaml.assertion.AuthnStatementBuilder;
import de.groothues.mysaml.assertion.ConditionsBuilder;
import de.groothues.mysaml.assertion.IssuerBuilder;
import de.groothues.mysaml.assertion.SignedAssertionBuilder;
import de.groothues.mysaml.assertion.SubjectBuilder;
import de.groothues.mysaml.assertion.SubjectConfirmationBuilder;
import de.groothues.mysaml.assertion.SubjectConfirmationDataBuilder;
import de.groothues.mysaml.assertion.SubjectNameIDBuilder;
import de.groothues.mysaml.protocol.ResponseBuilder;
import de.groothues.mysaml.signature.DocumentSigner;
import de.groothues.mysaml.signature.SignatureKeyStore;
import de.groothues.mysaml.signature.SignatureTrustStore;
import de.groothues.mysaml.validator.Validator;

public interface SamlContext {

	public Map<String, String> getProperties();
	
	public String getProperty(String key);
	
	public AssertionBuilder getAssertionBuilder();

	public SignedAssertionBuilder getSignedAssertionBuilder();
	
	public ResponseBuilder getResponseBuilder();
	
	public IssuerBuilder getIssuerBuilder();

	public SubjectBuilder getSubjectBuilder();

	public SubjectNameIDBuilder getNameIDBuilder();

	public SubjectConfirmationBuilder getSubjectConfirmationBuilder();

	public SubjectConfirmationDataBuilder getSubjectConfirmationDataBuilder();

	public ConditionsBuilder getConditionsBuilder();

	public AudienceRestrictionBuilder getAudienceRestrictionBuilder();

	public AuthnStatementBuilder getAuthnStatementBuilder();

	public AuthnContextBuilder getAuthnContextBuilder();

	public DocumentSigner getDocumentSigner();

	public Validator<Document> getSignatureValidator();
	
	public Validator<AssertionType> getAssertionAgeValidator();
	
	public Validator<AssertionType> getAssertionValidityPeriodValidator();
	
	public Validator<AssertionType> getAssertionAudienceValidator();

	public SignatureKeyStore getSignatureKeyStore();

	public SignatureTrustStore getSignatureTrustStore();

}