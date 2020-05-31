package dao;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Profession;
import models.config.HibernateUtil;

public class ProfessionDAO {

    private static Transaction transaction = null;
    Session session = HibernateUtil.getSessionFactory().openSession();

    // Ajouter une profession
    public boolean saveProfession(Profession profession) {

        try {
            transaction = session.beginTransaction();
            session.save(profession);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }

    }

    //Search profession via un objet
    public Profession searchProfession(Profession profession) {

        Profession profession2 = null;
        try {
            profession2 = session.createQuery("from Profession p", Profession.class).list().get(0);
            session.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return profession2;
    }

    // Search profession via son libelle
    public Profession findProfessionByLibeller(String libelle) {

        Profession profession = null;
        try {
            profession = (Profession) session.createQuery("FROM  Profession p  WHERE p.libelle=:lib").setParameter("lib", libelle).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profession;
    }

    // Delete profession via son libelle
    public void deleteProfession(String libelle) {
        try {
            Profession profession = findProfessionByLibeller(libelle);
            session.delete(profession);
            session.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // la liste de tous les professions
    public ArrayList<Profession> getAllProfession() {
        ArrayList<Profession> lisProfessions = new ArrayList<>();
        try {
            lisProfessions = (ArrayList<Profession>) session.createQuery("FROM  Profession p ").list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lisProfessions;
    }

    // Modifier profession
    public boolean updateProfession(Profession profession) {
        //session.flush();
        try {
            transaction = session.beginTransaction();
            session.update(profession);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    // Delete profession
    public boolean deleteProfession(Profession profession) {
        try {
            transaction = session.beginTransaction();
            session.delete(profession);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
