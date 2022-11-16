package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.ItemDTO;

import java.util.Set;

public interface ItemService {
    ItemDTO getItemByID(Long id);

    Set<ItemDTO> getAllItems();

    Set<ItemDTO> getAllItemsByHero(Long heroId);
}
