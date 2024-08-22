package com.nnk.springboot.controllers;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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

@WebMvcTest(controllers = RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Test
    @WithMockUser
    public void testHome() throws Exception {
        when(ratingService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/list"))
                .andExpect(MockMvcResultMatchers.model().attribute("ratings", Collections.emptyList()));
    }

    @Test
    @WithMockUser
    public void testAddRatingForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/add"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {
        Rating rating = new Rating();
        when(ratingService.createRating(any(Rating.class))).thenReturn(rating);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("moodysRating", "Test Moody")
                        .param("sandPRating", "Test SandPRating")
                        .param("fitchRating", "Test Fitch")
                        .param("order", "1"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        Rating rating = new Rating();
        when(ratingService.findById(anyInt())).thenReturn(rating);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/update"))
                .andExpect(MockMvcResultMatchers.model().attribute("rating", rating));
    }

    @Test
    @WithMockUser
    public void testUpdateRatingSuccess() throws Exception {
        Rating rating = new Rating();
        when(ratingService.updateCurvePoint(any(Rating.class), anyInt())).thenReturn(rating);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("moodysRating", "Updated Moody")
                        .param("sandPRating", "Updated SandPRating")
                        .param("fitchRating", "Updated Fitch")
                        .param("order", "2"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser
    public void testDeleteRating() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).deleteById(1);
    }
}
