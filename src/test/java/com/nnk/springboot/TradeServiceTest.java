package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
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
public class TradeServiceTest {

    @Mock
    TradeRepository tradeRepository;

    @InjectMocks
    TradeService tradeService;

    @Test
    public void findAllRuleName() {

        // Arrange
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Trade");
        trade.setType("Type");

        List<Trade> listTrade = Arrays.asList(trade);

        when(tradeRepository.findAll()).thenReturn(listTrade);

        // Act
        List<Trade> result = tradeService.findAllTrade();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Trade", result.get(0).getAccount());
    }

    @Test
    public void addRuleName_Successful() {

        // Arrange
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Trade");
        trade.setType("Type");

        // Act
        tradeService.save(trade);

        // Assert
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    public void getById() {

        // Arrange
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Trade");
        trade.setType("Type");

        int tradeID = trade.getTradeId();

        when(tradeRepository.findById(tradeID)).thenReturn(Optional.of(trade));
        // Act
        Trade result = tradeService.getTradeById(tradeID);

        // Assert
        assertEquals(trade.getTradeId(), result.getTradeId());
    }

    @Test
    public void getRuleNameByID_WhenNotFound() {
        // Arrange
        int id = 1;
        when(tradeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tradeService.getTradeById(id);
        });
        assertEquals("No Trade with this id : 1", exception.getMessage());
    }

    @Test
    public void updateRuleName() {

        // Arrange
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Trade");
        trade.setType("Type");

        int tradeID = trade.getTradeId();

        when(tradeRepository.findById(tradeID)).thenReturn(Optional.of(trade));

        // Act
        tradeService.update(trade);

        // Assert
        verify(tradeRepository).save(trade);
        assertEquals("Trade", trade.getAccount());
    }

    @Test
    public void ruleNameUpdate_WhenNotFound() {

        // Arrange
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Trade");
        trade.setType("Type");

        int nonExistentId = 2;
        trade.setTradeId(nonExistentId);
        when(tradeRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tradeService.update(trade);
        });
        assertEquals("No Trade with this id : " + nonExistentId, exception.getMessage());
    }

    @Test
    public void deleteRuleName() {

        // Arrange
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Trade");
        trade.setType("Type");

        int tradeID = trade.getTradeId();

        when(tradeRepository.findById(tradeID)).thenReturn(Optional.of(trade));

        // Act
        tradeService.delete(tradeID);

        // Assert
        verify(tradeRepository).delete(trade);
    }

    @Test
    public void deleteRuleName_WhenIdNoFound() {

        // Arrange
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Trade");
        trade.setType("Type");

        int nonExistentId = 2;

        when(tradeRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tradeService.delete(nonExistentId);
        });
        assertEquals("No Trade with this id : " + nonExistentId, exception.getMessage());
    }
}
