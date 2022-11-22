package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.AuthorDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Author;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionChecker;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ResourceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.UpdateConflictException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.AuthorMapper;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.AuthorRepository;
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

    public AuthorServiceImpl(AuthorRepository authorRepository,
                             ExceptionChecker exceptCheck) {
        this.authorRepository = authorRepository;
        this.exceptCheck = exceptCheck;
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
                .orElseThrow(() -> new ResourceNotFoundException("Author o id: " + id + " nie istnieje"));
        return mapAuthorToDTO(author);
    }

    @Override
    public AuthorDTO getAuthorByLastName(String name) {
        Author author = authorRepository.findByLastName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Author o name: " + name + " nie istnieje"));
        return mapAuthorToDTO(author);
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        exceptCheck.ifAuthorNamesAreNull(authorDTO.getFirstName(), authorDTO.getLastName());
        if (authorRepository.existsByFirstNameAndLastName(authorDTO.getFirstName(), authorDTO.getLastName())) {
            throw new UpdateConflictException("Author with this name and last name already exist");
        }
        return mapAuthorToDTO(authorRepository.save(mapDTOToAuthor(authorDTO)));
    }

    @Override
    public AuthorDTO updateAuthor(AuthorDTO authorDTO) {
        return null;
    }

    @Transactional
    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAuthorByFirstAndLastNames(String firstName, String lastName) {
        authorRepository.deleteByFirstNameAndLastName(firstName, lastName);
    }
}
