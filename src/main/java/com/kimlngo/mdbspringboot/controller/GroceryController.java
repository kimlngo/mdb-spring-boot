package com.kimlngo.mdbspringboot.controller;

import com.kimlngo.mdbspringboot.model.GroceryItem;
import com.kimlngo.mdbspringboot.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GroceryController {

    private GroceryService groceryService;

    @Autowired
    public GroceryController(GroceryService groceryService) {
        this.groceryService = groceryService;
    }

    @GetMapping("listAllGrocery")
    public ResponseEntity<List<GroceryItem>> listAllGroceryItems() {
        List<GroceryItem> groceryList = groceryService.listAllGroceryItems();
        return new ResponseEntity<>(groceryList, HttpStatus.OK);
    }

    @GetMapping("findByName")
    public ResponseEntity<GroceryItem> findGroceryItemByName(@RequestParam String name) {
        Optional<GroceryItem> groceryOpt = groceryService.findGroceryItemByName(name);
        if (groceryOpt.isPresent()) {
            return new ResponseEntity<>(groceryOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("listByCategory")
    public ResponseEntity<List<GroceryItem>> listGroceryByCategory(@RequestParam String category) {
        Optional<List<GroceryItem>> groceryByCategoryOpt = groceryService.listGroceryItemsByCategory(category);

        if (groceryByCategoryOpt.isPresent()) {
            return new ResponseEntity<>(groceryByCategoryOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("saveGrocery")
    public ResponseEntity<GroceryItem> saveNewGroceryItem(@RequestBody GroceryItem groceryItem) {
        try {
            GroceryItem savedGroceryItem = groceryService.saveGroceryItem(groceryItem);
            return new ResponseEntity<>(savedGroceryItem, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("updateQuantity/{name}")
    public ResponseEntity updateGroceryQuantity(@PathVariable String name, @RequestBody GroceryItem groceryToUpdate) {
        try {
            int newQuantity = groceryToUpdate.getQuantity();
            groceryService.updateGroceryQuantity(name, newQuantity);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("deleteGrocery/{id}")
    public ResponseEntity deleteGrocery(@PathVariable String id) {
        try {
            groceryService.deleteGroceryItem(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
