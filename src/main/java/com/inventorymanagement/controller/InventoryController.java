package com.inventorymanagement.controller;

import com.inventorymanagement.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{productId}")
    public int checkStock(@PathVariable Long productId) {
        return inventoryService.checkStock(productId);
    }

    @PostMapping("/update")
    public String updateStock(@RequestParam Long productId, @RequestParam int quantity) {
        inventoryService.updateStock(productId, quantity);
        return "Stock updated successfully";
    }
}