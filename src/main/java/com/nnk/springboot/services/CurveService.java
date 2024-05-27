package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurveService {

    @Autowired
    CurvePointRepository curvePointRepository;
    public List<CurvePoint> findAllCurves() {
        return curvePointRepository.findAll();
    }

    public CurvePoint getCurveById(int curveId) {
        Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(curveId);
        if (optionalCurvePoint.isEmpty()) {
            throw new RuntimeException("No Curve with this id : " + curveId);
        }
        return optionalCurvePoint.get();
    }

    public void save(CurvePoint curvePoint) {
        Utils.intIsValide(curvePoint.getCurveId(), "Curve ID");

        curvePointRepository.save(curvePoint);
    }

    public void update(CurvePoint updatedCurve) {
        // Retrieve the existing BidList
        getCurveById(updatedCurve.getId());

        // Save the updated BidList
        save(updatedCurve);
    }

    public void delete(int curveId) {
        // Retrieve the existing BidList
        CurvePoint curvePointToDelete = getCurveById(curveId);

        // Delete the BidList
        curvePointRepository.delete(curvePointToDelete);
    }
}
