package com.vladimirugol.management;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Named
@RequestScoped
@Data
public class FormPointBean implements Serializable {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r = BigDecimal.valueOf(1.0);
}
