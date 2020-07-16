package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Antecedent;
import models.config.HibernateUtil;
import org.hibernate.HibernateException;

public class AntecedentDAO {

    private static Transaction transaction = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    private static final Logger LOGGER = Logger.getLogger(AntecedentDAO.class.getName());

    public Antecedent findById(Long id) {
        try {
            openSession();
            return session.get(Antecedent.class, id);
        } catch (HibernateException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    // Ajouter ClasseAntecedent
    public boolean save(Antecedent antecedent) {

        try {
            openSession();
            transaction = session.beginTransaction();
            session.save(antecedent);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }

    public List<Antecedent> findByClasseName(String libelle) {
        try {
            openSession();
            return (ArrayList<Antecedent>) session
                    .createNamedQuery("Antecedent.findByClasseName")
                    .setParameter("libelle", libelle)
                    .getResultList();
        } catch (HibernateException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    // Search Antecedent via son libelle
    public Antecedent findByLibelle(String libelle) {
        try {
            openSession();
            List<Antecedent> antecedents = session.createQuery("FROM  Antecedent ant  WHERE ant.libelle=:lib").setParameter("lib", libelle).list();
            if (antecedents.size() > 0) {
                return antecedents.get(0);
            } 
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    // la liste de tous les Antecedent
    public ArrayList<Antecedent> findAll() {
        ArrayList<Antecedent> lisAntecedent = new ArrayList<>();
        try {
            openSession();
            lisAntecedent = (ArrayList<Antecedent>) session.createQuery("FROM  Antecedent clAnt").list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return lisAntecedent;
    }

    // Modifier Antecedent
    public boolean update(Antecedent antecedent) {
        try {
            openSession();
            transaction = session.beginTransaction();
            session.merge(antecedent);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

    }

    // Delete Antecedent
    public boolean delete(Antecedent antecedent) {
        try {
            openSession();
            transaction = session.beginTransaction();
            session.remove(antecedent);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
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
