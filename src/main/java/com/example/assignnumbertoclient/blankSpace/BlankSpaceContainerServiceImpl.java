package com.example.assignnumbertoclient.blankSpace;

import com.example.assignnumbertoclient.client.Client;
import com.example.assignnumbertoclient.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BlankSpaceContainerServiceImpl implements BlankSpaceContainerService {

    private final BlankSpaceContainerDao blankSpaceContainerDao;

    @Override
    public Integer getAllBlankContainerCunt() {

        return blankSpaceContainerDao.getCountOfAllBlankSpace();
    }

    @Override
    public Optional<BlankSpaceContaianer> getContainerThatContainNumber(long number) {

        return blankSpaceContainerDao.getContainerThatIncludeNumber(number);
    }

    @Override
    public BlankSpaceContaianer createNewContainer(BlankSpaceContaianer container) {

        return blankSpaceContainerDao.saveAndFlush(container);
    }

    @Override
    @Transactional
    public long getFirstAvailableBlankSpaceAndUpdateContainer() {
        BlankSpaceContaianer firstAvailableBlankSpace = blankSpaceContainerDao.findTopByOrderByLeftSideAsc().orElseThrow(() -> new ClientException("blank space not available"));
        long result = firstAvailableBlankSpace.getLeftSide();
        if (firstAvailableBlankSpace.getLeftSide() == firstAvailableBlankSpace.getRightSide()) {
            blankSpaceContainerDao.removeById(firstAvailableBlankSpace.getId());
        } else {
            blankSpaceContainerDao.updateById(firstAvailableBlankSpace.getRightSide(),firstAvailableBlankSpace.getLeftSide() + 1, firstAvailableBlankSpace.getId());
        }

        return result;
    }

    @Transactional
    @Override
    public long updateBlankSpaceForInsertVip(Client clientToInsert) {
        Optional<BlankSpaceContaianer> firstAvailableBlankSpace = blankSpaceContainerDao.getContainerThatIncludeNumber(clientToInsert.getClientNumber());

        if (firstAvailableBlankSpace.isEmpty()) {
            throw new ClientException("blank space not available");
        }
        BlankSpaceContaianer blankSpaceContaianer = firstAvailableBlankSpace.get();

        if (blankSpaceContaianer.getLeftSide() == blankSpaceContaianer.getRightSide()) {
            blankSpaceContainerDao.delete(blankSpaceContaianer);

            return clientToInsert.getClientNumber();
        } else {
            if (blankSpaceContaianer.getLeftSide() == clientToInsert.getClientNumber())
                blankSpaceContaianer.setLeftSide(blankSpaceContaianer.getLeftSide()+1);

            else if (blankSpaceContaianer.getRightSide() == clientToInsert.getClientNumber())
                blankSpaceContaianer.setRightSide(blankSpaceContaianer.getRightSide()-1);

            else {

                BlankSpaceContaianer leftContiner = new BlankSpaceContaianer()
                        .setLeftSide(blankSpaceContaianer.getLeftSide())
                        .setRightSide(clientToInsert.getClientNumber()-1);

                blankSpaceContainerDao.saveAndFlush(leftContiner);

                blankSpaceContaianer.setLeftSide(clientToInsert.getClientNumber() + 1);
            }

            blankSpaceContainerDao.updateById(blankSpaceContaianer.getRightSide(), blankSpaceContaianer.getLeftSide(), blankSpaceContaianer.getId());
        }


        return clientToInsert.getClientNumber();
    }

    @Override
    public BlankSpaceContaianer getFirstBlankSpaceThatCanContainAnArrayOfClients(long sizeOfArray){
        Optional<BlankSpaceContaianer> firstBlankSpaceThatCanContainAnArrayOfClients = blankSpaceContainerDao.getFirstBlankSpaceThatCanContainAnArrayOfClients(sizeOfArray);
        BlankSpaceContaianer contaianer = firstBlankSpaceThatCanContainAnArrayOfClients.orElseThrow(() -> new ClientException("cant find a blank space match eith this size"));

        BlankSpaceContaianer result=new BlankSpaceContaianer()
                .setLeftSide(contaianer.getLeftSide())
                .setRightSide(contaianer.getRightSide());

        contaianer.setLeftSide(contaianer.getLeftSide()+sizeOfArray);
        if(contaianer.getLeftSide()==contaianer.getRightSide()){
            blankSpaceContainerDao.removeById(contaianer.getId());

            return result;
        }

        blankSpaceContainerDao.updateById(contaianer.getRightSide(), contaianer.getLeftSide(), contaianer.getId());


        return result;
    }
}
