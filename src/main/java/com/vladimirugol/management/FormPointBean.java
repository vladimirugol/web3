package com.vladimirugol.management;

import javax.faces.bean.ManagedBean;
import javax.enterprise.context.RequestScoped;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@ManagedBean(name = "formPointBean", eager = true)
@RequestScoped
@Data
public class FormPointBean implements Serializable {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r = BigDecimal.valueOf(1.0);
}
