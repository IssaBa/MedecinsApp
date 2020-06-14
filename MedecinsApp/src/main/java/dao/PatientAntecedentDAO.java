/**
 *
 * Created on 23 mai 2020 by PrinceNar
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.ClasseAntecedent;
import models.PatientAntecedent;
import models.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Baye Lahad DIAGNE
 */
public class PatientAntecedentDAO {

    private static Transaction transaction = null;
    Session session;
    private static final Logger LOGGER = Logger.getLogger(PatientAntecedentDAO.class.getName());

    public List<PatientAntecedent> findAll() {
        try {
            openSession();
            return session.createNamedQuery("PatientAntecedent.findAll").getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
    public Long newID() {
        openSession();
        Long id = (Long) session.createQuery("SELECT MAX(pa.id) FROM PatientAntecedent pa").getSingleResult();
        closeSession();
        return id+1;
    }

    public PatientAntecedent findById(Long id) {
        try {
            openSession();
            return session.get(PatientAntecedent.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    public boolean save(PatientAntecedent p) {
        try {
            openSession();
            transaction = this.session.beginTransaction();
            session.save(p);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "PatientAntecedentDAO : " + e.getMessage(), e);
            transaction.rollback();
            return false;
        }
    }

    public boolean edit(PatientAntecedent p) {
        try {
            openSession();
            transaction = this.session.beginTransaction();
            session.merge(p);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "PatientAntecedentDAO : " + e.getMessage(), e);
            transaction.rollback();
            return false;
        }
    }

    public boolean delete(PatientAntecedent p) {
        try {
            openSession();
            transaction = this.session.beginTransaction();
            session.remove(p);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "PatientAntecedentDAO : " + e.getMessage(), e);
            transaction.rollback();
            return false;
        }
    }

    public List<PatientAntecedent> findByPatient(Integer idPatient) {
        try {
            openSession();
            return session.createNamedQuery("PatientAntecedent.findByPatient")
                    .setParameter("idPatient", idPatient)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    public PatientAntecedent findByPatientAndAntecedent(Integer idPatient, Long idAntecedent) {
        try {
            openSession();
            ArrayList<PatientAntecedent> patientAntecedents = (ArrayList<PatientAntecedent>) session.createNamedQuery("PatientAntecedent.findByPatientAndAntecedent")
                    .setParameter("idPatient", idPatient)
                    .setParameter("idAntecedent", idAntecedent)
                    .list();
            if(patientAntecedents.size() > 0) {
                return patientAntecedents.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    public List<ClasseAntecedent> getClassesFrom(List<PatientAntecedent> pas) {
        List<ClasseAntecedent> lca = new ArrayList<>();
        for (PatientAntecedent pa : pas) {
            if (!lca.contains(pa.getAntecedent().getClasse())) {
                lca.add(pa.getAntecedent().getClasse());
            }
        }
        return lca;
    }

    private void openSession() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    private void closeSession() {
        this.session.close();
    }
}
