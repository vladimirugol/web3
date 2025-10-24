package com.vladimirugol.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
@Entity
@Table(name="result")
@NoArgsConstructor
@Data
public class ResultPointBean implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private boolean hit;
    private String currentTime;
    private long execMs;

    public ResultPointBean(BigDecimal x, BigDecimal y, BigDecimal r, boolean hit, String currentTime, Long execMs) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.currentTime = currentTime;
        this.execMs = execMs;
    }
}
