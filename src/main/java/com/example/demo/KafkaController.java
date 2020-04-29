package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping(value = "/kafka")

public class KafkaController {

private final Producer producer;

@Autowired

public KafkaController(Producer producer) {

this.producer = producer;

}

@PostMapping(value = "/receive")

public void sendMessageToKafkaTopic(@RequestParam("message") String message) throws IOException{

this.producer.sendMessage(message);
receivefromHttp();

}
@Scheduled(fixedDelay = 30000, initialDelay = 30000)
public void receivefromHttp() throws IOException {
//	String command =
//			  "curl -X GET http://localhost:30896/metrics";
//	Process process = Runtime.getRuntime().exec(command);
//	System.out.println(command);
//	System.out.println(process.getInputStream().toString());
	 URL yahoo = new URL("http://10.90.21.41:30985/metrics");
     URLConnection yc = yahoo.openConnection();
     BufferedReader in = new BufferedReader(
                             new InputStreamReader(
                             yc.getInputStream()));
     String inputLine;
     
     while ((inputLine = in.readLine()) != null) 
         //System.out.println(inputLine);
     this.producer.sendMessage(inputLine);
     in.close();
	
}

}
