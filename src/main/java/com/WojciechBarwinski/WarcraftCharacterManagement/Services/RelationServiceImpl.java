package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RelationDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Relation;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.RelationKey;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.HeroNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ResourceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.RelationRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RelationServiceImpl implements RelationService {

    HeroRepository heroRepository;
    RelationRepository relationRepository;
    private RelationDTO newRelation;

    public RelationServiceImpl(RelationRepository relationRepository,
                               HeroRepository heroRepository) {
        this.relationRepository = relationRepository;
        this.heroRepository = heroRepository;
    }


    @Override
    public void addOneDirectionRelation(Hero hero1, Hero hero2, String hero2ToHero1) {
        relationRepository.save(new Relation(new RelationKey(hero1, hero2), hero2ToHero1));
    }

    @Override
    public Set<RelationDTO> addNewRelation(Long id, RelationDTO newRelation) {
        checkIfHeroDoesntExistAndThrowExcep(id);

        if (newRelation.getHeroFirstName() == null && newRelation.getHeroId() == null){
            throw new ResourceNotFoundException("Koniecznie należy podać imię lub id bohatera");
        }

        if (newRelation.getHeroId() != null){
            checkIfHeroDoesntExistAndThrowExcep(id);
            RelationKey byId = new RelationKey(heroRepository.findById(id).get(), heroRepository.findById(newRelation.getHeroId()).get());
            relationRepository.save(new Relation(byId, newRelation.getDescription()));
        } else {
            if (!heroRepository.existsByFirstName(newRelation.getHeroFirstName())){
                throw new HeroNotFoundException("nie znaleziono postaci o first name=" + newRelation.getHeroFirstName());
            } else {
                RelationKey byName = new RelationKey(heroRepository.findById(id).get(),heroRepository.findByFirstName(newRelation.getHeroFirstName()).get());
                relationRepository.save(new Relation(byName, newRelation.getDescription()));
            }
        }



        return null;
    }

    @Override
    public Set<RelationDTO> allRelationByHero(Long id) {
        checkIfHeroDoesntExistAndThrowExcep(id);
        Set<Relation> all = relationRepository.findByKey_OwnerId(id);
        return HeroMapper.mapRelations(all);
    }

    private void checkIfHeroDoesntExistAndThrowExcep(Long id){
        if (!heroRepository.existsById(id)){
            throw new HeroNotFoundException("wybrana postać o id=" + id + " nie istnieje. Proszę wybrać istniejącą postać");
        }
    }
}
