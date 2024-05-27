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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    public void addCurve_Successful() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Arrange
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(timestamp);
        curvePoint.setTerm(1.0);
        curvePoint.setValue(2.0);
        curvePoint.setCreationDate(timestamp);

        // Act
        curveService.save(curvePoint);

        // Assert
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    public void getById() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Arrange
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(timestamp);
        curvePoint.setTerm(1.0);
        curvePoint.setValue(2.0);
        curvePoint.setCreationDate(timestamp);

        int curveId = curvePoint.getId();

        when(curvePointRepository.findById(curveId)).thenReturn(Optional.of(curvePoint));
        // Act
        CurvePoint result = curveService.getCurveById(curveId);

        // Assert
        assertEquals(curvePoint.getCurveId(), result.getCurveId());
    }

    @Test
    public void getCurveByID_WhenNotFound() {
        // Arrange
        int id = 1;
        when(curvePointRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            curveService.getCurveById(id);
        });
        assertEquals("No Curve with this id : 1", exception.getMessage());
    }

    @Test
    public void updateCurve() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Arrange
        CurvePoint updatedCurve = new CurvePoint();
        updatedCurve.setId(1);
        updatedCurve.setCurveId(1);
        updatedCurve.setAsOfDate(timestamp);
        updatedCurve.setTerm(10.0);
        updatedCurve.setValue(2.0);
        updatedCurve.setCreationDate(timestamp);

        int curveId = updatedCurve.getId();

        when(curvePointRepository.findById(curveId)).thenReturn(Optional.of(updatedCurve));

        // Act
        curveService.update(updatedCurve);

        // Assert
        verify(curvePointRepository).save(updatedCurve);
        assertEquals(10.0, updatedCurve.getTerm());
    }

    @Test
    public void curveUpdate_WhenNotFound() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Arrange
        CurvePoint updatedCurve = new CurvePoint();
        updatedCurve.setId(1);
        updatedCurve.setCurveId(1);
        updatedCurve.setAsOfDate(timestamp);
        updatedCurve.setTerm(10.0);
        updatedCurve.setValue(2.0);
        updatedCurve.setCreationDate(timestamp);

        int nonExistentId = 2;
        updatedCurve.setId(nonExistentId);
        when(curvePointRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            curveService.update(updatedCurve);
        });
        assertEquals("No Curve with this id : " + nonExistentId, exception.getMessage());
    }

    @Test
    public void deleteBidList() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Arrange
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(timestamp);
        curvePoint.setTerm(1.0);
        curvePoint.setValue(2.0);
        curvePoint.setCreationDate(timestamp);

        int curveId = curvePoint.getId();

        when(curvePointRepository.findById(curveId)).thenReturn(Optional.of(curvePoint));

        // Act
        curveService.delete(curveId);

        // Assert
        verify(curvePointRepository).delete(curvePoint);
    }

    @Test
    public void deleteCurve_WhenIdNoFound() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Arrange
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(timestamp);
        curvePoint.setTerm(1.0);
        curvePoint.setValue(2.0);
        curvePoint.setCreationDate(timestamp);

        int nonExistentId = 2;
        when(curvePointRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            curveService.delete(nonExistentId);
        });
        assertEquals("No Curve with this id : " + nonExistentId, exception.getMessage());
    }
}
