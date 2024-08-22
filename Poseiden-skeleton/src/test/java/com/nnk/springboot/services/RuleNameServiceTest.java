package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(RuleNameService.class)
public class RuleNameServiceTest {

    @Autowired
    private RuleNameService ruleNameService;

    @MockBean
    private RuleNameRepository ruleNameRepository;

    @Test
    public void testFindAll() {
        RuleName ruleName = new RuleName();
        ruleName.setName("Test Name");
        ruleName.setDescription("Test Description");
        ruleName.setJson("Test Json");
        ruleName.setTemplate("Test Template");
        ruleName.setSql("Test SqlStr");
        ruleName.setSqlPart("Test SqlPart");
        List<RuleName> ruleNames = Arrays.asList(ruleName);

        when(ruleNameRepository.findAll()).thenReturn(ruleNames);

        List<RuleName> result = ruleNameService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void testFindById() {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Test Name");
        ruleName.setDescription("Test Description");
        ruleName.setJson("{}");
        ruleName.setTemplate("Test Template");
        ruleName.setSql("Test SqlStr");
        ruleName.setSqlPart("Test SqlPart");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        RuleName result = ruleNameService.findById(1);

        assertNotNull(result);
        assertEquals("Test Name", result.getName());
    }

    @Test
    public void testFindById_NotFound() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ruleNameService.findById(1));
    }

    @Test
    public void testCreateRuleName() {
        RuleName ruleName = new RuleName();
        ruleName.setName("Test Name");
        ruleName.setDescription("Test Description");
        ruleName.setJson("Test Json");
        ruleName.setTemplate("Test Template");
        ruleName.setSql("Test SqlStr");
        ruleName.setSqlPart("Test SqlPart");

        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName result = ruleNameService.createRuleName(ruleName);

        assertEquals(ruleName.getName(), result.getName());
        assertEquals(ruleName.getDescription(), result.getDescription());
        assertEquals(ruleName.getJson(), result.getJson());
        assertEquals(ruleName.getTemplate(), result.getTemplate());
        assertEquals(ruleName.getSql(), result.getSql());
        assertEquals(ruleName.getSqlPart(), result.getSqlPart());
    }

    @Test
    public void testUpdateRuleName() {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Original Name");
        ruleName.setDescription("Original Description");
        ruleName.setJson("Original Json");
        ruleName.setTemplate("Original Template");
        ruleName.setSql("Original SqlStr");
        ruleName.setSqlPart("Original SqlPart");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName updatedRuleName = new RuleName();
        updatedRuleName.setName("Updated Name");
        updatedRuleName.setDescription("Updated Description");
        updatedRuleName.setJson("Updated Json");
        updatedRuleName.setTemplate("Updated Template");
        updatedRuleName.setSql("Updated SqlStr");
        updatedRuleName.setSqlPart("Updated SqlPart");

        RuleName result = ruleNameService.updateRuleName(updatedRuleName, 1);

        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals("Updated Json", result.getJson());
        assertEquals("Updated Template", result.getTemplate());
        assertEquals("Updated SqlStr", result.getSql());
        assertEquals("Updated SqlPart", result.getSqlPart());
    }

    @Test
    public void testDeleteById() {
        ruleNameService.deleteById(1);

        verify(ruleNameRepository, times(1)).deleteById(1);
    }

}
