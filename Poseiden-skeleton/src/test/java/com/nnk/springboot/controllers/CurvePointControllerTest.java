package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Collections;

@WebMvcTest(controllers = CurveController.class)
public class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    @Test
    @WithMockUser
    public void testHome() throws Exception {
        when(curvePointService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/list"))
                .andExpect(MockMvcResultMatchers.model().attribute("curvePoints", Collections.emptyList()));
    }

    @Test
    @WithMockUser
    public void testAddCurvePointForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser
    void testValidate() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointService.createCurvePoint(any(CurvePoint.class))).thenReturn(curvePoint);

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("term", "5.0")
                        .param("value", "100.0"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointService.findById(anyInt())).thenReturn(curvePoint);

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/update"))
                .andExpect(MockMvcResultMatchers.model().attribute("curvePoint", curvePoint));
    }

    @Test
    @WithMockUser
    public void testUpdateCurvePointSuccess() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointService.updateCurvePoint(any(CurvePoint.class), anyInt())).thenReturn(curvePoint);

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("term", "5.0")
                        .param("value", "100.0"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/curvePoint/list"))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors());
    }

    @Test
    @WithMockUser
    public void testDeleteCurvePoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).deleteById(1);
    }
}
