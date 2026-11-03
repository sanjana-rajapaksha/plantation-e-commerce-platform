package com.harithasewana.identity.repository;

import com.harithasewana.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
interface Animal {
    void eat();
}

interface Dog extends Animal
{

}
Even though Dog is empty, it still has: eat(); like that
*/

//User - Entity , Long - Primary key Type (Id)
public interface UserRepository extends JpaRepository<User, Long> {

    //Spring Data JPA can generate database queries based on method names.
    //findByEmail(email) -> Find a user by email
    //existsByEmail(email) -> Check whether an email exists
    //findByFirstName(name) -> Find users by first name
    //existsById(id) -> Check whether a user ID exists

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id); /*existsByEmail – Check whether an email exists. AndIdNot – Exclude the user whose ID is provided.*/
    Optional<User> findByEmail(String email);
}

//By extending:
//JpaRepository<User, Long>

//we get methods such as:

//save(user);
//findAll();
//findById(id);
//deleteById(id);
//existsById(id);
//count();