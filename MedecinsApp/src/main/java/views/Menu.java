/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import javax.swing.JOptionPane;

/**
 *
 * @author A663588
 */
public class Menu extends javax.swing.JFrame {

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
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
        medecinsMenu = new javax.swing.JMenu();
        addMedecinMenuItem = new javax.swing.JMenuItem();
        modifMedecinMenuItem = new javax.swing.JMenuItem();
        professionMenu = new javax.swing.JMenu();
        listeProfessionMenuItem = new javax.swing.JMenuItem();
        batchMenuItem = new javax.swing.JMenuItem();
        patientsMenu = new javax.swing.JMenu();
        listePatientsMenuItem = new javax.swing.JMenuItem();
        classeAnteMenu = new javax.swing.JMenu();
        listeClasseAnteMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FAYEMILOG");

        desktopPane.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1051, Short.MAX_VALUE)
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
        );

        medecinsMenu.setMnemonic('f');
        medecinsMenu.setText("Médecins");
        medecinsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medecinsMenuActionPerformed(evt);
            }
        });

        addMedecinMenuItem.setMnemonic('o');
        addMedecinMenuItem.setText("Ajouter Medecins");
        addMedecinMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMedecinMenuItemMouseClicked(evt);
            }
        });
        addMedecinMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMedecinMenuItemActionPerformed(evt);
            }
        });
        medecinsMenu.add(addMedecinMenuItem);

        modifMedecinMenuItem.setText("Modification Medecins");
        modifMedecinMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifMedecinMenuItemActionPerformed(evt);
            }
        });
        medecinsMenu.add(modifMedecinMenuItem);

        menuBar.add(medecinsMenu);

        professionMenu.setMnemonic('e');
        professionMenu.setText("Professions");

        listeProfessionMenuItem.setMnemonic('t');
        listeProfessionMenuItem.setText("Liste des professions");
        listeProfessionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listeProfessionMenuItemActionPerformed(evt);
            }
        });
        professionMenu.add(listeProfessionMenuItem);

        batchMenuItem.setMnemonic('y');
        batchMenuItem.setText("BATCH");
        batchMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batchMenuItemActionPerformed(evt);
            }
        });
        professionMenu.add(batchMenuItem);

        menuBar.add(professionMenu);

        patientsMenu.setText("Patients");

        listePatientsMenuItem.setText("Liste des patients");
        listePatientsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listePatientsMenuItemActionPerformed(evt);
            }
        });
        patientsMenu.add(listePatientsMenuItem);

        menuBar.add(patientsMenu);

        classeAnteMenu.setText("Classe Antecedant");

        listeClasseAnteMenuItem.setText("Gestion CL ANT");
        listeClasseAnteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listeClasseAnteMenuItemActionPerformed(evt);
            }
        });
        classeAnteMenu.add(listeClasseAnteMenuItem);

        menuBar.add(classeAnteMenu);

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

    private void modifMedecinMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifMedecinMenuItemActionPerformed

        ModificationMedecin medecinModif = new ModificationMedecin();
        desktopPane.add(medecinModif).setVisible(true);
    }//GEN-LAST:event_modifMedecinMenuItemActionPerformed

    private void batchMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batchMenuItemActionPerformed
        BatchFiles batchFiles = new BatchFiles();
        desktopPane.add(batchFiles).setVisible(true);
    }//GEN-LAST:event_batchMenuItemActionPerformed

    private void listeProfessionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listeProfessionMenuItemActionPerformed
        GestionProfession gestionProfession = new GestionProfession();
        desktopPane.add(gestionProfession).setVisible(true);
    }//GEN-LAST:event_listeProfessionMenuItemActionPerformed

    private void listePatientsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listePatientsMenuItemActionPerformed
        ListePatients listePatients = new ListePatients();
        desktopPane.add(listePatients).setVisible(true);
    }//GEN-LAST:event_listePatientsMenuItemActionPerformed
    private void listeClasseAnteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        GestionClasseAntecedant classeAntecedant = new GestionClasseAntecedant();
        desktopPane.add(classeAntecedant).setVisible(true);
    }

    private void medecinsMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:

    }

    private void addMedecinMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        GestionMedecins gestionMedecins = new GestionMedecins();
        desktopPane.add(gestionMedecins).setVisible(true);
    }

    private void addMedecinMenuItemMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
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
    private javax.swing.JMenuItem addMedecinMenuItem;
    private javax.swing.JMenuItem batchMenuItem;
    private javax.swing.JMenu classeAnteMenu;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuItem listeClasseAnteMenuItem;
    private javax.swing.JMenuItem listePatientsMenuItem;
    private javax.swing.JMenuItem listeProfessionMenuItem;
    private javax.swing.JMenu medecinsMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem modifMedecinMenuItem;
    private javax.swing.JMenu patientsMenu;
    private javax.swing.JMenu professionMenu;
    // End of variables declaration//GEN-END:variables

}
