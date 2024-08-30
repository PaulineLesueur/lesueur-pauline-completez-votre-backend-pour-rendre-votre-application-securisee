package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Retrieves all Trade entries.
     *
     * @return a list of all Trade objects present in the database.
     */
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    /**
     * Retrieves a Trade object by its ID.
     *
     * @param id the ID of the Trade object to retrieve.
     * @return the Trade object corresponding to the provided ID.
     * @throws IllegalArgumentException if the provided ID does not match any object in the database.
     */
    public Trade findById(int id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    }

    /**
     * Creates a new Trade object and saves it to the database.
     *
     * @param trade the Trade object to create.
     * @return the created and saved Trade object.
     */
    @Transactional
    public Trade createTrade(Trade trade) {
        trade.setTradeDate(new Timestamp(System.currentTimeMillis()));
        return tradeRepository.save(trade);
    }

    /**
     * Updates an existing Trade object with the provided new values.
     *
     * @param trade the Trade object containing the new values.
     * @param id the ID of the Trade object to update.
     * @return the updated and saved Trade object.
     * @throws IllegalArgumentException if the provided ID does not match any object in the database.
     */
    @Transactional
    public Trade updateTrade(Trade trade, int id) {
        Trade updatedTrade = findById(id);
        updatedTrade.setAccount(trade.getAccount());
        updatedTrade.setType(trade.getType());
        updatedTrade.setBuyQuantity(trade.getBuyQuantity());
        return tradeRepository.save(updatedTrade);
    }

    /**
     * Deletes a Trade object by its ID.
     *
     * @param id the ID of the Trade object to delete.
     */
    @Transactional
    public void deleteById(int id) {
        tradeRepository.deleteById(id);
    }
}
