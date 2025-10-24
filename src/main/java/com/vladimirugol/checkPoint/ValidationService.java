package com.vladimirugol.checkPoint;


import com.vladimirugol.model.PointData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidationService {
    private static final BigDecimal X_MIN = new BigDecimal("-5");
    private static final BigDecimal X_MAX = new BigDecimal("5");

    private static final List<BigDecimal> VALID_R_VALUES = Arrays.asList(
            new BigDecimal("1"), new BigDecimal("1.5"), new BigDecimal("2"),
            new BigDecimal("2.5"), new BigDecimal("3")
    );

    private static final BigDecimal Y_MIN = new BigDecimal("-5");
    private static final BigDecimal Y_MAX = new BigDecimal("5");

    public List<String> validate(PointData point) {
        List<String> errors = new ArrayList<>();
        if (!point.allFieldsPresent()) {
            errors.add("Error: All fields (x, y, r) must be present.");
            return errors;
        }
        BigDecimal x = point.getX();
        BigDecimal y = point.getY();
        BigDecimal r = point.getR();
        if (x.compareTo(X_MIN) < 0 || x.compareTo(X_MAX) > 0) {
            errors.add("Error: X must be in the range [-4, 4].");
        }
        if (y.compareTo(Y_MIN) <= 0 || y.compareTo(Y_MAX) >= 0) {
            errors.add("Error: Y must be from -3 to 3 (exclusive).");
        }
        if (!VALID_R_VALUES.contains(r)) {
            errors.add("Error: R-value is not valid. Please select one of the checkboxes.");
        }
        return errors;
    }
}