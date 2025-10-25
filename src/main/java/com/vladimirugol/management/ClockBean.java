package com.vladimirugol.management;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ManagedBean(name = "clockBean", eager = true)
@ApplicationScoped
public class ClockBean {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String getCurrentTime() {
        return LocalDateTime.now().format(FORMATTER);
    }
}
