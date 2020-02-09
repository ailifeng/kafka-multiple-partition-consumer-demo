package com.alf.kafkamultiplepartitionconsumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/*
 * A Sink Channel Binding Interface for Topic c5
 */
public interface C6Stream {
    String INPUT = "c6-in";

    // read message
    @Input(INPUT)
    SubscribableChannel inboundC6();

    // write message
    //@Output(OUTPUT)
    //MessageChannel outboundC5();
}
