package de.groothues.mysaml.samples.ws;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.SamlContextFactory;
import de.groothues.mysaml.assertion.AssertionType;
import de.groothues.mysaml.impl.DomHelper;
import de.groothues.mysaml.validator.ValidationResult;

public class SamlAuthenticationHandler implements SOAPHandler<SOAPMessageContext> {
    
    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        if (isInbound(context)) {
            SOAPMessage message = context.getMessage();
            try {          
                validateAssertion(message);
            } catch (SOAPException e) {
                // TODO Log exception
                e.printStackTrace();
            }
            
            System.out.println("Message handled successfully.");
        }
        return true;
    }
    
    private void validateAssertion(SOAPMessage soapMessage) throws SOAPException {
        Element assertionElement = getAssertion(soapMessage.getSOAPHeader());
        if (assertionElement == null) {
            createAndThrowSOAPFault(soapMessage, "No SAML Assertion found in SOAP Header");
        }
        
        SamlContext samlContext = SamlContextFactory.createSamlContext();
        Document assertionDoc = toDocument(assertionElement);
        AssertionType assertion = samlContext.getAssertionBuilder().unmarshal(assertionDoc);
     
        ValidationResult ageValidation = samlContext.getAssertionAgeValidator().validate(assertion);
        if (!ageValidation.isValid()) {
            createAndThrowSOAPFault(soapMessage, ageValidation.getResultMessage());
        }

        ValidationResult periodValidation = samlContext.getAssertionValidityPeriodValidator().validate(assertion);
        if (!periodValidation.isValid()) {
            createAndThrowSOAPFault(soapMessage, periodValidation.getResultMessage());
        }
        
        ValidationResult audienceValidation = samlContext.getAssertionAudienceValidator().validate(assertion);
        if (!audienceValidation.isValid()) {
            createAndThrowSOAPFault(soapMessage, audienceValidation.getResultMessage());
        }
        
        ValidationResult signatureValidation = samlContext.getSignatureValidator().validate(assertionDoc);
        if (!signatureValidation.isValid()) {
            createAndThrowSOAPFault(soapMessage, signatureValidation.getResultMessage());
        }        
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    private boolean isInbound(SOAPMessageContext context) {
        Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        return !isOutbound;
    }

    private void createAndThrowSOAPFault(SOAPMessage message, String faultString) 
            throws SOAPException {
        SOAPHeader header = message.getSOAPHeader();
        header.removeContents();
        SOAPBody body = message.getSOAPPart().getEnvelope().getBody();
        body.removeContents();
        SOAPFault fault = body.addFault();
        fault.setFaultString(faultString);
        
        throw new SOAPFaultException(fault);
    }
    
    private Element getAssertion(SOAPHeader header) {
        Iterator<?> it = header.examineAllHeaderElements();

        while (it.hasNext()) {
            Node node = (Node) it.next();
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element element = (Element) node;
            if (!element.getLocalName().equals("Security")) {
                continue;
            }
            NodeList nodes = element.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion");
            return (Element)nodes.item(0);
        }
        return null;
        
    }

    private Document toDocument(Element assertion) {
        Document doc = DomHelper.createNewDocument();
        org.w3c.dom.Node imported = (org.w3c.dom.Node) doc.importNode(assertion, true);
        doc.appendChild(imported);
        return doc;
    }

}
