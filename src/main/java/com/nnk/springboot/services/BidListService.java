package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing BidList entities.
 * This class provides methods for CRUD operations on BidList entities.
 */
@Service
public class BidListService {

    @Autowired
    BidListRepository bidListRepository;

    /**
     * Retrieves all BidList entities.
     *
     * @return a List of all BidList entities.
     */
    public List<BidList> findAllBids() {
        return bidListRepository.findAll();
    }

    /**
     * Saves a new BidList entity.
     *
     * @param bidList the BidList entity to save.
     * @throws IllegalArgumentException if the account or type is invalid.
     */
    public void save(@NotNull BidList bidList) {

        Utils.stringIsValide(bidList.getAccount(), "Account");
        Utils.stringIsValide(bidList.getType(), "Type");

        bidListRepository.save(bidList);
    }

    /**
     * Retrieves a BidList by its ID.
     *
     * @param id the ID of the BidList to retrieve.
     * @return the BidList entity.
     * @throws RuntimeException if no BidList is found with the given ID.
     */
    public BidList getBidById(int id) {
        Optional<BidList> optionalBidList = bidListRepository.findById(id);
        if (optionalBidList.isEmpty()) {
            throw new RuntimeException("No Bid with this id : " + id + " found !");
        }
        return optionalBidList.get();
    }

    /**
     * Updates an existing BidList entity.
     *
     * @param updateBid the BidList entity with updated information.
     * @throws RuntimeException if no BidList is found with the given ID.
     * @throws IllegalArgumentException if the account or type is invalid.
     */
    public void updateBid(@NotNull BidList updateBid) {
        // Retrieve the existing BidList
        BidList oldBid = getBidById(updateBid.getBidListId());

        // Ensure the ID matches the existing entity
        if(!oldBid.getBidListId().equals(updateBid.getBidListId())) {
            throw new RuntimeException("No Bid with this id : " + updateBid.getBidListId() + " found !");
        }

        // Save the updated BidList
        save(updateBid);
    }

    /**
     * Deletes a BidList by its ID.
     *
     * @param bidListId the ID of the BidList to delete.
     * @throws RuntimeException if no BidList is found with the given ID.
     */
    public void delete(@NotNull int bidListId) {
        // Retrieve the existing BidList
        BidList bidListToDelete = getBidById(bidListId);

        // Delete the BidList
        bidListRepository.delete(bidListToDelete);
    }
}
