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

    /**
     * Retrieves all RuleName entries.
     *
     * @return a list of all RuleName objects present in the database.
     */
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    /**
     * Retrieves a RuleName object by its ID.
     *
     * @param id the ID of the RuleName object to retrieve.
     * @return the RuleName object corresponding to the provided ID.
     * @throws IllegalArgumentException if the provided ID does not match any object in the database.
     */
    public RuleName findById(int id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }

    /**
     * Creates a new RuleName object and saves it to the database.
     *
     * @param ruleName the RuleName object to create.
     * @return the created and saved RuleName object.
     */
    public RuleName createRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Updates an existing RuleName object with the provided new values.
     *
     * @param ruleName the RuleName object containing the new values.
     * @param id the ID of the RuleName object to update.
     * @return the updated and saved RuleName object.
     * @throws IllegalArgumentException if the provided ID does not match any object in the database.
     */
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

    /**
     * Deletes a RuleName object by its ID.
     *
     * @param id the ID of the RuleName object to delete.
     */
    public void deleteById(int id) {
        ruleNameRepository.deleteById(id);
    }
}

