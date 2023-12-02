package com.tonictrade.tonictrade.repository;

import com.tonictrade.tonictrade.datamodel.TradeDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TradeDataRepository extends JpaRepository<TradeDataModel,Long> {




}
