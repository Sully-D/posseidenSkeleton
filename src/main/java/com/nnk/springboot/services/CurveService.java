package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing curvePoint entities.
 * This class provides methods for CRUD operations on CurvePoint entities.
 */
@Service
public class CurveService {

    @Autowired
    CurvePointRepository curvePointRepository;

    /**
     * Retrieves all CurvePoint entities.
     *
     * @return a List of all CurvePoint entities.
     */
    public List<CurvePoint> findAllCurves() {
        return curvePointRepository.findAll();
    }

    /**
     * Retrieves a BidList by its ID.
     *
     * @param curveId the ID of the CurvePoint to retrieve.
     * @return the CurvePoint entity.
     * @throws RuntimeException if no CurvePoint is found with the given ID.
     */
    public CurvePoint getCurveById(int curveId) {
        Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(curveId);
        if (optionalCurvePoint.isEmpty()) {
            throw new RuntimeException("No Curve with this id : " + curveId);
        }
        return optionalCurvePoint.get();
    }

    /**
     * Saves a new CurvePoint entity.
     *
     * @param curvePoint the CurvePoint entity to save.
     * @throws IllegalArgumentException if the account or type is invalid.
     */
    public void save(CurvePoint curvePoint) {
        Utils.intIsValide(curvePoint.getCurveId(), "Curve ID");

        curvePointRepository.save(curvePoint);
    }

    /**
     * Updates an existing CurvePoint entity.
     *
     * @param updatedCurve the CurvePoint entity with updated information.
     * @throws RuntimeException if no CurvePoint is found with the given ID.
     * @throws IllegalArgumentException if the account or type is invalid.
     */
    public void update(CurvePoint updatedCurve) {
        // Retrieve the existing CurvePoint
        getCurveById(updatedCurve.getId());

        // Save the updated CurvePoint
        save(updatedCurve);
    }

    /**
     * Deletes a CurvePoint by its ID.
     *
     * @param curveId the ID of the CurvePoint to delete.
     * @throws RuntimeException if no CurvePoint is found with the given ID.
     */
    public void delete(int curveId) {
        // Retrieve the existing CurvePoint
        CurvePoint curvePointToDelete = getCurveById(curveId);

        // Delete the CurvePoint
        curvePointRepository.delete(curvePointToDelete);
    }
}
