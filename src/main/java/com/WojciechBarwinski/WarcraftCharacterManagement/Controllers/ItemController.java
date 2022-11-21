package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.ItemDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.ItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController()
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation(value = "Create a new item", notes = "Return new item. This method require Hero")
    @PostMapping()
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDTO){
        ItemDTO item = itemService.createItem(itemDTO);
        return getCorrectURILocation(item);
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

    @ApiOperation(value = "Update a item, need only new information", notes = "Returns url and json with to updated item")
    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO){
        ItemDTO item = itemService.updateItem(id, itemDTO);
        return getCorrectURILocation(item);
    }

    @ApiOperation(value = "Delete item by item id", notes = "Return nothing")
    @DeleteMapping(value = "/{id}")
    public void deleteItemById(@PathVariable Long id){
        itemService.deleteItemById(id);
    }

    @ApiOperation(value = "Delete item by item name", notes = "Return nothing")
    @DeleteMapping(value = "/")
    public void deleteItemByName(@RequestParam(value = "itemName") String itemName){
        itemService.deleteItemByName(itemName);
    }

    private ResponseEntity<ItemDTO> getCorrectURILocation(ItemDTO itemDTO){
        URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/items/{id}")
                .buildAndExpand(itemDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(itemDTO);
    }
}
