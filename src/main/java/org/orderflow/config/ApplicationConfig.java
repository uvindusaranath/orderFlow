package org.orderflow.config;

import org.orderflow.application.port.InventoryClient;
import org.orderflow.infrastructure.inventory.FakeInventoryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    InventoryClient inventoryClient() {
        return new FakeInventoryClient(true);
    }
}