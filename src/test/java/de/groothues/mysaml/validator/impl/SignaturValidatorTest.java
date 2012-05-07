package de.groothues.mysaml.validator.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.AssertionType;
import de.groothues.mysaml.assertion.SignedAssertionBuilder;
import de.groothues.mysaml.impl.SamlContextImpl;
import de.groothues.mysaml.validator.Validator;
import de.groothues.mysaml.validator.ValidationResult;

public class SignaturValidatorTest {

	private Validator<Document> signaturValidator;
	private SignedAssertionBuilder signedAssertionBuilder;
	
	@Before
	public void setUp() throws Exception {
		SamlContext samlContext = new SamlContextImpl();
		signaturValidator = samlContext.getSignatureValidator();
		signedAssertionBuilder = samlContext.getSignedAssertionBuilder();
	}

	@Test
	public void testValidate_ValidSignature() {
		Document signedAssertionDoc = signedAssertionBuilder.build(null);
		ValidationResult result = signaturValidator.validate(signedAssertionDoc);
		assertTrue(result.isValid());
	}
	
	@Test
	public void testValidate_InalidSignature() {
		Document signedAssertionDoc = signedAssertionBuilder.build(null);
		AssertionType signedAssertion = signedAssertionBuilder.unmarshal(signedAssertionDoc);
		signedAssertion.getIssuer().setValue("http://another.issuer.test");
		Document invalidSignedAssertionDoc = signedAssertionBuilder.marshal(signedAssertion);
		ValidationResult result = signaturValidator.validate(invalidSignedAssertionDoc);
		assertFalse(result.isValid());
	}	
}

