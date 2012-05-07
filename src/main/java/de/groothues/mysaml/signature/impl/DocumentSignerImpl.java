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

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

import org.w3c.dom.Document;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.impl.SamlContextAware;
import de.groothues.mysaml.signature.DocumentSigner;

public class DocumentSignerImpl extends SamlContextAware implements DocumentSigner {

	public DocumentSignerImpl(SamlContext samlContext) {
		super(samlContext);
	}

	/* (non-Javadoc)
	 * @see de.groothues.mysaml.signature.impl.DocumentSigner#sign(org.w3c.dom.Document, java.util.Map)
	 */
	@Override
	public void sign(Document document, Map<String, String> runtimeProperties) {
		XMLSignatureFactory xmlSignatureFactory = XMLSignatureFactory.getInstance("DOM");
		
		try {
			SignedInfo signedInfo = createSignedInfo(xmlSignatureFactory);
			
			KeyInfo keyInfo = createKeyInfo(xmlSignatureFactory);
			
			PrivateKey privateKey = getSamlContext().getSignatureKeyStore().getSignatureKey();
			DOMSignContext signContext = new DOMSignContext(privateKey, document.getDocumentElement());
			XMLSignature signature = xmlSignatureFactory.newXMLSignature(signedInfo, keyInfo);
			signature.sign(signContext);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private KeyInfo createKeyInfo(XMLSignatureFactory xmlSignatureFactory)
			throws KeyStoreException {
		X509Certificate cert = (X509Certificate) getSamlContext().getSignatureKeyStore().
				getSignatureCertificate();
		KeyInfoFactory keyInfoFactory = xmlSignatureFactory.getKeyInfoFactory();
		List<Object> x509Content = new ArrayList<Object>();
		x509Content.add(cert.getSubjectX500Principal().getName());
		x509Content.add(cert);
		X509Data data = keyInfoFactory.newX509Data(x509Content);
		KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(data));
		return keyInfo;
	}

	private SignedInfo createSignedInfo(XMLSignatureFactory xmlSignatureFactory)
			throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		Reference reference = xmlSignatureFactory.newReference("", 
				xmlSignatureFactory.newDigestMethod(DigestMethod.SHA1, null), 
				Collections.singletonList(xmlSignatureFactory.newTransform(Transform.ENVELOPED, 
						(TransformParameterSpec) null)), 
				null, 
				null);

		SignedInfo signedInfo = xmlSignatureFactory.newSignedInfo(
				xmlSignatureFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, 
						(C14NMethodParameterSpec) null), 
				xmlSignatureFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null), 
				Collections.singletonList(reference));
		return signedInfo;
	}
	    
}
