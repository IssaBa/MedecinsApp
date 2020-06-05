/**
 *
 * Created on 23 mai 2020 by PrinceNar
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Patient;
import models.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Baye Lahad DIAGNE
 */
public class PatientDAO {

    private static Transaction transaction = null;
    Session session;
    private static final Logger LOGGER = Logger.getLogger(PatientDAO.class.getName());

    public List<Patient> findAll() {
        try {
            openSession();
            return session.createNamedQuery("Patient.findAll").getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public Patient findById(Integer id) {
        try {
            openSession();
            return session.get(Patient.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    public boolean save(Patient p) {
        try {
            openSession();
            transaction = this.session.beginTransaction();
            session.save(p);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "PatientDAO : " + e.getMessage(), e);
            transaction.rollback();
            return false;
        }
    }

    public boolean edit(Patient p) {
        try {
            openSession();
            transaction = this.session.beginTransaction();
            session.merge(p);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "PatientDAO : " + e.getMessage(), e);
            transaction.rollback();
            return false;
        }
    }

    public boolean delete(Patient p) {
        try {
            openSession();
            transaction = this.session.beginTransaction();
            session.remove(p);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "PatientDAO : " + e.getMessage(), e);
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
