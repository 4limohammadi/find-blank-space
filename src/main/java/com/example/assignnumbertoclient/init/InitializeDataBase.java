package com.example.assignnumbertoclient.init;

import com.example.assignnumbertoclient.blankSpace.BlankSpaceContaianer;
import com.example.assignnumbertoclient.blankSpace.BlankSpaceContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitializeDataBase {

    @Autowired
    public InitializeDataBase(BlankSpaceContainerService blankSpaceContainerService){
        if(blankSpaceContainerService.getAllBlankContainerCunt()==0){
            BlankSpaceContaianer container=new BlankSpaceContaianer()
                    .setLeftSide(0l)
                    .setRightSide(Long.MAX_VALUE);
            blankSpaceContainerService.createNewContainer(container);
        }
    }

}
