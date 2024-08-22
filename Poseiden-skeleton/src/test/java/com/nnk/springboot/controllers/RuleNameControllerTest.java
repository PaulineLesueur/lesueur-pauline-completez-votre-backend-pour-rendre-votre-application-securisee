package com.nnk.springboot.controllers;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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

@WebMvcTest(controllers = RuleNameController.class)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @Test
    @WithMockUser
    public void testHome() throws Exception {
        when(ruleNameService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ruleName/list"))
                .andExpect(MockMvcResultMatchers.model().attribute("ruleNames", Collections.emptyList()));
    }

    @Test
    @WithMockUser
    public void testAddRuleForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ruleName/add"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {
        RuleName ruleName = new RuleName();
        when(ruleNameService.createRuleName(any(RuleName.class))).thenReturn(ruleName);

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("name", "Test Name")
                        .param("description", "Test Description")
                        .param("json", "Test JSON")
                        .param("template", "Test Template")
                        .param("sql", "Test SQL")
                        .param("sqlPart", "Test SQL Part"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        RuleName ruleName = new RuleName();
        when(ruleNameService.findById(anyInt())).thenReturn(ruleName);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ruleName/update"))
                .andExpect(MockMvcResultMatchers.model().attribute("ruleName", ruleName));
    }

    @Test
    @WithMockUser
    public void testUpdateRuleNameSuccess() throws Exception {
        RuleName ruleName = new RuleName();
        when(ruleNameService.updateRuleName(any(RuleName.class), anyInt())).thenReturn(ruleName);

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("name", "Updated Name")
                        .param("description", "Updated Description")
                        .param("json", "Updated JSON")
                        .param("template", "Updated Template")
                        .param("sql", "Updated SQL")
                        .param("sqlPart", "Updated SQL Part"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser
    public void testDeleteRuleName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).deleteById(1);
    }
}
