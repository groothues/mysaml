package de.groothues.mysaml.samples;

import org.w3c.dom.Document;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.impl.DomHelper;
import de.groothues.mysaml.impl.SamlContextImpl;

public class ResponseSample {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SamlContext context = new SamlContextImpl();
        Document samlResponse = context.getResponseBuilder().build(null);
        
        System.out.println(DomHelper.writeToString(samlResponse));

    }

}
