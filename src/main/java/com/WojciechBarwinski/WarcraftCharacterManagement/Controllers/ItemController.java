package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.DTOFlag;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.ItemDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.ItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.WojciechBarwinski.WarcraftCharacterManagement.ControllerType.ITEM;
import static com.WojciechBarwinski.WarcraftCharacterManagement.Controllers.RespEntityPathCreator.getCorrectResponseEntity;

@RestController()
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation(value = "Create a new item", notes = "Return new item. This method require Hero")
    @PostMapping()
    public ResponseEntity<DTOFlag> createItem(@RequestBody ItemDTO itemDTO) {
        return getCorrectResponseEntity(ITEM, itemService.createItem(itemDTO));
    }

    @ApiOperation(value = "Read a item by id", notes = "Returns item as per id")
    @GetMapping(value = "/{id}")
    public ItemDTO readItemById(@PathVariable Long id) {
        return itemService.getItemByID(id);
    }

    @ApiOperation(value = "Read all items", notes = "Returns all items")
    @GetMapping
    public Set<ItemDTO> readAllItem() {
        return itemService.getAllItems();
    }

    @ApiOperation(value = "Read all items of hero by hero id", notes = "Returns all hero's items")
    @GetMapping(value = "/")
    public Set<ItemDTO> readItemsByHero(@RequestParam(value = "heroId") Long heroId) {
        return itemService.getAllItemsByHero(heroId);
    }

    @ApiOperation(value = "Update a item, need only new information", notes = "Returns url and json with to updated item")
    @PutMapping("/{id}")
    public ResponseEntity<DTOFlag> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        return getCorrectResponseEntity(ITEM, itemService.updateItem(id, itemDTO));
    }

    @ApiOperation(value = "Delete item by item id", notes = "Return nothing")
    @DeleteMapping(value = "/{id}")
    public void deleteItemById(@PathVariable Long id) {
        itemService.deleteItemById(id);
    }

    @ApiOperation(value = "Delete item by item name", notes = "Return nothing")
    @DeleteMapping(value = "/")
    public void deleteItemByName(@RequestParam(value = "itemName") String itemName) {
        itemService.deleteItemByName(itemName);
    }

}
