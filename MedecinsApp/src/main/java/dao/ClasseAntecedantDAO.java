package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.hibernate.Session;
import org.hibernate.Transaction;
import models.ClasseAntecedent;
import models.Patient;
import models.config.HibernateUtil;



public class ClasseAntecedantDAO {


	
	private static Transaction transaction = null;
    Session session = HibernateUtil.getSessionFactory().openSession();

    // Ajouter ClasseAntecedent
    public boolean saveClasseAntecedant(ClasseAntecedent classeAntecedent) {

        try {
            transaction = session.beginTransaction();
            session.save(classeAntecedent);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }

    }

    //Search ClasseAntecedent via un objet
    public ClasseAntecedent searchClasseAntecedant(ClasseAntecedent classeAntecedent) {

    	ClasseAntecedent classeAntecedent2 = null;
        try {
        	classeAntecedent2 = session.createQuery("from ClasseAntecedent p", ClasseAntecedent.class).list().get(0);
            session.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classeAntecedent2;
    }

    // Search ClasseAntecedent via son libelle
    public ClasseAntecedent findClasseAntecedant(String libelle) {

    	List<ClasseAntecedent> classeAntecedent = null;
        try {
            classeAntecedent = session.createQuery("FROM  ClasseAntecedent cl  WHERE cl.libelle=:lib").setParameter("lib", libelle).list();
           // session.flush();
            if(classeAntecedent.size()>0) {
            	return classeAntecedent.get(0);
            }else {
            	return null;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Delete ClasseAntecedent via son libelle
    public void deleteClasseAntecedent(String libelle) {
        try {
        	ClasseAntecedent classeAntecedent = findClasseAntecedant(libelle);
            session.delete(classeAntecedent);
            session.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // la liste de tous les ClasseAntecedent
    public ArrayList<ClasseAntecedent> getAllClasseAntecedent() {
        ArrayList<ClasseAntecedent> lisClasseAntecedent = new ArrayList<>();
        try {
        	lisClasseAntecedent = (ArrayList<ClasseAntecedent>) session.createQuery("FROM  ClasseAntecedent clAnt ").list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lisClasseAntecedent;
    }

    // Modifier ClasseAntecedent
    public boolean updateClasseAntecedent(ClasseAntecedent classeAntecedent) {
        //session.flush();
        try {
            transaction = session.beginTransaction();
            session.update(classeAntecedent);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    // Delete ClasseAntecedent
    public boolean deleteProfession(ClasseAntecedent classeAntecedent) {
        try {
            transaction = session.beginTransaction();
            session.delete(classeAntecedent);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    
    public ClasseAntecedent findClAntById(Long id) {
        try {
            return session.get(ClasseAntecedent.class, id);
        } catch (Exception e) {
            
            return null;
        }
    }
    
    
}
