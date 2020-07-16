package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Profession;
import models.config.HibernateUtil;

public class ProfessionDAO {

    private static Transaction transaction;
    Session session;
    private static final Logger LOGGER = Logger.getLogger(ProfessionDAO.class.getName());

    // Ajouter une profession
    public boolean save(Profession profession) {
        try {
            openSession();
            transaction = session.beginTransaction();
            session.save(profession);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

    }

    // Search profession via son libelle
    public Profession findByLibelle(String libelle) {
        try {
            openSession();
            List pros = session.createQuery("FROM Profession p  WHERE p.libelle=:lib").setParameter("lib", libelle).getResultList();
            return pros.size() > 0 ? (Profession) pros.get(0) : null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }

    }

    // la liste de tous les professions
    public List<Profession> findAll() {
        try {
            openSession();
            return session.createQuery("FROM Profession p ORDER BY p.libelle ASC").list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    // Modifier profession
    public boolean updateProfession(Profession profession) {
        try {
            openSession();
            transaction = session.beginTransaction();
            session.update(profession);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

    }

    // Delete profession
    public boolean deleteProfession(Profession profession) {
        try {
            openSession();
            transaction = session.beginTransaction();
            session.delete(profession);
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
