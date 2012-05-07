package de.groothues.mysaml.impl;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.signature.SignatureKeyStore;
import de.groothues.mysaml.signature.SignatureTrustStore;

public class SamlContextImplKeyStoreTest {

	private SamlContext samlContext;
	
	@Before
	public void setUp() throws Exception {
		samlContext = new SamlContextImpl();
	}
	
	@Test
	public void testGetSignatureKeyStoreSingleton() {
		SignatureKeyStore signatureKeyStore1 = samlContext.getSignatureKeyStore();
		assertNotNull(signatureKeyStore1);
		SignatureKeyStore signatureKeyStore2 = samlContext.getSignatureKeyStore();
		assertSame(signatureKeyStore1, signatureKeyStore2);
	}

	@Test
	public void testGetSignatureTrustStoreSingleton() {
		SignatureTrustStore signatureTrustStore1 = samlContext.getSignatureTrustStore();
		assertNotNull(signatureTrustStore1);
		SignatureTrustStore signatureTrustStore2 = samlContext.getSignatureTrustStore();
		assertSame(signatureTrustStore1, signatureTrustStore2);
	}
	
}
