package com.example.assignnumbertoclient.blankSpace;


import com.example.assignnumbertoclient.client.Client;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BlankSpaceContainerService {
    Integer getAllBlankContainerCunt();

    Optional<BlankSpaceContaianer> getContainerThatContainNumber(long number);

    BlankSpaceContaianer createNewContainer(BlankSpaceContaianer container);

    long getFirstAvailableBlankSpaceAndUpdateContainer();

    @Transactional
    long updateBlankSpaceForInsertVip(Client clientToInsert);

    BlankSpaceContaianer getFirstBlankSpaceThatCanContainAnArrayOfClients(long sizeOfArray);
}
