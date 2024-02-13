package com.kimlngo.mdbspringboot.service;

import com.kimlngo.mdbspringboot.model.GroceryItem;
import com.kimlngo.mdbspringboot.repository.CustomItemRepositoryImpl;
import com.kimlngo.mdbspringboot.repository.ItemRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@EnableMongoRepositories
public class GroceryService {

    private ItemRepository groceryItemRepo;

    private CustomItemRepositoryImpl customItemRepository;

    @Autowired
    public GroceryService(ItemRepository groceryItemRepo, CustomItemRepositoryImpl customItemRepository) {
        this.groceryItemRepo = groceryItemRepo;
        this.customItemRepository = customItemRepository;
    }

    public List<GroceryItem> listAllGroceryItems() {
        return groceryItemRepo.findAll();
    }

    public Optional<GroceryItem> findGroceryItemByName(String name) {
        return Optional.ofNullable(groceryItemRepo.findItemByName(name));
    }

    public Optional<List<GroceryItem>> listGroceryItemsByCategory(String category) {
        return Optional.ofNullable(groceryItemRepo.findAll(category));
    }

    public void updateGroceryCategoryName(String oldCategory, String newCategory) {
        List<GroceryItem> items = groceryItemRepo.findAll(oldCategory);
        if(CollectionUtils.isNotEmpty(items)) {
            items.forEach(setGroceryItem(newCategory));
            groceryItemRepo.saveAll(items);
        }
    }

    public void updateGroceryQuantity(String name, int newQuantity) {
        customItemRepository.updateItemQuantity(name, newQuantity);
    }

    public void deleteGroceryItem(String id) {
        groceryItemRepo.deleteById(id);
    }

    private Consumer<GroceryItem> setGroceryItem(String newCategory) {
        return item -> item.setCategory(newCategory);
    }

    public GroceryItem saveGroceryItem(GroceryItem groceryItem) {
        return groceryItemRepo.save(groceryItem);
    }
}
