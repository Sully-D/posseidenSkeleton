package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    @Autowired
    BidListRepository bidListRepository;

    public List<BidList> findAllBids() {
        return bidListRepository.findAll();
    }

    public void save(@NotNull BidList bidList) {

        Utils.stringIsValide(bidList.getAccount(), "Account");
        Utils.stringIsValide(bidList.getType(), "Type");

        bidListRepository.save(bidList);
    }

    public BidList getBidById(int id) {
        Optional<BidList> optionalBidList = bidListRepository.findById(id);
        if (optionalBidList.isEmpty()) {
            throw new RuntimeException("No Bid with this id : " + id + " found !");
        }
        return optionalBidList.get();
    }

    public void updateBid(@NotNull BidList updateBid) {
        BidList oldBid = getBidById(updateBid.getBidListId());

        if(!oldBid.getBidListId().equals(updateBid.getBidListId())) {
            throw new RuntimeException("No Bid with this id : " + updateBid.getBidListId() + " found !");
        }

        save(updateBid);
    }

    public void delete(@NotNull int bidListId) {
        BidList bidListToDelete = getBidById(bidListId);

        bidListRepository.delete(bidListToDelete);
    }
}
