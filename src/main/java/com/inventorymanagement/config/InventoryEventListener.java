package com.inventorymanagement.config;

import com.inventorymanagement.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventListener {

    @Autowired
    private InventoryService inventoryService;

    @KafkaListener(topics = "order_placed", groupId = "inventory-group")
    public void handleOrderEvent(String message) {
        System.out.println("Received Order Event: " + message);

        // Example message: "productId:101,quantity:2"
        String[] parts = message.split(",");
        Long productId = Long.parseLong(parts[0].split(":")[1]);
        int quantity = Integer.parseInt(parts[1].split(":")[1]);

        // Deduct stock
        inventoryService.updateStock(productId, quantity);

        // Check for low stock alerts
        if (inventoryService.isLowStock(productId)) {
            System.out.println("⚠️ Low Stock Alert for Product ID: " + productId);
        }
    }
}