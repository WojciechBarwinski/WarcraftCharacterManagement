package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.ItemDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Item;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionChecker;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.HeroNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ItemNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ResourceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.ItemMapper;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.ItemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.ItemMapper.mapDTOToItem;
import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.ItemMapper.mapItemToDTO;

@Service
public class ItemServiceImpl implements ItemService {

    ItemRepository itemRepository;
    HeroRepository heroRepository;
    ExceptionChecker exceptCheck;

    public ItemServiceImpl(ItemRepository itemRepository,
                           HeroRepository heroRepository,
                           ExceptionChecker exceptCheck) {
        this.itemRepository = itemRepository;
        this.heroRepository = heroRepository;
        this.exceptCheck = exceptCheck;
    }

    @Override
    public ItemDTO getItemByID(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id.toString()));
        return mapItemToDTO(item);
    }

    @Override
    public Set<ItemDTO> getAllItems() {
        List<Item> all = itemRepository.findAll();
        return all.stream()
                .map(ItemMapper::mapItemToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ItemDTO> getAllItemsByHero(Long heroId) {
        exceptCheck.ifHeroDoesNotExist(heroId);
        Set<Item> itemsByHero = itemRepository.findAllByOwnerId(heroId);
        return itemsByHero.stream()
                .map(ItemMapper::mapItemToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public ItemDTO createItem(ItemDTO itemDTO) {
        exceptCheck.ifHeroIdIsNull(itemDTO.getOwnerId());
        exceptCheck.ifItemNameIsNull(itemDTO.getName());
        checkItemNameExists(itemDTO.getName());
        Hero hero = heroRepository.findById(itemDTO.getOwnerId())
                .orElseThrow(() -> new HeroNotFoundException(itemDTO.getOwnerId().toString()));

        Item item = mapDTOToItem(itemDTO);
        item.setOwner(hero);
        return mapItemToDTO(itemRepository.save(item));
    }

    @Transactional
    @Override
    public void deleteItemById(Long id){
        itemRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteItemByName(String name){
        itemRepository.deleteByName(name);
    }


    //TODO
    @Override
    public ItemDTO updateItem(Long id, ItemDTO itemDTO) {

        return null;
    }

    private void checkItemNameExists(String itemName){
        if (itemRepository.existsByName(itemName)){
            throw new ResourceNotFoundException("Item o nazwie " + itemName + " juz istnieje");
        }
    }
}