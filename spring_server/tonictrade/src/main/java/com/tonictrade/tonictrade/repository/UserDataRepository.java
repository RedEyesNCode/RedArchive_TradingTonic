package com.tonictrade.tonictrade.repository;

import com.tonictrade.tonictrade.datamodel.SignupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<SignupModel, Long> {


    //Query to insert the user in the database if the email and phone number does not exists already.


    @Query("SELECT s FROM SignupModel s WHERE s.userEmail = ?1")
    Optional<SignupModel> findbyEmail(String email);

    @Query("SELECT s FROM SignupModel s WHERE s.number = ?1")
    Optional<SignupModel> findbyPassword(String Number);

    @Query("SELECT s FROM SignupModel s WHERE s.userEmail = :userEmail AND s.password = :userpassword" )
    Optional<SignupModel> loginUser(@Param("userEmail") String userEmail,@Param("userpassword") String userpassword);




}
