package com.example.assignnumbertoclient.client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client insertNewClient();

    Optional<Client> insertNewClient(Client client);

    Optional<List<Client>> insertAnArrayOfSerialClient(long size);
}
