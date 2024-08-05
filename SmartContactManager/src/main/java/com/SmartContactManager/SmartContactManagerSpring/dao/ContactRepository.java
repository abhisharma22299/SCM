package com.SmartContactManager.SmartContactManagerSpring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SmartContactManager.SmartContactManagerSpring.Entites.Contact;
import com.SmartContactManager.SmartContactManagerSpring.Entites.User;
//pegination
public interface ContactRepository extends JpaRepository<Contact,Integer> {
@Query("from Contact as c where c.user.id =:userId")
public List<Contact> findContactByUser(@Param("userId")int userId);



// searching
    public List<Contact> findByNameContainingAndUser(String name,User user);

}