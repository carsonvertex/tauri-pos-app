package com.tauri.pos.sqlite.service;

import com.tauri.pos.sqlite.model.LocalOrderItem;

import java.util.List;

public interface LocalOrderItemService {
    LocalOrderItem createLocalOrderItem(LocalOrderItem localOrderItem);

    List<LocalOrderItem> getAllLocalOrderItem();

    LocalOrderItem getLocalOrderItemById(Long Id);

    LocalOrderItem updateLocalOrderItemById(Long Id, LocalOrderItem localOrderItem);

    void deleteLocalOrderItemById(Long Id);
}
