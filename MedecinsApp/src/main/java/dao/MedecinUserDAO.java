package dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

import models.MedecinUser;
import models.config.HibernateUtil;

public class MedecinUserDAO {

    private static Transaction transaction = null;
    Session session = HibernateUtil.getSessionFactory().openSession();

    // Ajout d'un user
    public void saveUser(MedecinUser users) {

        try {

            transaction = session.beginTransaction();
            session.save(users);
            transaction.commit();
            session.close();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

    }

    //Search usermedecin via un objet
    public MedecinUser searchUser(MedecinUser users) {

        MedecinUser usersSearch = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            usersSearch = session.createQuery("from Medecin_User", MedecinUser.class).list().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usersSearch;
    }

    // Search d'un userMedecin via son matricule
    public MedecinUser findUserByMatricule(String matricule) {

        MedecinUser medecinUser = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            MedecinUser medecin = (MedecinUser) session.createQuery("FROM  MedecinUser m  WHERE m.username=:mat").setParameter("mat", matricule).getSingleResult();
            if (medecin != null) {
                return medecin;
            } else {
                return null;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return medecinUser;
    }

    // Delete d'un userMedecin via son matricule
    public void deleteMedecinUserById(String matricule) {
        try {
            MedecinUser medecinUser = findUserByMatricule(matricule);
            session.delete(medecinUser);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
