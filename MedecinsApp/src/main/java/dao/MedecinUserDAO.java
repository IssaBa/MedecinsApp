package dao;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import models.MedecinUser;
import models.config.HibernateUtil;
import org.hibernate.HibernateException;

public class MedecinUserDAO {

    private static Transaction transaction = null;
    private Session session;
    private static final Logger LOGGER = Logger.getLogger(MedecinUserDAO.class.getName());

    // Ajout d'un user
    public boolean save(MedecinUser user) {
        try {
            openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            closeSession();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

    }

    // Search d'un userMedecin via son matricule
    public MedecinUser findUserByUsername(String username) {
        try {
            openSession();
            MedecinUser medecin = (MedecinUser) session.createQuery("FROM  MedecinUser m  WHERE m.username=:mat").setParameter("mat", username).getSingleResult();
            return medecin;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    public ArrayList<MedecinUser> getAllMedecinuser() {

        try {
            openSession();
            ArrayList<MedecinUser> listmedecin = (ArrayList<MedecinUser>) session.createQuery("FROM MedecinUser m").list();
            closeSession();
            return listmedecin;
        } catch (HibernateException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    public boolean update(MedecinUser medecinUser) {
        try {
            openSession();
            transaction = session.beginTransaction();
            session.update(medecinUser);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

    }

    public boolean delete(MedecinUser medecinUser) {
        try {
            openSession();
            transaction = session.beginTransaction();
            session.delete(medecinUser);
            transaction.commit();
            session.close();
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
