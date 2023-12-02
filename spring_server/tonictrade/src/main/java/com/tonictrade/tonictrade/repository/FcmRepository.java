package com.tonictrade.tonictrade.repository;


import com.tonictrade.tonictrade.datamodel.FcmTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface FcmRepository extends JpaRepository<FcmTokenModel,Long> {


    //Method to check if fcm already exists in data if not then insert it.
    @Query("SELECT s FROM FcmTokenModel s WHERE s.fcmToken = ?1")
    Optional<FcmTokenModel> findByfcmToken(String fcmToken);


    @Transactional
    @Modifying
    @Query("UPDATE FcmTokenModel s SET s.fcmToken = :fcmTokenNew WHERE s.userName = :userNameInput")
    int updateFcmToken(@Param("fcmTokenNew") String fcmTokenNew, @Param("userNameInput") String userNameInput);


}
