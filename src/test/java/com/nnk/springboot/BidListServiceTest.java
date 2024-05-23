package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @Test
    public void findAllBids() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Arrange
        BidList bidList1 = new BidList();
        bidList1.setBidListId(1);
        bidList1.setAccount("user");
        bidList1.setType("user");
        bidList1.setBidQuantity(10.0);
        bidList1.setAskQuantity(2.0);
        bidList1.setBid(3.0);
        bidList1.setAsk(5.0);
        bidList1.setBenchmark("benchmark");
        bidList1.setCommentary("commentary");
        bidList1.setSecurity("security");
        bidList1.setStatus("status");
        bidList1.setTrader("trader");
        bidList1.setBook("book");
        bidList1.setCreationName("creation name");
        bidList1.setCreationDate(timestamp);
        bidList1.setRevisionName("revision name");
        bidList1.setRevisionDate(timestamp);
        bidList1.setDealName("deal name");
        bidList1.setDealType("deal type");
        bidList1.setSourceListId("source list id");
        bidList1.setSide("side");

        BidList bidList2 = new BidList();
        bidList2.setBidListId(2);
        bidList2.setAccount("admin");
        bidList2.setType("admin");
        bidList2.setBidQuantity(10.0);
        bidList2.setAskQuantity(2.0);
        bidList2.setBid(3.0);
        bidList2.setAsk(5.0);
        bidList2.setBenchmark("benchmark");
        bidList2.setCommentary("commentary");
        bidList2.setSecurity("security");
        bidList2.setStatus("status");
        bidList2.setTrader("trader");
        bidList2.setBook("book");
        bidList2.setCreationName("creation name");
        bidList2.setCreationDate(timestamp);
        bidList2.setRevisionName("revision name");
        bidList2.setRevisionDate(timestamp);
        bidList2.setDealName("deal name");
        bidList2.setDealType("deal type");
        bidList2.setSourceListId("source list id");
        bidList2.setSide("side");

        List<BidList> listBids = Arrays.asList(bidList1, bidList2);

        when(bidListRepository.findAll()).thenReturn(listBids);

        // Act
        List<BidList> result = bidListService.findAllBids();

        // Assert
        assertEquals(2, result.size());
        assertEquals("user", result.get(0).getAccount());
        assertEquals("admin", result.get(1).getAccount());
    }

    @Test
    public void getBidByID() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Arrange
        BidList bidList1 = new BidList();
        bidList1.setBidListId(1);
        bidList1.setAccount("user");
        bidList1.setType("user");
        bidList1.setBidQuantity(10.0);
        bidList1.setAskQuantity(2.0);
        bidList1.setBid(3.0);
        bidList1.setAsk(5.0);
        bidList1.setBenchmark("benchmark");
        bidList1.setCommentary("commentary");
        bidList1.setSecurity("security");
        bidList1.setStatus("status");
        bidList1.setTrader("trader");
        bidList1.setBook("book");
        bidList1.setCreationName("creation name");
        bidList1.setCreationDate(timestamp);
        bidList1.setRevisionName("revision name");
        bidList1.setRevisionDate(timestamp);
        bidList1.setDealName("deal name");
        bidList1.setDealType("deal type");
        bidList1.setSourceListId("source list id");
        bidList1.setSide("side");

        when(bidListRepository.findById(bidList1.getBidListId())).thenReturn(Optional.of(bidList1));

        // Act
        BidList result = bidListService.getBidById(1);

        // Assert
        assertEquals(bidList1.getAccount(), result.getAccount());
    }

    @Test
    public void getBidByID_WhenNotFound() {
        // Arrange
        int id = 1;
        when(bidListRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bidListService.getBidById(id);
        });
        assertEquals("No Bid with this id : 1 found !", exception.getMessage());
    }
}
