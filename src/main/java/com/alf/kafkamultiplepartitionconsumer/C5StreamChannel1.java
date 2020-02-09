package com.alf.kafkamultiplepartitionconsumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/*
 * A Sink Channel Binding Interface for Topic c5
 */
public interface C5StreamChannel1 {
    String INPUT = "c5-in-ch1";

    // read message
    @Input(INPUT)
    SubscribableChannel inbound();

    // write message
    //@Output(OUTPUT)
    //MessageChannel outboundC5();
}
