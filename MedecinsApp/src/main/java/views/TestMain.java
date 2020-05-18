package views;

import dao.MedecinUserDAO;
import models.MedecinUser;

public class TestMain {

    public static void main(String[] args) {

        MedecinUserDAO usDao = new MedecinUserDAO();
        MedecinUser users1 = new MedecinUser();
        users1.setNom("BA");
        users1.setPrenom("Issa");
        users1.setUsername("Isac");
        users1.setPassword("test");

        // save users
        usDao.saveUser(users1);

        // find users
        System.out.println(usDao.searchUser(users1).getUsername());

    }

}
