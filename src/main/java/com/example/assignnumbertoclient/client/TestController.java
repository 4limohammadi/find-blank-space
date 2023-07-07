package com.example.assignnumbertoclient.client;

import com.example.assignnumbertoclient.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @see #testInsert() using this method for create a not vip user
 * @see #testInserVip(long) using this method for create a  vip user by pass user code  as input
 * @see #testInsertABunchOfClient(long) using this method for insert a number od user with serial user code
 * */

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestController {
    private final ClientService clientService;

    @GetMapping("insert/{clientNum}")
    public Client testInserVip(@PathVariable long clientNum){
        Client client = new Client();
        client.setClientNumber(clientNum);


        return clientService.insertNewClient(client).orElseThrow(()->{throw new ClientException("duplicated record");});
    }

    @GetMapping("insert")
    public Client testInsert(){

        return  clientService.insertNewClient();

    }

    @GetMapping("insert-list/{size}")
    public List<Client> testInsertABunchOfClient(@PathVariable long size){

        return clientService.insertAnArrayOfSerialClient(size).orElseThrow(()->{throw new ClientException("server error!");});
    }
}
