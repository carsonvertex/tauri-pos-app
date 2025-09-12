package com.tauri.pos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EntityScan(basePackages = {"com.tauri.pos.sqlite.persistance.eo"})
public class TauriPosApplication {

    public static void main(String[] args) {
        SpringApplication.run(TauriPosApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("🚀 Tauri POS Backend is running on port 8080");
        System.out.println("🔑 Default credentials: admin/password");
    }
}
