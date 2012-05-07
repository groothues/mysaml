package de.groothues.mysaml.impl;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.AssertionBuilder;
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
import de.groothues.mysaml.signature.DocumentSigner;
import de.groothues.mysaml.validator.Validator;

public class SamlContextImplGetBuildersTest {

	private SamlContext samlContext;
	
	@Before
	public void setUp() throws Exception {
		samlContext = new SamlContextImpl();
	}
	
	@Test
	public void testGetAssertionBuilderSingleton() {
		AssertionBuilder assertionBuilder1 = samlContext.getAssertionBuilder();
		assertNotNull(assertionBuilder1);
		AssertionBuilder assertionBuilder2 = samlContext.getAssertionBuilder();
		assertSame(assertionBuilder1, assertionBuilder2);
	}

	@Test
	public void testGetSignedAssertionBuilderSingleton() {
		SignedAssertionBuilder signedAssertionBuilder1 = samlContext.getSignedAssertionBuilder();
		assertNotNull(signedAssertionBuilder1);
		SignedAssertionBuilder signedAssertionBuilder2 = samlContext.getSignedAssertionBuilder();
		assertSame(signedAssertionBuilder1, signedAssertionBuilder2);
	}
	
	@Test
	public void testGetIssuerBuilderSingleton() {
		IssuerBuilder issuerBuilder1 = samlContext.getIssuerBuilder();
		assertNotNull(issuerBuilder1);
		IssuerBuilder issuerBuilder2 = samlContext.getIssuerBuilder();
		assertSame(issuerBuilder1, issuerBuilder2);
	}

	@Test
	public void testGetSubjectBuilderSingleton() {
		SubjectBuilder subjectBuilder1 = samlContext.getSubjectBuilder();
		assertNotNull(subjectBuilder1);
		SubjectBuilder subjectBuilder2 = samlContext.getSubjectBuilder();
		assertSame(subjectBuilder1, subjectBuilder2);
	}

	@Test
	public void testGetNameIDBuilderSingleton() {
		SubjectNameIDBuilder nameIDBuilder1 = samlContext.getNameIDBuilder();
		assertNotNull(nameIDBuilder1);
		SubjectNameIDBuilder nameIDBuilder2 = samlContext.getNameIDBuilder();
		assertSame(nameIDBuilder1, nameIDBuilder2);
	}

	@Test
	public void testGetSubjectConfirmationBuilderSingleton() {
		SubjectConfirmationBuilder subjectConfirmationBuilder1 = 
			samlContext.getSubjectConfirmationBuilder();
		assertNotNull(subjectConfirmationBuilder1);
		SubjectConfirmationBuilder subjectConfirmationBuilder2 = 
			samlContext.getSubjectConfirmationBuilder();
		assertSame(subjectConfirmationBuilder1, subjectConfirmationBuilder2);
	}

	@Test
	public void testGetSubjectConfirmationDataBuilderSingleton() {
		SubjectConfirmationDataBuilder subjectConfirmationDataBuilder1 = 
			samlContext.getSubjectConfirmationDataBuilder();
		assertNotNull(subjectConfirmationDataBuilder1);
		SubjectConfirmationDataBuilder subjectConfirmationDataBuilder2 = 
			samlContext.getSubjectConfirmationDataBuilder();
		assertSame(subjectConfirmationDataBuilder1, subjectConfirmationDataBuilder2);
	}

	@Test
	public void testGetConditionsBuilderSingleton() {
		ConditionsBuilder conditionsBuilder1 = 
			samlContext.getConditionsBuilder();
		assertNotNull(conditionsBuilder1);
		ConditionsBuilder conditionsBuilder2 = 
			samlContext.getConditionsBuilder();
		assertSame(conditionsBuilder1, conditionsBuilder2);
	}

	@Test
	public void testGetAudienceRestrictionBuilderSingleton() {
		AudienceRestrictionBuilder audienceRestrictionBuilder1 = 
			samlContext.getAudienceRestrictionBuilder();
		assertNotNull(audienceRestrictionBuilder1);
		AudienceRestrictionBuilder audienceRestrictionBuilder2 = 
			samlContext.getAudienceRestrictionBuilder();
		assertSame(audienceRestrictionBuilder1, audienceRestrictionBuilder2);
	}

	@Test
	public void testGetAuthnStatementBuilderSingleton() {
		AuthnStatementBuilder authnStatementBuilder1 = 
			samlContext.getAuthnStatementBuilder();
		assertNotNull(authnStatementBuilder1);
		AuthnStatementBuilder authnStatementBuilder2 = 
			samlContext.getAuthnStatementBuilder();
		assertSame(authnStatementBuilder1, authnStatementBuilder2);
	}

	@Test
	public void testGetAuthnContextBuilderSingleton() {
		AuthnContextBuilder authnContextBuilder1 = 
			samlContext.getAuthnContextBuilder();
		assertNotNull(authnContextBuilder1);
		AuthnContextBuilder authnContextBuilder2 = 
			samlContext.getAuthnContextBuilder();
		assertSame(authnContextBuilder1, authnContextBuilder2);
	}

	@Test
	public void testGetDocumentSignerSingleton() {
		DocumentSigner documentSigner1 = 
			samlContext.getDocumentSigner();
		assertNotNull(documentSigner1);
		DocumentSigner documentSigner2 = 
			samlContext.getDocumentSigner();
		assertSame(documentSigner1, documentSigner2);
	}

	@Test
	public void testGetDocumentValidatorSingleton() {
		Validator<Document> documentValidator1 = 
			samlContext.getSignatureValidator();
		assertNotNull(documentValidator1);
		Validator<Document> documentValidator2 = 
			samlContext.getSignatureValidator();
		assertSame(documentValidator1, documentValidator2);
	}
	
}
