/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Cursor;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author A663588
 */
public class Menu extends javax.swing.JFrame {

    private ListePatients listePatients;

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        desktopPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.listePatients = new ListePatients();
        desktopPane.add(listePatients).setVisible(true);
        try {
            listePatients.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(ListePatients.class.getName()).log(Level.SEVERE, null, ex);
        }
        desktopPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public ListePatients getListePatients() {
        return listePatients;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        patientsMenu = new javax.swing.JMenu();
        listePatientsMenuItem = new javax.swing.JMenuItem();
        medecinsMenu = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        professionMenu = new javax.swing.JMenu();
        listeProfessionMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        listeClasseAnteMenuItem = new javax.swing.JMenuItem();
        typeConsultationMenu = new javax.swing.JMenu();
        listeTypeConsultationMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GYNELOGBIS");

        desktopPane.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1051, Short.MAX_VALUE)
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );

        patientsMenu.setText("Patients");
        patientsMenu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        listePatientsMenuItem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        listePatientsMenuItem.setText("Liste des patients");
        listePatientsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listePatientsMenuItemActionPerformed(evt);
            }
        });
        patientsMenu.add(listePatientsMenuItem);

        menuBar.add(patientsMenu);

        medecinsMenu.setMnemonic('f');
        medecinsMenu.setText("Médecins");
        medecinsMenu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        medecinsMenu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                medecinsMenuKeyPressed(evt);
            }
        });

        jMenuItem4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jMenuItem4.setText("Gestion des utilisateurs");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        medecinsMenu.add(jMenuItem4);

        menuBar.add(medecinsMenu);

        professionMenu.setMnemonic('e');
        professionMenu.setText("Professions");
        professionMenu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        professionMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                professionMenuActionPerformed(evt);
            }
        });

        listeProfessionMenuItem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        listeProfessionMenuItem.setMnemonic('t');
        listeProfessionMenuItem.setText("Gestion des professions");
        listeProfessionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listeProfessionMenuItemActionPerformed(evt);
            }
        });
        professionMenu.add(listeProfessionMenuItem);

        menuBar.add(professionMenu);

        jMenu2.setText("Antécedents");
        jMenu2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jMenuItem3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jMenuItem3.setText("Gestion des antécedents");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        listeClasseAnteMenuItem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        listeClasseAnteMenuItem.setText("Gestion des classes d'antécedents");
        listeClasseAnteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listeClasseAnteMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(listeClasseAnteMenuItem);

        menuBar.add(jMenu2);

        typeConsultationMenu.setText("Types de consultation");
        typeConsultationMenu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        listeTypeConsultationMenuItem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        listeTypeConsultationMenuItem.setText("Gestion des types");
        listeTypeConsultationMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listeTypeConsultationMenuItemActionPerformed(evt);
            }
        });
        typeConsultationMenu.add(listeTypeConsultationMenuItem);

        menuBar.add(typeConsultationMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listeProfessionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listeProfessionMenuItemActionPerformed
        GestionProfession gestionProfession = new GestionProfession();
        desktopPane.add(gestionProfession).setVisible(true);
    }//GEN-LAST:event_listeProfessionMenuItemActionPerformed


    private void listePatientsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listePatientsMenuItemActionPerformed
        if (listePatients != null) {
            listePatients.setVisible(true);
        } else {
            desktopPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            listePatients = new ListePatients();
            desktopPane.add(listePatients).setVisible(true);
            try {
                listePatients.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ListePatients.class.getName()).log(Level.SEVERE, null, ex);
            }
            desktopPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }


    }//GEN-LAST:event_listePatientsMenuItemActionPerformed

    private void listeTypeConsultationMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listeTypeConsultationMenuItemActionPerformed
        GestionTypeConsultation gestionTypeConsultation = new GestionTypeConsultation();
        desktopPane.add(gestionTypeConsultation).setVisible(true);
    }//GEN-LAST:event_listeTypeConsultationMenuItemActionPerformed
    private void listeClasseAnteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        GestionClasseAntecedent classeAntecedant = new GestionClasseAntecedent();
        desktopPane.add(classeAntecedant).setVisible(true);
    }

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        GestionMedecinUser gestionMedecinUser = new GestionMedecinUser();
        desktopPane.add(gestionMedecinUser).setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void medecinsMenuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_medecinsMenuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_medecinsMenuKeyPressed

    private void professionMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_professionMenuActionPerformed
        BatchFiles batchFiles = new BatchFiles();
        desktopPane.add(batchFiles).setVisible(true);
    }//GEN-LAST:event_professionMenuActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
        GestionAntecedent gestionAntecedant = new GestionAntecedent();
        desktopPane.add(gestionAntecedant).setVisible(true);
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem listeClasseAnteMenuItem;
    private javax.swing.JMenuItem listePatientsMenuItem;
    private javax.swing.JMenuItem listeProfessionMenuItem;
    private javax.swing.JMenuItem listeTypeConsultationMenuItem;
    private javax.swing.JMenu medecinsMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu patientsMenu;
    private javax.swing.JMenu professionMenu;
    private javax.swing.JMenu typeConsultationMenu;
    // End of variables declaration//GEN-END:variables

}
