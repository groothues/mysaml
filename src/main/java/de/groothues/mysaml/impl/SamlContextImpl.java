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
package de.groothues.mysaml.impl;

import java.util.Collections;
import java.util.Map;

import org.w3c.dom.Document;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.AssertionBuilder;
import de.groothues.mysaml.assertion.AssertionType;
import de.groothues.mysaml.assertion.AudienceRestrictionBuilder;
import de.groothues.mysaml.assertion.AuthnContextBuilder;
import de.groothues.mysaml.assertion.AuthnStatementBuilder;
import de.groothues.mysaml.assertion.ConditionsBuilder;
import de.groothues.mysaml.assertion.IssuerBuilder;
import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.assertion.SignedAssertionBuilder;
import de.groothues.mysaml.assertion.SubjectBuilder;
import de.groothues.mysaml.assertion.SubjectConfirmationBuilder;
import de.groothues.mysaml.assertion.SubjectConfirmationDataBuilder;
import de.groothues.mysaml.assertion.SubjectNameIDBuilder;
import de.groothues.mysaml.assertion.impl.AssertionBuilderImpl;
import de.groothues.mysaml.assertion.impl.AudienceRestrictionBuilderImpl;
import de.groothues.mysaml.assertion.impl.AuthnContextBuilderImpl;
import de.groothues.mysaml.assertion.impl.AuthnStatementBuilderImpl;
import de.groothues.mysaml.assertion.impl.ConditionsBuilderImpl;
import de.groothues.mysaml.assertion.impl.IssuerBuilderImpl;
import de.groothues.mysaml.assertion.impl.SignedAssertionBuilderImpl;
import de.groothues.mysaml.assertion.impl.SubjectBuilderImpl;
import de.groothues.mysaml.assertion.impl.SubjectConfirmationBuilderImpl;
import de.groothues.mysaml.assertion.impl.SubjectConfirmationDataBuilderImpl;
import de.groothues.mysaml.assertion.impl.SubjectNameIDBuilderImpl;
import de.groothues.mysaml.protocol.ResponseBuilder;
import de.groothues.mysaml.protocol.impl.ResponseBuilderImpl;
import de.groothues.mysaml.signature.DocumentSigner;
import de.groothues.mysaml.signature.SignatureKeyStore;
import de.groothues.mysaml.signature.SignatureTrustStore;
import de.groothues.mysaml.signature.impl.DocumentSignerImpl;
import de.groothues.mysaml.signature.impl.SignatureKeyStoreImpl;
import de.groothues.mysaml.signature.impl.SignatureTrustStoreImpl;
import de.groothues.mysaml.validator.Validator;
import de.groothues.mysaml.validator.impl.AssertionAgeValidator;
import de.groothues.mysaml.validator.impl.AssertionAudienceValidator;
import de.groothues.mysaml.validator.impl.AssertionValidityPeriodValidator;
import de.groothues.mysaml.validator.impl.SigatureValidator;

public class SamlContextImpl implements SamlContext, PropertyKeys {
	private static final String DEFAULT_PROPERTIES_FILE = "samlDefault.properties";
	
	private Map<String, String> properties;

	private AssertionBuilder assertionBuilder;

	private SignedAssertionBuilder signedAssertionBuilder;

	private IssuerBuilder issuerBuilder;

	private SubjectBuilder subjectBuilder;

	private SubjectNameIDBuilder nameIDBuilder;

	private SubjectConfirmationBuilder subjectConfirmationBuilder;

	private SubjectConfirmationDataBuilder subjectConfirmationDataBuilder;

	private ConditionsBuilder conditionsBuilder;

	private AudienceRestrictionBuilder audienceRestrictionBuilder;

	private AuthnStatementBuilder authnStatementBuilder;

	private AuthnContextBuilder authnContextBuilder;

	private DocumentSigner documentSigner;

	private Validator<Document> documentValidator;

	private SignatureKeyStoreImpl signatureKeyStore;

	private SignatureTrustStore signatureTrustStore;

    private ResponseBuilder responseBuilder;

    private Validator<AssertionType> assertionAgeValidator;

    private Validator<AssertionType> assertionValidityPeriodValidator;

    private Validator<AssertionType> assertionAudienceValidator;
    
	public SamlContextImpl() {
		this(null);
	}

