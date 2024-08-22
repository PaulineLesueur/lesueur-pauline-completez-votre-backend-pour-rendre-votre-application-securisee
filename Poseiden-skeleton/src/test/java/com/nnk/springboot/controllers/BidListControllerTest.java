package com.nnk.springboot.controllers;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;

@WebMvcTest(controllers = BidListController.class)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    @Test
    @WithMockUser
    public void testHome() throws Exception {
        when(bidListService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("bidLists", Collections.emptyList()));
    }

    @Test
    @WithMockUser
    public void testAddBidForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser
    void testValidate() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .with(csrf().asHeader())
                        .param("account", "test")
                        .param("type", "test")
                        .param("bidQuantity", "10.0"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        BidList bidList = new BidList();
        when(bidListService.findById(anyInt())).thenReturn(bidList);

        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attribute("bidList", bidList));
    }

    @Test
    @WithMockUser
    public void testUpdateBidSuccess() throws Exception {
        BidList bidList = new BidList();
        when(bidListService.updateBidList(bidList, 1)).thenReturn(bidList);
        mockMvc.perform(post("/bidList/update/1")
                        .with(csrf().asHeader())
                        .param("id", "1")
                        .param("account", "test")
                        .param("type", "test").param("bidQuantity", "10.0"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser
    public void testDeleteBid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).deleteById(1);
    }
}
