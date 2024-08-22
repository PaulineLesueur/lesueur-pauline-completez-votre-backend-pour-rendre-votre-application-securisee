package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(TradeService.class)
public class TradeServiceTest {

    @Autowired
    private TradeService tradeService;

    @MockBean
    private TradeRepository tradeRepository;

    @Test
    public void testFindAll() {
        Trade trade = new Trade();
        trade.setAccount("Test Account");
        trade.setType("Test Type");
        trade.setBuyQuantity(100.0);
        trade.setTradeDate(new Timestamp(System.currentTimeMillis()));
        List<Trade> trades = Arrays.asList(trade);

        when(tradeRepository.findAll()).thenReturn(trades);

        List<Trade> result = tradeService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void testFindById() {
        Trade trade = new Trade();
        trade.setId(1);
        trade.setAccount("Test Account");
        trade.setType("Test Type");
        trade.setBuyQuantity(100.0);
        trade.setTradeDate(new Timestamp(System.currentTimeMillis()));

        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Trade result = tradeService.findById(1);

        assertNotNull(result);
        assertEquals("Test Account", result.getAccount());
    }

    @Test
    public void testFindById_NotFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> tradeService.findById(1));
    }

    @Test
    public void testCreateTrade() {
        Trade trade = new Trade();
        trade.setAccount("Test Account");
        trade.setType("Test Type");
        trade.setBuyQuantity(100.0);
        trade.setTradeDate(new Timestamp(System.currentTimeMillis()));

        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade result = tradeService.createTrade(trade);

        assertEquals(trade.getAccount(), result.getAccount());
        assertEquals(trade.getType(), result.getType());
        assertEquals(trade.getBuyQuantity(), result.getBuyQuantity());
    }

    @Test
    public void testUpdateTrade() {
        Trade trade = new Trade();
        trade.setId(1);
        trade.setAccount("Test Account");
        trade.setType("Test Type");
        trade.setBuyQuantity(100.0);

        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade updatedTrade = new Trade();
        updatedTrade.setAccount("Updated Account");
        updatedTrade.setType("Updated Type");
        updatedTrade.setBuyQuantity(200.0);

        Trade result = tradeService.updateTrade(updatedTrade, 1);

        assertEquals("Updated Account", result.getAccount());
        assertEquals("Updated Type", result.getType());
        assertEquals(200.0, result.getBuyQuantity());
    }

    @Test
    public void testDeleteById() {
        tradeService.deleteById(1);

        verify(tradeRepository, times(1)).deleteById(1);
    }

}
