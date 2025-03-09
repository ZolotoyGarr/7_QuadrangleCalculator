package org.example.validator.impl;

import java.util.List;

public class PointValidator {
    public boolean validate(List<String> stringPoint) {
        if (stringPoint.size() != 2) { return false; }
        for (String number : stringPoint) {
            try {
                Double.parseDouble(number);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
