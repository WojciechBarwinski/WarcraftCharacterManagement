package com.WojciechBarwinski.WarcraftCharacterManagement.Mappers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.ItemDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Item;

public class ItemMapper {

    private ItemMapper() {
    }

    public static ItemDTO mapItemToDTO(Item item){
        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .ownerId(item.getOwner().getId())
                .build();
    }

    public static Item mapDTOToItem(ItemDTO itemDTO){
        return Item.builder()
                .name(itemDTO.getName())
                .description(itemDTO.getDescription())
                .build();
    }
}
