package de.groothues.mysaml.signature.impl;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.AssertionBuilder;
import de.groothues.mysaml.assertion.AssertionType;
import de.groothues.mysaml.impl.SamlContextImpl;
import de.groothues.mysaml.signature.DocumentSigner;

public class DocumentSignerImplTest {
		
	private DocumentSigner documentSigner;
	private AssertionBuilder assertionBuilder;

	@Before
	public void setUp() throws Exception {
		SamlContext samlContext = new SamlContextImpl();
		documentSigner = samlContext.getDocumentSigner();
		assertionBuilder = samlContext.getAssertionBuilder();
	}
	
	@Test
	public void testSignDocument() {
		AssertionType unsignedAssertion = assertionBuilder.build(null);
		
		assertNull(unsignedAssertion.getSignature());
		
		Document assertionDoc = assertionBuilder.marshal(unsignedAssertion);
		documentSigner.sign(assertionDoc, null);
		AssertionType signedAssertion = assertionBuilder.unmarshal(assertionDoc);
		
		assertNotNull(signedAssertion.getSignature());
	}	
}
