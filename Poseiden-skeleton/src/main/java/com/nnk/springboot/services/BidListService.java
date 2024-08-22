package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BidListService {
    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    public BidList findById(int id) { return bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id)); }

    public BidList createBidList(BidList bid) {
        bid.setCreationDate(new Timestamp(System.currentTimeMillis()));
        return bidListRepository.save(bid);
    }

    public BidList updateBidList(BidList bid, int id) {
        BidList updatedBidList = findById(id);
        updatedBidList.setAccount(bid.getAccount());
        updatedBidList.setType(bid.getType());
        updatedBidList.setBidQuantity(bid.getBidQuantity());
        return bidListRepository.save(updatedBidList);
    }

    public void deleteById(int id) {
        bidListRepository.deleteById(id);
    }

}
