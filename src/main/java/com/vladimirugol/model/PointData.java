package com.vladimirugol.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PointData implements Serializable {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    public PointData() {
    }
    public PointData(BigDecimal x, BigDecimal y, BigDecimal r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public boolean allFieldsPresent() {
        return x != null && y != null && r != null;
    }
}
