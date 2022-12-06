package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.ItemDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Item;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionChecker;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionMessage;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ResourceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.UpdateConflictException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.ItemMapper;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.ItemRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.ItemMapper.mapDTOToItem;
import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.ItemMapper.mapItemToDTO;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final HeroRepository heroRepository;
    private final ExceptionChecker exceptCheck;
    private final ExceptionMessage em;

    public ItemServiceImpl(ItemRepository itemRepository,
                           HeroRepository heroRepository,
                           ExceptionChecker exceptCheck,
                           ExceptionMessage exceptionMessage) {
        this.itemRepository = itemRepository;
        this.heroRepository = heroRepository;
        this.exceptCheck = exceptCheck;
        this.em = exceptionMessage;
    }

    @Override
    public ItemDTO getItemByID(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noItemId") + id));
        return mapItemToDTO(item);
    }

    @Override
    public Set<ItemDTO> getAllItems() {
        return itemRepository.findAll().stream()
                .map(ItemMapper::mapItemToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ItemDTO> getAllItemsByHero(Long id) {
        exceptCheck.ifHeroDoesNotExist(id);
        return itemRepository.findAllByOwnerId(id).stream()
                .map(ItemMapper::mapItemToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public ItemDTO createItem(ItemDTO dto) {
        exceptCheck.ifHeroIdIsNull(dto.getOwnerId());
        exceptCheck.ifItemNameIsNull(dto.getName());
        if (!itemRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new UpdateConflictException(em.getMessage("isItemName") + dto.getName());
        }
        Hero hero = heroRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noHeroId") + dto.getOwnerId()));

        Item item = mapDTOToItem(dto);
        item.setOwner(hero);
        return mapItemToDTO(itemRepository.save(item));
    }

    @Transactional
    @Override
    public void deleteItemById(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ResourceNotFoundException(em.getMessage("noItemId") + id);
        }
        itemRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteItemByName(String name) {
        if (!itemRepository.existsByNameIgnoreCase(name)) {
            throw new ResourceNotFoundException(em.getMessage("noItemName") + name);
        }
        itemRepository.deleteByNameIgnoreCase(name);
    }

    @Transactional
    @Override
    public ItemDTO updateItem(Long id, ItemDTO dto) {
        return mapItemToDTO(itemRepository.save(buildUpdateItem(id, dto)));
    }

    private Item buildUpdateItem(Long id, ItemDTO dto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noItemId") + id));

        if (!StringUtils.isBlank(dto.getName())) {
            item.setName(dto.getName());
        }

        if (!StringUtils.isBlank(dto.getDescription())) {
            item.setDescription(dto.getDescription());
        }

        if (dto.getOwnerId() != null) {
            item.setOwner(heroRepository.findById(dto.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noHeroId") + dto.getOwnerId())));
        }

        return item;
    }


}
