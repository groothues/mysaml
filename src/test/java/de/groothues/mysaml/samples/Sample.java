package de.groothues.mysaml.samples;

import org.w3c.dom.Document;

import de.groothues.mysaml.SamlContext;
import de.groothues.mysaml.impl.DomHelper;
import de.groothues.mysaml.impl.SamlContextImpl;

public class Sample {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SamlContext context = new SamlContextImpl();
        Document assertion = context.getSignedAssertionBuilder().build(null);
        
        System.out.println(DomHelper.writeToString(assertion));

    }

}
