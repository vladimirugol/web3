package com.vladimirugol.management;

import com.vladimirugol.model.PointData;
import com.vladimirugol.model.ResultPointBean;
import com.vladimirugol.database.DataService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Named
@SessionScoped
public class ResultsManagerBean implements Serializable {
    @Inject
    DataService dataService;

    @Inject
    FormPointBean formPointBean;

    @Inject
    SliderBean sliderBean;
    private List<ResultPointBean> results;

    @PostConstruct
    public void init() {
        results = new ArrayList<>(dataService.get());
    }

    public void addNewResult() {
        PointData pointData = new PointData(
                formPointBean.getX(),
                formPointBean.getY(),
                formPointBean.getR()
        );
        ResultPointBean newResult = dataService.processData(pointData);
        results.add(0, newResult);
    }
    public List<ResultPointBean> getResults() {
        return results;
    }
}
