package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(CurvePointService.class)
public class CurvePointServiceTest {

    @Autowired
    private CurvePointService curvePointService;

    @MockBean
    private CurvePointRepository curvePointRepository;

    @Test
    public void testFindAll() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setTerm(10.0);
        curvePoint.setValue(100.0);
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
        List<CurvePoint> curvePoints = Arrays.asList(curvePoint);

        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        List<CurvePoint> result = curvePointService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void testFindById() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setTerm(10.0);
        curvePoint.setValue(100.0);
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePoint result = curvePointService.findById(1);

        assertNotNull(result);
        assertEquals(10.0, result.getTerm());
        assertEquals(100.0, result.getValue());
    }

    @Test
    public void testFindById_NotFound() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> curvePointService.findById(1));
    }

    @Test
    public void testCreateCurvePoint() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setTerm(10.0);
        curvePoint.setValue(100.0);
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));

        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint result = curvePointService.createCurvePoint(curvePoint);

        assertEquals(curvePoint.getTerm(), result.getTerm());
        assertEquals(curvePoint.getValue(), result.getValue());
        assertEquals(curvePoint.getCreationDate(), result.getCreationDate());
    }

    @Test
    public void testUpdateCurvePoint() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setTerm(10.0);
        curvePoint.setValue(100.0);
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint updatedCurvePoint = new CurvePoint();
        updatedCurvePoint.setTerm(20.0);
        updatedCurvePoint.setValue(200.0);

        CurvePoint result = curvePointService.updateCurvePoint(updatedCurvePoint, 1);

        assertEquals(20.0, result.getTerm());
        assertEquals(200.0, result.getValue());
    }

    @Test
    public void testDeleteById() {
        curvePointService.deleteById(1);

        verify(curvePointRepository, times(1)).deleteById(1);
    }

}
