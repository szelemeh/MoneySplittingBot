package com.dbteam.service;

import com.dbteam.exception.IllegalUsernameException;
import com.dbteam.model.Person;
import com.dbteam.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void addPerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public void updatePerson(Person person) {
        Person newPerson = new Person();
        person.saveTo(newPerson);
        personRepository.save(newPerson);
    }

    @Override
    public Person findPersonByUsername(String username) throws IllegalUsernameException {
        Optional<Person> optionalPerson = personRepository.findUserByUsername(username);
        return optionalPerson.orElseThrow(() -> new IllegalUsernameException("There is no person in db with username: " + username));
    }
}
