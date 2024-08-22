package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    public Trade findById(int id) { return tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id)); }

    public Trade createTrade(Trade trade) {
        trade.setTradeDate(new Timestamp(System.currentTimeMillis()));
        return tradeRepository.save(trade);
    }

    public Trade updateTrade(Trade trade, int id) {
        Trade updatedTrade = findById(id);
        updatedTrade.setAccount(trade.getAccount());
        updatedTrade.setType(trade.getType());
        updatedTrade.setBuyQuantity(trade.getBuyQuantity());
        return tradeRepository.save(updatedTrade);
    }

    public void deleteById(int id) {
        tradeRepository.deleteById(id);
    }

}
