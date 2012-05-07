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
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.assertion.PropertyKeys;
import de.groothues.mysaml.signature.SignatureTrustStore;

public class SignatureTrustStoreImpl implements SignatureTrustStore, PropertyKeys {

	private SamlContext samlContext;
	private KeyStore trustStore;

	public SignatureTrustStoreImpl(SamlContext samlContext) {
		this.samlContext = samlContext;
		this.trustStore = loadTrustStore();
	}

	@Override
	public Set<X509Certificate> getTrustedCertificates() {
		try {
			Set<X509Certificate> certificates = new HashSet<X509Certificate>();
			Enumeration<String> aliases = trustStore.aliases();
			while (aliases.hasMoreElements()) {
				String alias = aliases.nextElement();
				// TODO: Check this
				// if (trustStore.isCertificateEntry(alias)) {
					certificates.add((X509Certificate) trustStore.getCertificate(alias));
				// }
			}
			return certificates;
		} catch (KeyStoreException e) {
			throw new RuntimeException(e);
		}
	}
	
	private KeyStore loadTrustStore() {
		try {
			KeyStore ks = KeyStore.getInstance("JKS");
	
			String keystoreFile = getProperty(TRUSTSTORE_LOCATION);
			InputStream is = SignatureTrustStoreImpl.class.getResourceAsStream(keystoreFile);
	
			String keystorePass = getProperty(TRUSTSTORE_PASSWORD);
			ks.load(is, keystorePass.toCharArray());
			return ks;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getProperty(String key) {
		return this.samlContext.getProperty(key);
	}
	

}
