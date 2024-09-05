package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Retrieves all CurvePoint entries.
     *
     * @return a list of all CurvePoint objects present in the database.
     */
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    /**
     * Retrieves a CurvePoint object by its ID.
     *
     * @param id the ID of the CurvePoint object to retrieve.
     * @return the CurvePoint object corresponding to the provided ID.
     * @throws IllegalArgumentException if the provided ID does not match any object in the database.
     */
    public CurvePoint findById(int id) {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }

    /**
     * Creates a new CurvePoint object and saves it to the database.
     *
     * @param curvePoint the CurvePoint object to create.
     * @return the created and saved CurvePoint object.
     */
    public CurvePoint createCurvePoint(CurvePoint curvePoint) {
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Updates an existing CurvePoint object with the provided new values.
     *
     * @param curvePoint the CurvePoint object containing the new values.
     * @param id the ID of the CurvePoint object to update.
     * @return the updated and saved CurvePoint object.
     * @throws IllegalArgumentException if the provided ID does not match any object in the database.
     */
    public CurvePoint updateCurvePoint(CurvePoint curvePoint, int id) {
        CurvePoint updatedCurvePoint = findById(id);
        updatedCurvePoint.setTerm(curvePoint.getTerm());
        updatedCurvePoint.setValue(curvePoint.getValue());
        return curvePointRepository.save(updatedCurvePoint);
    }

    /**
     * Deletes a CurvePoint object by its ID.
     *
     * @param id the ID of the CurvePoint object to delete.
     */
    public void deleteById(int id) {
        curvePointRepository.deleteById(id);
    }
}

