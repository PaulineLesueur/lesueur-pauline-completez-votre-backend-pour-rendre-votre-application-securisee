package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(BidListService.class)
public class BidListServiceTest {

    @Autowired
    private BidListService bidListService;

    @MockBean
    private BidListRepository bidListRepository;

    @Test
    public void testFindAll() {
        BidList bid = new BidList();
        bid.setAccount("Test Account");
        bid.setType("Test Type");
        bid.setBidQuantity(10.0);
        bid.setCreationDate(new Timestamp(System.currentTimeMillis()));
        List<BidList> bids = Arrays.asList(bid);

        when(bidListRepository.findAll()).thenReturn(bids);

        List<BidList> result = bidListService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void testFindById() {
        BidList bid = new BidList();
        bid.setId(1);
        bid.setAccount("Test Account");
        bid.setType("Test Type");
        bid.setBidQuantity(10.0);
        bid.setCreationDate(new Timestamp(System.currentTimeMillis()));

        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        BidList result = bidListService.findById(1);

        assertNotNull(result);
        assertEquals("Test Account", result.getAccount());
    }

    @Test
    public void testFindById_NotFound() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> bidListService.findById(1));
    }

    @Test
    public void testCreateBidList() {
        BidList bid = new BidList();
        bid.setAccount("Test Account");
        bid.setType("Test Type");
        bid.setBidQuantity(10.0);
        bid.setCreationDate(new Timestamp(System.currentTimeMillis()));

        when(bidListRepository.save(any(BidList.class))).thenReturn(bid);

        BidList result = bidListService.createBidList(bid);

        assertEquals(bid.getAccount(), result.getAccount());
        assertEquals(bid.getType(), result.getType());
        assertEquals(bid.getBidQuantity(), result.getBidQuantity());
    }

    @Test
    public void testUpdateBidList() {
        BidList bid = new BidList();
        bid.setId(1);
        bid.setAccount("Test Account");
        bid.setType("Test Type");
        bid.setBidQuantity(10.0);

        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));
        when(bidListRepository.save(any(BidList.class))).thenReturn(bid);

        BidList updatedBid = new BidList();
        updatedBid.setAccount("Updated Account");
        updatedBid.setType("Updated Type");
        updatedBid.setBidQuantity(20.0);

        BidList result = bidListService.updateBidList(updatedBid, 1);

        assertEquals("Updated Account", result.getAccount());
        assertEquals("Updated Type", result.getType());
        assertEquals(20.0, result.getBidQuantity());
    }

    @Test
    public void testDeleteById() {
        bidListService.deleteById(1);

        verify(bidListRepository, times(1)).deleteById(1);
    }
}
