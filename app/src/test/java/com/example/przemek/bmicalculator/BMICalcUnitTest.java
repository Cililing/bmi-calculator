package com.example.przemek.bmicalculator;

import com.example.przemek.bmicalculator.tools.BMICalc;

import org.junit.Test;

import static org.junit.Assert.*;

public class BMICalcUnitTest {

    @Test
    public void checkBMIZeroData() {
        assertFalse(BMICalc.isDataValidKilograms(0, 0));
    }

    @Test
    public void checkBMIMinusData() {
        assertFalse(BMICalc.isDataValidKilograms(-1, -2));
    }

    @Test
    public void checkBMICorrectData() {
        assertTrue(BMICalc.isDataValidKilograms(70, 2));
    }

    @Test
    public void checkBMICalcCorrectness() {
        assertEquals(22.16f, BMICalc.calculateBMIKilograms(80f, 1.9f), 0.1f);
    }
}
