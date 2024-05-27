package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurveService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurveServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurveService curveService;

    @Test
    public void findAllCurve() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Arrange
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(timestamp);
        curvePoint.setTerm(1.0);
        curvePoint.setValue(2.0);
        curvePoint.setCreationDate(timestamp);

        List<CurvePoint> listCurves = Arrays.asList(curvePoint);

        when(curvePointRepository.findAll()).thenReturn(listCurves);

        // Act
        List<CurvePoint> result = curveService.findAllCurves();

        // Assert
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getCurveId());
        assertEquals(2.0, result.get(0).getValue());
    }
}
