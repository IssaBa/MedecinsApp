package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Antecedent;
import models.ClasseAntecedent;
import models.config.HibernateUtil;

public class AntecedantDAO {
	private static Transaction transaction = null;
    Session session = HibernateUtil.getSessionFactory().openSession();

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
            e.printStackTrace();
            return false;
        }

    }

    //Search Antecedent via un objet
    public Antecedent searchAntecedant(Antecedent antecedent) {

    	Antecedent classeAntecedent2 = null;
        try {
        	classeAntecedent2 = session.createQuery("from Antecedent ant", Antecedent.class).list().get(0);
            session.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classeAntecedent2;
    }

    // Search Antecedent via son libelle
    public Antecedent findAntecedant(String libelle) {

    	List<Antecedent> antecedent = null;
        try {
        	antecedent = session.createQuery("FROM  Antecedent ant  WHERE ant.libelle=:lib").setParameter("lib", libelle).list();
           // session.flush();
            if(antecedent.size()>0) {
            	return antecedent.get(0);
            }else {
            	return null;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

    }

    // la liste de tous les Antecedent
    public ArrayList<Antecedent> getAllAntecedent() {
        ArrayList<Antecedent> lisAntecedent = new ArrayList<>();
        try {
        	lisAntecedent = (ArrayList<Antecedent>) session.createQuery("FROM  Antecedent clAnt ").list();
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }

    }

}
