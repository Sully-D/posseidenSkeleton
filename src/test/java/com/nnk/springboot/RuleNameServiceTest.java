package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    RuleNameRepository ruleNameRepository;

    @InjectMocks
    RuleNameService ruleNameService;

    @Test
    public void findAllRuleName() {

        // Arrange
        RuleName ruleName = new RuleName();
        ruleName.setName("Name");
        ruleName.setDescription("Description");
        ruleName.setJson("json");
        ruleName.setTemplate("Template");
        ruleName.setSqlStr("SQL str");
        ruleName.setSqlPart("SQL part");

        List<RuleName> listRuleNames = Arrays.asList(ruleName);

        when(ruleNameRepository.findAll()).thenReturn(listRuleNames);

        // Act
        List<RuleName> result = ruleNameService.findAllRuleName();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Name", result.get(0).getName());
    }

    @Test
    public void addRuleName_Successful() {

        // Arrange
        RuleName ruleName = new RuleName();
        ruleName.setName("Name");
        ruleName.setDescription("Description");
        ruleName.setJson("json");
        ruleName.setTemplate("Template");
        ruleName.setSqlStr("SQL str");
        ruleName.setSqlPart("SQL part");

        // Act
        ruleNameService.save(ruleName);

        // Assert
        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    @Test
    public void getById() {

        // Arrange
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Name");
        ruleName.setDescription("Description");
        ruleName.setJson("json");
        ruleName.setTemplate("Template");
        ruleName.setSqlStr("SQL str");
        ruleName.setSqlPart("SQL part");

        int ruleNameId = ruleName.getId();

        when(ruleNameRepository.findById(ruleNameId)).thenReturn(Optional.of(ruleName));
        // Act
        RuleName result = ruleNameService.getRuleNameById(ruleNameId);

        // Assert
        assertEquals(ruleName.getId(), result.getId());
    }

    @Test
    public void getRuleNameByID_WhenNotFound() {
        // Arrange
        int id = 1;
        when(ruleNameRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ruleNameService.getRuleNameById(id);
        });
        assertEquals("No RuleName with this id : 1", exception.getMessage());
    }

    @Test
    public void updateRuleName() {

        // Arrange
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Name");
        ruleName.setDescription("Description");
        ruleName.setJson("json");
        ruleName.setTemplate("Template");
        ruleName.setSqlStr("SQL str");
        ruleName.setSqlPart("SQL part");

        int ruleNameId = ruleName.getId();

        when(ruleNameRepository.findById(ruleNameId)).thenReturn(Optional.of(ruleName));

        // Act
        ruleNameService.update(ruleName);

        // Assert
        verify(ruleNameRepository).save(ruleName);
        assertEquals("Name", ruleName.getName());
    }

    @Test
    public void ruleNameUpdate_WhenNotFound() {

        // Arrange
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Name");
        ruleName.setDescription("Description");
        ruleName.setJson("json");
        ruleName.setTemplate("Template");
        ruleName.setSqlStr("SQL str");
        ruleName.setSqlPart("SQL part");

        int nonExistentId = 2;
        ruleName.setId(nonExistentId);
        when(ruleNameRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ruleNameService.update(ruleName);
        });
        assertEquals("No RuleName with this id : " + nonExistentId, exception.getMessage());
    }

    @Test
    public void deleteRuleName() {

        // Arrange
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Name");
        ruleName.setDescription("Description");
        ruleName.setJson("json");
        ruleName.setTemplate("Template");
        ruleName.setSqlStr("SQL str");
        ruleName.setSqlPart("SQL part");

        int ruleNameId = ruleName.getId();

        when(ruleNameRepository.findById(ruleNameId)).thenReturn(Optional.of(ruleName));

        // Act
        ruleNameService.delete(ruleNameId);

        // Assert
        verify(ruleNameRepository).delete(ruleName);
    }

    @Test
    public void deleteRuleName_WhenIdNoFound() {

        // Arrange
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Name");
        ruleName.setDescription("Description");
        ruleName.setJson("json");
        ruleName.setTemplate("Template");
        ruleName.setSqlStr("SQL str");
        ruleName.setSqlPart("SQL part");

        int nonExistentId = 2;
        when(ruleNameRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ruleNameService.delete(nonExistentId);
        });
        assertEquals("No RuleName with this id : " + nonExistentId, exception.getMessage());
    }
}
