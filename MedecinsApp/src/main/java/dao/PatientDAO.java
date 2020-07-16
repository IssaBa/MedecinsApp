/**
 *
 * Created on 23 mai 2020 by PrinceNar
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Consultation;
import models.Ordonnance;
import models.Patient;
import models.config.HibernateUtil;
import org.hibernate.Hibernate;
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
            List<Patient> patients = session.createNamedQuery("Patient.findAll").getResultList();
            closeSession();
            return patients;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public Patient findById(Long id) {
        try {
            openSession();
            Patient p = session.get(Patient.class, id);
            Hibernate.initialize(p.getProfession());
            Hibernate.initialize(p.getProfessionConjoint());
            Hibernate.initialize(p.getPatientAntecedentListe());
            Hibernate.initialize(p.getConsultationListe());
            Hibernate.initialize(p.getOrdonnanceListe());
            closeSession();
            return p;
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

    public boolean editConsultation(Patient p, Consultation c) {
        try {
            for (int i = 0; i < p.getConsultationListe().size(); i++) {
                if (p.getConsultationListe().get(i).getId().equals(c.getId())) {
                    p.getConsultationListe().set(i, c);
                }
            }
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

    public boolean deleteConsultation(Patient p, Consultation c) {
        try {
            for (int i = 0; i < p.getConsultationListe().size(); i++) {
                if (p.getConsultationListe().get(i).getId().equals(c.getId())) {
                    p.getConsultationListe().remove(i);
                }
            }
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

    public boolean editOrdonnance(Patient p, Ordonnance o) {
        try {
            for (int i = 0; i < p.getOrdonnanceListe().size(); i++) {
                if (p.getOrdonnanceListe().get(i).getId().equals(o.getId())) {
                    p.getOrdonnanceListe().set(i, o);
                }
            }
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

    public boolean deleteOrdonnance(Patient p, Ordonnance c) {
        try {
            for (int i = 0; i < p.getOrdonnanceListe().size(); i++) {
                if (p.getOrdonnanceListe().get(i).getId().equals(c.getId())) {
                    p.getOrdonnanceListe().remove(i);
                }
            }
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

    public void refresh(Patient p) {
        try {
            openSession();
            session.refresh(p);
            closeSession();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "PatientDAO : " + e.getMessage(), e);
        }
    }

    private void openSession() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    private void closeSession() {
        this.session.close();
    }
}
