package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    public PersonRepository rep;

    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        Person personToSave = PersonMapper.INSTANCE.toModel(personDTO);
        Person savedPerson = rep.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created person with ID ");
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = rep.findAll();
        return allPeople.stream()
                .map(PersonMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);
        return PersonMapper.INSTANCE.toDTO(person);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);
        Person personToUpdate = PersonMapper.INSTANCE.toModel(personDTO);
        Person updatePerson = rep.save(personToUpdate);
        return createMessageResponse(updatePerson.getId(), "Update Person with ID ");
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        rep.deleteById(id);
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return rep.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}
