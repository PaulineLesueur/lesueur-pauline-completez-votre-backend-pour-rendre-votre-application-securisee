package com.nnk.springboot.controllers;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @Test
    @WithMockUser
    public void testHome() throws Exception {
        when(tradeService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/list"))
                .andExpect(MockMvcResultMatchers.model().attribute("trades", Collections.emptyList()));
    }

    @Test
    @WithMockUser
    public void testAddTradeForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/add"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {
        Trade trade = new Trade();
        when(tradeService.createTrade(any(Trade.class))).thenReturn(trade);

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account", "Test Account")
                        .param("type", "Test Type")
                        .param("buyQuantity", "100.0"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        Trade trade = new Trade();
        when(tradeService.findById(anyInt())).thenReturn(trade);

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/update"))
                .andExpect(MockMvcResultMatchers.model().attribute("trade", trade));
    }

    @Test
    @WithMockUser
    public void testUpdateTradeSuccess() throws Exception {
        Trade trade = new Trade();
        when(tradeService.updateTrade(any(Trade.class), anyInt())).thenReturn(trade);

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account", "Updated Account")
                        .param("type", "Updated Type")
                        .param("buyQuantity", "150.0"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser
    public void testDeleteTrade() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).deleteById(1);
    }
}
