/**
 *
 * Created on 23 mai 2020 by PrinceNar
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Profession;
import models.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Baye Lahad DIAGNE
 */
public class ProfessionDAO {

    private static Transaction transaction = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    private static final Logger LOGGER = Logger.getLogger(ProfessionDAO.class.getName());
    
    
    public List<Profession> findAll() {
        try {
            return session.createNamedQuery("Profession.findAll").getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}
