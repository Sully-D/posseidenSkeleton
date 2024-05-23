package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListService {

    @Autowired
    BidListRepository bidListRepository;

    public List<BidList> findAllBids() {
        return bidListRepository.findAll();
    }

    public void save(BidList bidList) {

        Utils.stringIsValide(bidList.getAccount(), "Account");
        Utils.stringIsValide(bidList.getType(), "Type");

        bidListRepository.save(bidList);
    }
}
