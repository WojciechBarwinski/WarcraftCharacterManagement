package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.ItemDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.ItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController()
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation(value = "Read a item by id", notes = "Returns item as per id")
    @GetMapping(value = "/{id}")
    public ItemDTO readItemById(@PathVariable Long id){
        return itemService.getItemByID(id);
    }

    @ApiOperation(value = "Read all items", notes = "Returns all items")
    @GetMapping
    public Set<ItemDTO> readAllItem(){
        return itemService.getAllItems();
    }

    @ApiOperation(value = "Read all items of hero by hero id", notes = "Returns all hero's items")
    @GetMapping(value = "/")
    public Set<ItemDTO> readItemsByHero(@RequestParam(value="heroId") Long heroId){
        return itemService.getAllItemsByHero(heroId);
    }


}
