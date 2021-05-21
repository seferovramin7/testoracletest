package com.azericard.testoracle.controller;

import com.azericard.testoracle.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
public class ConnectionSample {

    @Autowired
    ConnectionService connectionService;

    @RequestMapping( value = "/testoracle", method = RequestMethod.GET)
    public ResponseEntity<String> getConnection(@RequestParam Integer num) throws SQLException, ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(num);//creating a pool of 5 threads
        for (int i = 0; i < num; i++) {
            Runnable worker = new ConnectionService();
            executor.execute(worker);//calling execute method of ExecutorService
        }
        executor.shutdown();
        while (!executor.isTerminated()) {   }
        System.out.println("Finished all threads");

//        String response = connectionService.measureTime();
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
