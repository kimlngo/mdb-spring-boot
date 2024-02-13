package com.kimlngo.mdbspringboot.repository;

public interface CustomItemRepository {
    void updateItemQuantity(String name, int newQuantity);
}
