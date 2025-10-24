package com.vladimirugol.management;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;

@Data
@Named
@ViewScoped
public class SliderBean implements Serializable {
    private double x = 1.0;
}
