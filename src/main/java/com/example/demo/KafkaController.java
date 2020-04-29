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


public class KafkaController {

private final Producer producer;

@Autowired

public KafkaController(Producer producer) {

this.producer = producer;

}




@Scheduled(fixedDelay = 30000, initialDelay = 30000)
public void receivefromHttp() throws IOException {

	 URL link = new URL("http://10.90.21.41:30985/metrics");
     URLConnection content = link.openConnection();
     BufferedReader in = new BufferedReader(
                             new InputStreamReader(
                             content.getInputStream()));
     String inputLine;
     
     while ((inputLine = in.readLine()) != null) 
     this.producer.sendMessage(inputLine);
     in.close();
	
}

}
