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
import static org.mockito.Mockito.verify;
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

    @Test
    public void updateBid() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Arrange
        BidList oldBid = new BidList();
        oldBid.setBidListId(1);
        oldBid.setAccount("user");
        oldBid.setType("user");
        oldBid.setBidQuantity(10.0);
        oldBid.setAskQuantity(2.0);
        oldBid.setBid(3.0);
        oldBid.setAsk(5.0);
        oldBid.setBenchmark("benchmark");
        oldBid.setCommentary("commentary");
        oldBid.setSecurity("security");
        oldBid.setStatus("status");
        oldBid.setTrader("trader");
        oldBid.setBook("book");
        oldBid.setCreationName("creation name");
        oldBid.setCreationDate(timestamp);
        oldBid.setRevisionName("revision name");
        oldBid.setRevisionDate(timestamp);
        oldBid.setDealName("deal name");
        oldBid.setDealType("deal type");
        oldBid.setSourceListId("source list id");
        oldBid.setSide("side");

        BidList updateBid = new BidList();
        updateBid.setBidListId(1);
        updateBid.setAccount("updatedAccount");
        updateBid.setType("updatedType");
        updateBid.setBidQuantity(150.0);
        updateBid.setAskQuantity(20.0);
        updateBid.setBid(1.0);
        updateBid.setAsk(8.0);
        updateBid.setBenchmark("benchmark");
        updateBid.setCommentary("commentary");
        updateBid.setSecurity("security");
        updateBid.setStatus("status");
        updateBid.setTrader("trader");
        updateBid.setBook("book");
        updateBid.setCreationName("creation name");
        updateBid.setCreationDate(timestamp);
        updateBid.setRevisionName("revision name");
        updateBid.setRevisionDate(timestamp);
        updateBid.setDealName("deal name");
        updateBid.setDealType("deal type");
        updateBid.setSourceListId("source list id");
        updateBid.setSide("side");

        when(bidListRepository.findById(oldBid.getBidListId())).thenReturn(Optional.of(oldBid));

        // Act
        bidListService.updateBid(updateBid);

        // Assert
        verify(bidListRepository).save(updateBid);
        assertEquals("updatedAccount", updateBid.getAccount());
        assertEquals("updatedType", updateBid.getType());
        assertEquals(150.0, updateBid.getBidQuantity());
    }

    @Test
    public void testUpdateBidNotFound() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Arrange
        BidList oldBid = new BidList();
        oldBid.setBidListId(1);
        oldBid.setAccount("user");
        oldBid.setType("user");
        oldBid.setBidQuantity(10.0);
        oldBid.setAskQuantity(2.0);
        oldBid.setBid(3.0);
        oldBid.setAsk(5.0);
        oldBid.setBenchmark("benchmark");
        oldBid.setCommentary("commentary");
        oldBid.setSecurity("security");
        oldBid.setStatus("status");
        oldBid.setTrader("trader");
        oldBid.setBook("book");
        oldBid.setCreationName("creation name");
        oldBid.setCreationDate(timestamp);
        oldBid.setRevisionName("revision name");
        oldBid.setRevisionDate(timestamp);
        oldBid.setDealName("deal name");
        oldBid.setDealType("deal type");
        oldBid.setSourceListId("source list id");
        oldBid.setSide("side");

        BidList updateBid = new BidList();
        updateBid.setBidListId(1);
        updateBid.setAccount("updatedAccount");
        updateBid.setType("updatedType");
        updateBid.setBidQuantity(150.0);
        updateBid.setAskQuantity(20.0);
        updateBid.setBid(1.0);
        updateBid.setAsk(8.0);
        updateBid.setBenchmark("benchmark");
        updateBid.setCommentary("commentary");
        updateBid.setSecurity("security");
        updateBid.setStatus("status");
        updateBid.setTrader("trader");
        updateBid.setBook("book");
        updateBid.setCreationName("creation name");
        updateBid.setCreationDate(timestamp);
        updateBid.setRevisionName("revision name");
        updateBid.setRevisionDate(timestamp);
        updateBid.setDealName("deal name");
        updateBid.setDealType("deal type");
        updateBid.setSourceListId("source list id");
        updateBid.setSide("side");

        int nonExistentId = 2;
        updateBid.setBidListId(nonExistentId);
        when(bidListRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bidListService.updateBid(updateBid);
        });
        assertEquals("No Bid with this id : " + nonExistentId + " found !", exception.getMessage());
    }

    @Test
    public void deleteBidList() {
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
        bidListService.delete(bidList1.getBidListId());

        // Assert
        verify(bidListRepository).delete(bidList1);
    }

    @Test
    public void deleteBidList_WhenIdNoFound() {

        // Arrange
        BidList bidList1 = new BidList();
        bidList1.setBidListId(1);
        bidList1.setAccount("user");
        bidList1.setType("user");

        int nonExistentId = 2;
        when(bidListRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bidListService.delete(nonExistentId);
        });
        assertEquals("No Bid with this id : " + nonExistentId + " found !", exception.getMessage());
    }
}
