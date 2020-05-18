package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.MedecinUser;
import utils.HibernateUtil;


public class MedecinUserDAO {

	
	private static  Transaction transaction = null;
	Session session = HibernateUtil.getSessionFactory().openSession();
	
	// Add userMedecin
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
			Session session = HibernateUtil.getSessionFactory().openSession();

			usersSearch = session.createQuery("from MedecinUsers", MedecinUser.class).list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return usersSearch;
	}
	
	// Search d'un userMedecin via son matricule
	@SuppressWarnings("deprecation")
	public MedecinUser findUserByMatricule(String matricule) {
		
		MedecinUser medecinUser = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			medecinUser =  (MedecinUser) session.createQuery("from MedecinUsers as  mu  where mu.MATRICULE= :mat").setEntity("mat", matricule).list().get(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return medecinUser;
	}
	
	// Delete d'un userMedecin via son matricule
	public void deleteMedecinUserById(String matricule) {
		try {
				MedecinUser medecinUser =	findUserByMatricule(matricule);
				session.delete(medecinUser);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
