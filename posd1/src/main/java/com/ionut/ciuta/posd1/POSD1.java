package com.ionut.ciuta.posd1;

import com.ionut.ciuta.posd1.service.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class POSD1 {
	@Autowired
	private Storage storage;

	public static void main(String[] args) {
		SpringApplication.run(POSD1.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onStart() {
		System.out.println("Storage users: " + storage.getUsers());
		System.out.println("Storage resources: " + storage.getResources());
	}
}
