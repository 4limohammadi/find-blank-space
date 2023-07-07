package com.example.assignnumbertoclient.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientDao extends JpaRepository<Client, Long> {

    @Modifying
    @Query("select max(client.clientNumber) from Client client")
    Optional<Client> getMaxClientNumber();

    Optional<Client> getClientByClientNumber(long clientNumber);

}
