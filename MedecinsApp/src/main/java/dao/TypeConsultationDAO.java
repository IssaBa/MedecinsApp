/**
 *
 * Created on 23 mai 2020 by PrinceNar
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.TypeConsultation;
import models.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Baye Lahad DIAGNE
 */
public class TypeConsultationDAO {

    private static Transaction transaction = null;
    Session session;
    private static final Logger LOGGER = Logger.getLogger(TypeConsultationDAO.class.getName());

    public List<TypeConsultation> findAll() {
        try {
            openSession();
            return session.createNamedQuery("TypeConsultation.findAll").getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public TypeConsultation findById(Long id) {
        try {
            openSession();
            return session.get(TypeConsultation.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    public boolean save(TypeConsultation p) {
        try {
            openSession();
            transaction = this.session.beginTransaction();
            session.save(p);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TypeConsultationDAO : " + e.getMessage(), e);
            transaction.rollback();
            return false;
        }
    }

    public boolean edit(TypeConsultation p) {
        try {
            openSession();
            transaction = this.session.beginTransaction();
            session.merge(p);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TypeConsultationDAO : " + e.getMessage(), e);
            transaction.rollback();
            return false;
        }
    }

    public boolean delete(TypeConsultation p) {
        try {
            openSession();
            transaction = this.session.beginTransaction();
            session.remove(p);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TypeConsultationDAO : " + e.getMessage(), e);
            transaction.rollback();
            return false;
        }
    }

    private void openSession() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    private void closeSession() {
        this.session.close();
    }
}
