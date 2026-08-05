package com.harithasewana.identity.repository;

import com.harithasewana.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

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

}
