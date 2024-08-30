package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    /**
     * Retrieves all BidList entries.
     *
     * @return a list containing all BidList objects present in the database.
     */
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    /**
     * Retrieves a BidList object by its ID.
     *
     * @param id the ID of the BidList object to retrieve.
     * @return the BidList object corresponding to the provided ID.
     * @throws IllegalArgumentException if the provided ID does not match any object in the database.
     */
    public BidList findById(int id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
    }

    /**
     * Creates a new BidList object and saves it to the database.
     *
     * @param bid the BidList object to create.
     * @return the BidList object that has been created and saved to the database.
     */
    @Transactional
    public BidList createBidList(BidList bid) {
        bid.setCreationDate(new Timestamp(System.currentTimeMillis()));
        return bidListRepository.save(bid);
    }

    /**
     * Updates an existing BidList object with the provided new values.
     *
     * @param bid the BidList object containing the new values.
     * @param id the ID of the BidList object to update.
     * @return the updated BidList object saved to the database.
     * @throws IllegalArgumentException if the provided ID does not match any object in the database.
     */
    @Transactional
    public BidList updateBidList(BidList bid, int id) {
        BidList updatedBidList = findById(id);
        updatedBidList.setAccount(bid.getAccount());
        updatedBidList.setType(bid.getType());
        updatedBidList.setBidQuantity(bid.getBidQuantity());
        return bidListRepository.save(updatedBidList);
    }

    /**
     * Deletes a BidList object by its ID.
     *
     * @param id the ID of the BidList object to delete.
     */
    @Transactional
    public void deleteById(int id) {
        bidListRepository.deleteById(id);
    }
}
