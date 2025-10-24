package com.vladimirugol.database;

import com.vladimirugol.model.PointData;
import com.vladimirugol.model.ResultPointBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.vladimirugol.checkPoint.HitChecker.checkHit;
@ApplicationScoped
public class DataService {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("se.ifmo.web3");
    private final EntityManager em = emf.createEntityManager();
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Transactional
    public ResultPointBean processData(PointData req) {
        long startTime = System.nanoTime();

        boolean isHit = checkHit(req);
        String currentTime = LocalTime.now().format(DTF);
        ResultPointBean resultPoint = new ResultPointBean(
                req.getX(),
                req.getY(),
                req.getR(),
                isHit,
                currentTime,
                System.nanoTime() - startTime
        );
        em.persist(resultPoint);
        return resultPoint;
    }


    @Transactional
    public List<ResultPointBean> get() {
        return em.createQuery("SELECT d FROM ResultPointBean d", ResultPointBean.class).getResultList();
    }
}