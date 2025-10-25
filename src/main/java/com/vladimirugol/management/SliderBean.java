package com.vladimirugol.management;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import lombok.Data;

import java.io.Serializable;
@ManagedBean(name = "sliderBean", eager = true)
@Data
@SessionScoped
public class SliderBean implements Serializable {
    private double x = 1.0;
}
