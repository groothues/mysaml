package de.groothues.mysaml.samples.ws;

import java.util.LinkedList;
import java.util.List;

import javax.xml.ws.Binding;
import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;

public class TimerService {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Endpoint endpoint = Endpoint.create(new TimerImpl());
        Binding binding = endpoint.getBinding();
        @SuppressWarnings("rawtypes")
        List<Handler> handlerChain = new LinkedList<Handler>();
        handlerChain.add(new SamlAuthenticationHandler());
        binding.setHandlerChain(handlerChain);
        
        endpoint.publish("http://localhost:1234/timer");
    }

}