	public SamlContextImpl(Map<String, String> overwriteProperties) {
		Map<String, String> defaultProperties = PropertiesHelper.toStringMap(
				PropertiesHelper.loadProperties(DEFAULT_PROPERTIES_FILE));
		
		if (overwriteProperties != null) {
			defaultProperties.putAll(overwriteProperties);
		}

		this.properties = Collections.unmodifiableMap(defaultProperties);
		
		initializeBuilders();
	}

	@Override
	public Map<String, String> getProperties() {
		return this.properties;
	}

	@Override
	public String getProperty(String key) {
		return this.properties.get(key);
	}

	@Override
	public AssertionBuilder getAssertionBuilder() {
		return this.assertionBuilder;
	} 

	@Override
	public SignedAssertionBuilder getSignedAssertionBuilder() {
		return this.signedAssertionBuilder;
	}

    @Override
    public ResponseBuilder getResponseBuilder() {
        return this.responseBuilder;
    }
	
	@Override
	public IssuerBuilder getIssuerBuilder() {
		return this.issuerBuilder;
	}

	@Override
	public SubjectBuilder getSubjectBuilder() {
		return this.subjectBuilder;
	}

	@Override
	public SubjectNameIDBuilder getNameIDBuilder() {
		return this.nameIDBuilder;
	}

	@Override
	public SubjectConfirmationBuilder getSubjectConfirmationBuilder() {
		return this.subjectConfirmationBuilder;
	}
	
	@Override
	public SubjectConfirmationDataBuilder getSubjectConfirmationDataBuilder() {
		return this.subjectConfirmationDataBuilder;
	}

	@Override
	public ConditionsBuilder getConditionsBuilder() {
		return this.conditionsBuilder;
	}

	@Override
	public AudienceRestrictionBuilder getAudienceRestrictionBuilder() {
		return this.audienceRestrictionBuilder;
	}

	@Override
	public AuthnStatementBuilder getAuthnStatementBuilder() {
		return this.authnStatementBuilder;
	}

	@Override
	public AuthnContextBuilder getAuthnContextBuilder() {
		return this.authnContextBuilder;
	}

	@Override
	public DocumentSigner getDocumentSigner() {
		return this.documentSigner;
	}

	@Override
	public Validator<Document> getSignatureValidator() {
		return this.documentValidator;
	}

    @Override
    public Validator<AssertionType> getAssertionAgeValidator() {
        return this.assertionAgeValidator;
    }

    @Override
    public Validator<AssertionType> getAssertionValidityPeriodValidator() {
        return this.assertionValidityPeriodValidator;
    }

    @Override
    public Validator<AssertionType> getAssertionAudienceValidator() {
        return this.assertionAudienceValidator;
    }
    
	@Override
	public SignatureKeyStore getSignatureKeyStore() {
		return this.signatureKeyStore;
	}

	@Override
	public SignatureTrustStore getSignatureTrustStore() {
		return this.signatureTrustStore;
	}	

	private void initializeBuilders() {
		this.assertionBuilder = new AssertionBuilderImpl(this);
		this.signedAssertionBuilder = new SignedAssertionBuilderImpl(this);
		this.responseBuilder = new ResponseBuilderImpl(this);
		this.issuerBuilder = new IssuerBuilderImpl(this);
		this.subjectBuilder = new SubjectBuilderImpl(this);
		this.nameIDBuilder = new SubjectNameIDBuilderImpl(this);
		this.subjectConfirmationBuilder = new SubjectConfirmationBuilderImpl(this);
		this.subjectConfirmationDataBuilder = new SubjectConfirmationDataBuilderImpl(this);
		this.conditionsBuilder = new ConditionsBuilderImpl(this);
		this.audienceRestrictionBuilder = new AudienceRestrictionBuilderImpl(this);
		this.authnStatementBuilder = new AuthnStatementBuilderImpl(this);
		this.authnContextBuilder = new AuthnContextBuilderImpl(this);
		this.documentSigner = new DocumentSignerImpl(this);
		this.documentValidator = new SigatureValidator(this);
		this.assertionAgeValidator = new AssertionAgeValidator(this);
		this.assertionValidityPeriodValidator = new AssertionValidityPeriodValidator(this);
		this.assertionAudienceValidator = new AssertionAudienceValidator(this);
		this.signatureKeyStore = new SignatureKeyStoreImpl(this);
		this.signatureTrustStore = new SignatureTrustStoreImpl(this);
	}
		
}
