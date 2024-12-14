package com.example.libraryapp.NEWdomain.user.ports;

import com.example.libraryapp.NEWdomain.user.model.Person;

public interface PersonRepository {
    Person save(Person personToSave);
}
