package de.groothues.mysaml.samples.ws;

import java.util.Date;

import javax.jws.WebService;

@WebService(endpointInterface="de.groothues.mysaml.samples.ws.Timer")
public class TimerImpl implements Timer {

    @Override
    public String currentTime() {
        return new Date().toString();
    }

}
