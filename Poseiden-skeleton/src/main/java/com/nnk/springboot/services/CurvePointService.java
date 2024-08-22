package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    public CurvePoint findById(int id) { return curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id)); }

    public CurvePoint createCurvePoint(CurvePoint curvePoint) {
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
        return curvePointRepository.save(curvePoint);
    }

    public CurvePoint updateCurvePoint(CurvePoint curvePoint, int id) {
        CurvePoint updatedCurvePoint = findById(id);
        updatedCurvePoint.setTerm(curvePoint.getTerm());
        updatedCurvePoint.setValue(curvePoint.getValue());
        return curvePointRepository.save(updatedCurvePoint);
    }

    public void deleteById(int id) {
        curvePointRepository.deleteById(id);
    }
}
