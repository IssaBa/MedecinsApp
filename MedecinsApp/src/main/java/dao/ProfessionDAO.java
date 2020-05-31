package dao;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.MedecinUser;
import models.Profession;
import models.config.HibernateUtil;

public class ProfessionDAO {

	private static Transaction transaction = null;
    Session session = HibernateUtil.getSessionFactory().openSession();

 // Ajouter une profession
    public void saveProfession(Profession profession) {

        try {

            transaction = session.beginTransaction();
            session.save(profession);
            transaction.commit();
            session.close();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

    }

    //Search profession via un objet
    public Profession searchProfession(Profession profession) {

        Profession profession2 = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            profession2 = session.createQuery("from Profession", Profession.class).list().get(0);
            session.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return profession2;
    }

    // Search profession via son libelle
    public Profession findProfessionByLibeller(String libelle) {

       
        try {
	            session = HibernateUtil.getSessionFactory().openSession();
	            Profession profession = (Profession) session.createQuery("FROM  Profession p  WHERE p.libelle=:lib").setParameter("lib", libelle).getSingleResult();
	           
            if (profession != null) {
            	//System.out.println("Libelle :"+profession.getLibelle());
                return profession;
            } else {
            	System.out.println("Libelle : Null");
                return null;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
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
    	
    	try {
				
    		 ArrayList<Profession> lisProfessions = (ArrayList<Profession>) session.createQuery("FROM  Profession p ").list();
    		 if(lisProfessions.size()>0) {
    			 return lisProfessions;
    		 }else {
    			 return null;
    		 }
    		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	return null;
		
    	
	}
    
    // Modifier profession
    public void UpdateProfession(Profession profession) {
    	//session.flush();
    	try {
    		Session session = HibernateUtil.getSessionFactory().openSession();
    		transaction = session.beginTransaction();
            session.update(profession);
            transaction.commit();
            session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
    
    
    // Delete profession
    public void DeleteProfession(Profession profession) {
    	try {
    		
    		transaction = session.beginTransaction();
            session.delete(profession);
            transaction.commit();
            session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

    
	
}
