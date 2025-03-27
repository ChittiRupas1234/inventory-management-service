package com.inventorymanagement.service;


import com.inventorymanagement.entity.Inventory;
import com.inventorymanagement.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // Check stock level with caching
    @Cacheable(value = "inventory", key = "#productId")
    public int checkStock(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .map(Inventory::getQuantity)
                .orElse(0);
    }

    // Update stock level
    public void updateStock(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElse(new Inventory(null, productId, 0));
        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
    }

    // Handle low stock alerts
    public boolean isLowStock(Long productId) {
        return checkStock(productId) < 5; // Threshold: 5 items
    }
}

