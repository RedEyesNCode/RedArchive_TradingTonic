package com.tonictrade.tonictrade.repository;

import com.tonictrade.tonictrade.datamodel.TradeHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface TradeHistoryRepository extends JpaRepository<TradeHistoryModel,Long> {

    @Query("SELECT t from TradeHistoryModel t where t.month = :monthx")
    List<TradeHistoryModel> filterByMonth(@Param("monthx") int month);

    @Transactional
    @Modifying
    @Query("UPDATE TradeHistoryModel t SET t.tradeMessage = :tradeMessageInput, t.title = :tradeTitleInput WHERE t.id = :idInput")
    void updateTradeData(String tradeMessageInput,String tradeTitleInput,Long idInput);

    @Transactional
    @Modifying
    @Query("UPDATE TradeHistoryModel t SET t.tradeImage = :tradeImageUrl WHERE t.id = :idInput")
    void updateTradeImage(@Param("tradeImageUrl") String tradeImageUrl,@Param("idInput") Long idInput);

}
