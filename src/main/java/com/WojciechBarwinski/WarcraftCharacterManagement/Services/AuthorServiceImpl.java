package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.AuthorDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Author;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionChecker;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionMessage;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ResourceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.UpdateConflictException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.AuthorMapper;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.AuthorRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.AuthorMapper.mapAuthorToDTO;
import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.AuthorMapper.mapDTOToAuthor;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ExceptionChecker exceptCheck;
    private final ExceptionMessage em;

    public AuthorServiceImpl(AuthorRepository authorRepository,
                             ExceptionChecker exceptCheck,
                             ExceptionMessage exceptionMessage) {
        this.authorRepository = authorRepository;
        this.exceptCheck = exceptCheck;
        this.em = exceptionMessage;
    }

    @Override
    public Set<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(AuthorMapper::mapAuthorToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noAuthorId") + id));
        return mapAuthorToDTO(author);
    }

    @Override
    public AuthorDTO getAuthorByLastName(String name) {
        Author author = authorRepository.findByLastNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noAuthorName") + name));
        return mapAuthorToDTO(author);
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO dto) {
        exceptCheck.ifAuthorNamesAreNull(dto.getFirstName(), dto.getLastName());
        if (authorRepository.existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(dto.getFirstName(), dto.getLastName())) {
            throw new UpdateConflictException(em.getMessage("noAuthorNames"));
        }
        Author save = authorRepository.save(mapDTOToAuthor(dto));
        return mapAuthorToDTO(save);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO dto) {
        Author save = authorRepository.save(buildUpdateAuthor(dto, id));
        return mapAuthorToDTO(save);
    }

    @Transactional
    @Override
    public void deleteAuthorById(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException(em.getMessage("noAuthorId") + id);
        }
        authorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAuthorByFirstAndLastNames(String firstName, String lastName) {
        exceptCheck.ifAuthorNamesAreNull(firstName, lastName);
        if (!authorRepository.existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName)) {
            throw new ResourceNotFoundException(em.getMessage("noAuthorNames"));
        }
        authorRepository.deleteByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
    }


    private Author buildUpdateAuthor(AuthorDTO dto, Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noAuthorId") + id));

        if (!StringUtils.isBlank(dto.getFirstName())) {
            author.setFirstName(dto.getFirstName());
        }

        if (!StringUtils.isBlank(dto.getLastName())) {
            author.setLastName(dto.getLastName());
        }

        if (!StringUtils.isBlank(dto.getNationality())) {
            author.setNationality(dto.getNationality());
        }

        return author;
    }
}
