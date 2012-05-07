package de.groothues.mysaml.signature.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import de.groothues.mysaml.impl.SamlContextImpl;
import de.groothues.mysaml.signature.SignatureTrustStore;

public class SignatureTrustStoreImplTest {

	private SignatureTrustStore signatureTrustStore;
	
	@Before
	public void setUp() {
		signatureTrustStore = new SignatureTrustStoreImpl(new SamlContextImpl()); 
	}
	
	@Test
	public void testGetTrustedCertificates() {
		assertNotNull(signatureTrustStore.getTrustedCertificates());
		assertEquals(1, signatureTrustStore.getTrustedCertificates().size());
	}
	
}

