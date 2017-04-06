package com.example.przemek.bmicalculator.tools;

import com.example.przemek.bmicalculator.R;

public class BMICalc {

    public static float maxHeight = 2.5f;
    public static float minHeight = 0.5f;
    public static float minMass = 10;
    public static float maxMass = 250;


    /**
     *
     * @param mass mass in kg
     * @param height height in m
     * @return mass and height is valid
     */
    public static boolean isDataValidKilograms(float mass, float height) {
        return (mass > minMass && mass < maxMass &&
                height > minHeight && height < maxHeight);
    }

    /**
     *
     * @param mass mass in pounds
     * @param height height in inches
     * @return mass and height is valid
     */
    public static boolean isDataValidPounds(float mass, float height) {
        float _mass = mass * 0.45f; //now _mass is in kg
        float _height = height * 0.0254f; //now height is in m

        return isDataValidKilograms(_mass, _height);
    }

    public static float calculateBMIKilograms(float mass, float height) {
        return isDataValidKilograms(mass, height) ? mass/(height*height) : -1;
    }

    public static float calculateBMIPounds(float mass, float height) {
        return isDataValidPounds(mass, height) ? (mass/(height*height)) * 703 : -1;
    }

    public static int getColorRepresentation(float bmi) {

        int _colorValue;
        if (bmi < 18.5) {
            _colorValue = R.color.badBMI;
        }
        else if (bmi < 20) {
            _colorValue = R.color.averageBMI;
        }
        else if (bmi < 24) {
            _colorValue = R.color.goodBMI;
        }
        else if (bmi < 28) {
            _colorValue = R.color.averageBMI;
        }
        else {
            _colorValue = R.color.badBMI;
        }

        return _colorValue; //_colorValue;
    }

    public static void main(String... args) {

    }
}
