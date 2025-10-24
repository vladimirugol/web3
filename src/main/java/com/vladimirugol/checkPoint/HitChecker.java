package com.vladimirugol.checkPoint;

import com.vladimirugol.model.PointData;
import java.math.BigDecimal;

public class HitChecker {
    public static boolean checkHit(PointData pointData) {
        if (pointData == null || !pointData.allFieldsPresent()) {
            return false;
        }
        return checkHit(pointData.getX(), pointData.getY(), pointData.getR());
    }

    public static boolean checkHit(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (r.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        return hitRectangle(x, y, r) || hitCircle(x, y, r) || hitTriangle(x, y, r);
    }

    private static boolean hitRectangle(BigDecimal x, BigDecimal y, BigDecimal r) {
        BigDecimal rHalf = r.divide(new BigDecimal("2"));
        return x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) >= 0 &&
                x.compareTo(rHalf.negate()) >= 0 && y.compareTo(r) <= 0;
    }

    private static boolean hitCircle(BigDecimal x, BigDecimal y, BigDecimal r) {
        BigDecimal rHalf = r.divide(new BigDecimal("2"));
        return x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) <= 0 &&
                x.pow(2).add(y.pow(2)).compareTo(rHalf.pow(2)) <= 0;
    }

    private static boolean hitTriangle(BigDecimal x, BigDecimal y, BigDecimal r) {
        BigDecimal rHalf = r.divide(new BigDecimal("2"));
        return x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) >= 0 &&
                x.add(y).compareTo(rHalf) <= 0;
    }
}