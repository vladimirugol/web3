package com.vladimirugol.database;

import com.vladimirugol.checkPoint.HitChecker;
import com.vladimirugol.model.PointData;
import com.vladimirugol.model.ResultPointEntity;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ManagedBean(name = "dataService", eager = true)
@ApplicationScoped
public class DataService {

    @PersistenceContext(unitName = "se.ifmo.web3")
    private EntityManager em;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ResultPointEntity processData(PointData req) {
        UserTransaction utx = null;
        try {
            utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            utx.begin();

            long startTime = System.nanoTime();
            boolean isHit = HitChecker.checkHit(req);
            String currentTime = LocalTime.now().format(DTF);
            ResultPointEntity resultPoint = new ResultPointEntity(
                    req.getX(),
                    req.getY(),
                    req.getR(),
                    isHit,
                    currentTime,
                    System.nanoTime() - startTime
            );
            em.persist(resultPoint);

            utx.commit();
            return resultPoint;

        } catch (Exception e) {
            if (utx != null) {
                try {
                    utx.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new RuntimeException("Transaction failed", e);
        }
    }

    public List<ResultPointEntity> get() {
        return em.createQuery("SELECT d FROM ResultPointEntity d", ResultPointEntity.class).getResultList();
    }
}