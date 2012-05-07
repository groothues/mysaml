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

import java.security.Key;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Set;

import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.X509Data;

import de.groothues.mysaml.SamlContext;

public class X509KeySelector extends KeySelector {

	private SamlContext samlContext;

	public X509KeySelector(SamlContext samlContext) {
		this.samlContext = samlContext;
	}

	@Override
	public KeySelectorResult select(KeyInfo keyInfo, KeySelector.Purpose purpose, 
			AlgorithmMethod method,	XMLCryptoContext context) {
		
		Iterator<?> ki = keyInfo.getContent().iterator();
		while (ki.hasNext()) {
			XMLStructure info = (XMLStructure) ki.next();
			if (!(info instanceof X509Data))
				continue;
			
			X509Data x509Data = (X509Data) info;
			Iterator<?> xi = x509Data.getContent().iterator();
			while (xi.hasNext()) {
				Object o = xi.next();
				if (!(o instanceof X509Certificate))
				{
					continue;
				}
				
				X509Certificate certificate = (X509Certificate) o;
				
				if (!isTrustedCertificate(certificate)) {
					continue;
				}
				
				final PublicKey key = certificate.getPublicKey();
				
				// Make sure the algorithm is compatible with the method.
				if (algEquals(method.getAlgorithm(), key.getAlgorithm())) {
					return new KeySelectorResult() {
						public Key getKey() {
							return key;
						}
					};
				}
			}
		}
		throw new RuntimeException(new KeySelectorException("No key found!"));
	}

	private boolean isTrustedCertificate(X509Certificate certificate) {
		Set<X509Certificate> trustedCertificates = samlContext.getSignatureTrustStore().
				getTrustedCertificates();
		for (X509Certificate trustedCertificate: trustedCertificates) {
			if (trustedCertificate.equals(certificate)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean algEquals(String algURI, String algName) {
		if ((algName.equalsIgnoreCase("DSA") && algURI
				.equalsIgnoreCase(SignatureMethod.DSA_SHA1))
				|| (algName.equalsIgnoreCase("RSA") && algURI
						.equalsIgnoreCase(SignatureMethod.RSA_SHA1))) {
			return true;
		} else {
			return false;
		}
	}

}
