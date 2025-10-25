package com.vladimirugol.management;

import com.vladimirugol.model.PointData;
import com.vladimirugol.model.ResultPointEntity;
import com.vladimirugol.database.DataService;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "resultManagerBean", eager = true)
@SessionScoped
public class ResultsManagerBean implements Serializable {

    @ManagedProperty(value = "#{dataService}")
    private DataService dataService;

    @ManagedProperty(value = "#{formPointBean}")
    private FormPointBean formPointBean;

    private List<ResultPointEntity> results;

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
        ResultPointEntity newResult = dataService.processData(pointData);
        results.add(0, newResult);
    }

    public List<ResultPointEntity> getResults() {
        return results;
    }

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    public DataService getDataService() {
        return dataService;
    }

    public FormPointBean getFormPointBean() {
        return formPointBean;
    }

    public void setFormPointBean(FormPointBean formPointBean) {
        this.formPointBean = formPointBean;
    }

    public void setResults(List<ResultPointEntity> results) {
        this.results = results;
    }
}