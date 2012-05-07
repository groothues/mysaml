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
package de.groothues.mysaml.signature.impl;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.signature.SignatureKeyStore;

public class SignatureKeyStoreImpl implements SignatureKeyStore, PropertyKeys {

	private SamlContext samlContext;
	private KeyStore keyStore;

	public SignatureKeyStoreImpl(SamlContext samlContext) {
		this.samlContext = samlContext;
		this.keyStore = loadKeyStore();
	}

	@Override
	public PrivateKey getSignatureKey() {
		return getPrivateKey();
	}
	
	private KeyStore loadKeyStore() {
		try {
			KeyStore ks = KeyStore.getInstance("JKS");
	
			String keystoreFile = getProperty(KEYSTORE_LOCATION);
			InputStream is = SignatureKeyStoreImpl.class.getResourceAsStream(keystoreFile);
	
			String keystorePass = getProperty(KEYSTORE_PASSWORD);
			
			ks.load(is, keystorePass.toCharArray());
			return ks;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private PrivateKey getPrivateKey() {
		try {
			String privateKeyPass = getProperty(KEYSTORE_KEY_PASSWORD);
			String privateKeyAlias = getProperty(KEYSTORE_KEY_ALIAS);
			PrivateKey privateKey = (PrivateKey) this.keyStore.getKey(privateKeyAlias, 
					privateKeyPass.toCharArray());
			return privateKey;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public X509Certificate getSignatureCertificate() {
		try {
			String certificateAlias = getProperty(KEYSTORE_CERTIFICATE_ALIAS);
			return (X509Certificate) this.keyStore.getCertificate(certificateAlias);
		} catch (KeyStoreException e) {
			throw new RuntimeException(e);
		}
	}	
	
	private String getProperty(String key) {
		return this.samlContext.getProperty(key);
	}

}
