package de.groothues.mysaml.signature.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.impl.SamlContextImpl;
import de.groothues.mysaml.signature.SignatureKeyStore;

public class SignatureKeyStoreImplTest {

	private SignatureKeyStore signatureKeyStore;
	
	@Before
	public void setUp() {
		signatureKeyStore = new SignatureKeyStoreImpl(new SamlContextImpl()); 
	}
	
	@Test
	public void testGetSignatureKey() {
		assertNotNull(signatureKeyStore.getSignatureKey());
	}

	@Test
	public void testGetSignatureCertificate() {
		assertNotNull(signatureKeyStore.getSignatureCertificate());
	}
	
}

