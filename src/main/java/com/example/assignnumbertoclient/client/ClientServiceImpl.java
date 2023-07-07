package com.example.assignnumbertoclient.client;

import com.example.assignnumbertoclient.blankSpace.BlankSpaceContaianer;
import com.example.assignnumbertoclient.blankSpace.BlankSpaceContainerService;
import com.example.assignnumbertoclient.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ClientServiceImpl implements ClientService {

    private final BlankSpaceContainerService blankSpaceContainerService;
    private final ClientDao clientDao;

    //new normal client
    @Override
    @Transactional
    public Client insertNewClient() {

        long firstAvailableBlankSpaceAndUpdateContainer = blankSpaceContainerService.getFirstAvailableBlankSpaceAndUpdateContainer();
        Client client = new Client();
        client.setClientNumber(firstAvailableBlankSpaceAndUpdateContainer);
        Client result = clientDao.saveAndFlush(client);

        return result;
    }


    //new Vip client
    @Override
    @Transactional
    public Optional<Client> insertNewClient(Client client) {

        if (clientDao.existsById(client.getClientNumber())) {
            throw new ClientException("duplicated record");
        }

        blankSpaceContainerService.updateBlankSpaceForInsertVip(client);
        
        return Optional.of(clientDao.saveAndFlush(client));
    }

   @Transactional
   @Override
    public Optional<List<Client>> insertAnArrayOfSerialClient(long sizeOfArray){
       BlankSpaceContaianer firstBlankSpaceThatCanContainAnArrayOfClients = blankSpaceContainerService.getFirstBlankSpaceThatCanContainAnArrayOfClients(sizeOfArray);
       LongStream longStream = LongStream.rangeClosed(firstBlankSpaceThatCanContainAnArrayOfClients.getLeftSide(), firstBlankSpaceThatCanContainAnArrayOfClients.getLeftSide()+sizeOfArray);
       List<Client> clientList= longStream.mapToObj(item -> {
           Client client = new Client();
           client.setClientNumber(item);
           return client;
       }).toList();

       return Optional.of(clientDao.saveAllAndFlush(clientList));
   }
}
