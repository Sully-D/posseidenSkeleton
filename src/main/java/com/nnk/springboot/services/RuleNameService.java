package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing RuleName entities.
 * This class provides methods for CRUD operations on RuleName entities.
 */
@Service
public class RuleNameService {

    @Autowired
    RuleNameRepository ruleNameRepository;

    /**
     * Retrieves all RuleName entities.
     *
     * @return a List of all RuleName entities.
     */
    public List<RuleName> findAllRuleName() {
        return ruleNameRepository.findAll();
    }

    /**
     * Saves a new RuleName entity.
     *
     * @param ruleName the RuleName entity to save.
     */
    public void save(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }

    /**
     * Retrieves a RuleName by its ID.
     *
     * @param ruleNameId the ID of the RuleName to retrieve.
     * @return the RuleName entity.
     * @throws RuntimeException if no RuleName is found with the given ID.
     */
    public RuleName getRuleNameById(int ruleNameId) {
        Optional<RuleName> optionalRuleName = ruleNameRepository.findById(ruleNameId);
        if (optionalRuleName.isEmpty()) {
            throw new RuntimeException("No RuleName with this id : " + ruleNameId);
        }
        return optionalRuleName.get();
    }

    /**
     * Updates an existing RuleName entity.
     *
     * @param ruleName the RuleName entity with updated information.
     * @throws RuntimeException if no RuleName is found with the given ID.
     * @throws IllegalArgumentException if the account or type is invalid.
     */
    public void update(RuleName ruleName) {
        // Retrieve the existing RuleName
        getRuleNameById(ruleName.getId());

        // Save the updated RuleName
        save(ruleName);
    }

    /**
     * Deletes a RuleName by its ID.
     *
     * @param ruleNameId the ID of the RuleName to delete.
     * @throws RuntimeException if no RuleName is found with the given ID.
     */
    public void delete(int ruleNameId) {
        // Retrieve the existing RuleName
        RuleName ruleNametToDelete = getRuleNameById(ruleNameId);

        // Delete the CurvePoint
        ruleNameRepository.delete(ruleNametToDelete);
    }
}
