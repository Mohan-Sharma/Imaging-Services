package com.nzion.external;

import lombok.NoArgsConstructor;
import org.axonframework.domain.EventMessage;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

/**
 * Created by Nthdimenzion on 4/8/2015.
 */
@Component
@NoArgsConstructor
public class LabOrderEventHandler {

    @EventHandler
    public void labOrderEventHandler(EventMessage<LabOrderDto> labOrdetDtoGenericEventMessage){
        System.out.println(labOrdetDtoGenericEventMessage.getPayload());
    }
}
