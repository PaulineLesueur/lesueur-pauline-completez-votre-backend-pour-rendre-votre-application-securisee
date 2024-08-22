package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService {
    @Autowired
    private RuleNameRepository ruleNameRepository;

    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    public RuleName findById(int id) { return ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id)); }

    @Transactional
    public RuleName createRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    @Transactional
    public RuleName updateRuleName(RuleName ruleName, int id) {
        RuleName updatedRuleName = findById(id);
        updatedRuleName.setName(ruleName.getName());
        updatedRuleName.setDescription(ruleName.getDescription());
        updatedRuleName.setJson(ruleName.getJson());
        updatedRuleName.setTemplate(ruleName.getTemplate());
        updatedRuleName.setSql(ruleName.getSql());
        updatedRuleName.setSqlPart(ruleName.getSqlPart());
        return ruleNameRepository.save(updatedRuleName);
    }

    @Transactional
    public void deleteById(int id) {
        ruleNameRepository.deleteById(id);
    }
}
