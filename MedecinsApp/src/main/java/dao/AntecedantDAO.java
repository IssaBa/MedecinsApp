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

public class AntecedantDAO {

    private static Transaction transaction = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    private static final Logger LOGGER = Logger.getLogger(AntecedantDAO.class.getName());

    public Antecedent findById(Long id) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(Antecedent.class, id);
        } catch (HibernateException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    // Ajouter ClasseAntecedent
    public boolean saveAntecedant(Antecedent antecedent) {

        try {
            transaction = session.beginTransaction();
            session.save(antecedent);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }

    //Search Antecedent via un objet
    public Antecedent searchAntecedant(Antecedent antecedent) {

        Antecedent classeAntecedent2 = null;
        try {
            classeAntecedent2 = session.createQuery("from Antecedent ant", Antecedent.class).list().get(0);
            session.flush();
        } catch (HibernateException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return classeAntecedent2;
    }

    public ArrayList<Antecedent> findByClasseName(String libelle) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return (ArrayList<Antecedent>) session
                    .createNamedQuery("Antecedent.findByClasseName")
                    .setParameter("libelle", libelle)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    // Search Antecedent via son libelle
    public Antecedent findAntecedant(String libelle) {

        List<Antecedent> antecedent;
        try {
            antecedent = session.createQuery("FROM  Antecedent ant  WHERE ant.libelle=:lib").setParameter("lib", libelle).list();
            // session.flush();
            if (antecedent.size() > 0) {
                return antecedent.get(0);
            } else {
                return null;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    // Delete Antecedent via son libelle
    public void deleteAntecedent(String libelle) {
        try {
            Antecedent antecedent = findAntecedant(libelle);
            session.delete(antecedent);
            session.flush();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    // la liste de tous les Antecedent
    public ArrayList<Antecedent> getAllAntecedent() {
        ArrayList<Antecedent> lisAntecedent = new ArrayList<>();
        try {
            lisAntecedent = (ArrayList<Antecedent>) session.createQuery("FROM  Antecedent clAnt ").list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return lisAntecedent;
    }

    // Modifier Antecedent
    public boolean updateAntecedent(Antecedent antecedent) {
        //session.flush();
        try {
            transaction = session.beginTransaction();
            session.update(antecedent);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

    }

    // Delete Antecedent
    public boolean deleteAntecedent(Antecedent antecedent) {
        try {
            transaction = session.beginTransaction();
            session.delete(antecedent);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

    }

}
