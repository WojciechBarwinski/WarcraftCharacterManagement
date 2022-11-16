package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.ItemDTO;

import java.util.Set;

public interface ItemService {
    ItemDTO getItemByID(Long id);

    Set<ItemDTO> getAllItems();

    Set<ItemDTO> getAllItemsByHero(Long heroId);

    ItemDTO createItem(ItemDTO itemDTO);

    void deleteItemById(Long id);

    void deleteItemByName(String name);

    ItemDTO updateItem(Long id, ItemDTO itemDTO);
}
