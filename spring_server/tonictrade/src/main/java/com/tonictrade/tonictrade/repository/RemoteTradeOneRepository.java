package com.tonictrade.tonictrade.repository;

import com.tonictrade.tonictrade.datamodel.RemoteTradeOneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RemoteTradeOneRepository extends JpaRepository<RemoteTradeOneData,Long> {

}
