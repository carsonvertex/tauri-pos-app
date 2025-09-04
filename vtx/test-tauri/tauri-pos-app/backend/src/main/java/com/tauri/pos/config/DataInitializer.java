package com.tauri.pos.config;

import com.tauri.pos.service.PosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private PosService posService;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        posService.initializeSampleData();
        System.out.println("✅ Sample data initialized successfully!");
    }
}
