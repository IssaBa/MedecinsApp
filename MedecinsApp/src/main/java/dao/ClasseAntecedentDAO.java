package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;
import models.ClasseAntecedent;
import models.config.HibernateUtil;

public class ClasseAntecedentDAO {

    private static Transaction transaction = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    private static final Logger LOGGER = Logger.getLogger(ClasseAntecedentDAO.class.getName());

    /**
     * Ajouter ClasseAntecedent
     * @param classeAntecedent
     * @return 
     */
    public boolean save(ClasseAntecedent classeAntecedent) {

        try {
            openSession();
            transaction = session.beginTransaction();
            session.save(classeAntecedent);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

    }

    /**
     * 
     * @param libelle
     * @return ClasseAntecedent via son libelle
     */
    public ClasseAntecedent findByLibelle(String libelle) {
        try {
            openSession();
            List<ClasseAntecedent> classeAntecedent = session.createQuery("FROM  ClasseAntecedent cl  WHERE cl.libelle=:lib").setParameter("lib", libelle).list();
            if (classeAntecedent.size() > 0) {
                return classeAntecedent.get(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    /**
     *
     * @return la liste de tous les ClasseAntecedent
     */
    public List<ClasseAntecedent> findAll() {
        try {
            openSession();
            return session.createQuery("FROM  ClasseAntecedent clAnt").list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    // Modifier ClasseAntecedent
    public boolean update(ClasseAntecedent classeAntecedent) {
        try {
            openSession();
            transaction = session.beginTransaction();
            session.merge(classeAntecedent);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

    }

    // Delete ClasseAntecedent
    public boolean delete(ClasseAntecedent classeAntecedent) {
        try {
            openSession();
            transaction = session.beginTransaction();
            session.remove(classeAntecedent);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

    }

    public ClasseAntecedent findById(Long id) {
        try {
            openSession();
            return session.get(ClasseAntecedent.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    private void openSession() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    private void closeSession() {
        this.session.close();
    }

}
