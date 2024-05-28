package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Trade entities.
 * This class provides methods for CRUD operations on Trade entities.
 */
@Service
public class TradeService {

    @Autowired
    TradeRepository tradeRepository;

    /**
     * Retrieves all Trade entities.
     *
     * @return a List of all Trade entities.
     */
    public List<Trade> findAllTrade() {
        return tradeRepository.findAll();
    }

    /**
     * Saves a new Trade entity.
     *
     * @param trade the RuleName entity to save.
     */
    public void save(Trade trade) {
        tradeRepository.save(trade);
    }

    /**
     * Retrieves a Trade by its ID.
     *
     * @param tradeID the ID of the Trade to retrieve.
     * @return the RuleName entity.
     * @throws RuntimeException if no Trade is found with the given ID.
     */
    public Trade getTradeById(int tradeID) {
        Optional<Trade> optionalTrade = tradeRepository.findById(tradeID);
        if (optionalTrade.isEmpty()) {
            throw new RuntimeException("No Trade with this id : " + tradeID);
        }
        return optionalTrade.get();
    }

    /**
     * Updates an existing Trade entity.
     *
     * @param trade the Trade entity with updated information.
     * @throws RuntimeException if no Trade is found with the given ID.
     * @throws IllegalArgumentException if the account or type is invalid.
     */
    public void update(Trade trade) {
        // Retrieve the existing Trade
        getTradeById(trade.getTradeId());

        // Save the updated RuleName
        save(trade);
    }

    /**
     * Deletes a Trade by its ID.
     *
     * @param tradeID the ID of the RuleName to delete.
     * @throws RuntimeException if no Trade is found with the given ID.
     */
    public void delete(int tradeID) {
        // Retrieve the existing Trade
        Trade tradeToDelete = getTradeById(tradeID);

        // Delete the CurvePoint
        tradeRepository.delete(tradeToDelete);
    }
}
