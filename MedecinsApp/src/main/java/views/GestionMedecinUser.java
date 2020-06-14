/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.MedecinUserDAO;
import models.MedecinUser;

/**
 *
 * @author A663588
 */
public class GestionMedecinUser extends javax.swing.JInternalFrame {

    DefaultTableModel model = new DefaultTableModel();

    Long idAntecedantModif;

    Long idClasseAntecedant;

    String libeleModif = null;

    boolean reset = false;

    public void remplirTableMedecin() {
        try {
            //tablemedecin.removeAll();
            MedecinUserDAO dao = new MedecinUserDAO();
            model.setColumnIdentifiers(new String[]{"ID", "USERNAME", "NOM", "PRENOM"});
            List<MedecinUser> medecinUsers = dao.getAllMedecinuser();
            if (medecinUsers.size() > 0) {
                for (int i = 0; i < medecinUsers.size(); i++) {
                    model.addRow(new String[]{"" + medecinUsers.get(i).getId(), medecinUsers.get(i).getUsername(), medecinUsers.get(i).getNom(), medecinUsers.get(i).getPrenom()});
                }
                tablemedecin.setModel(model);
                tablemedecin.setEnabled(false);
            } else {
                tablemedecin.setEnabled(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void checkChoixSelect(String choixAction) {
        try {
            if (choixAction.equalsIgnoreCase("")) {
                desactiverChamps();
            } else if (choixAction.equalsIgnoreCase("AJOUTER  MEDECIN")) {
                activerChamps();
                choixBTX.setText("AJOUTER  MEDECIN");
            } else if (choixAction.equalsIgnoreCase("MODIFIER  MEDECIN")) {
                username.setEditable(false);
                activerChamps();
                choixBTX.setText("MODIFIER  MEDECIN");
            } else if (choixAction.equalsIgnoreCase("SUPPRIMER  MEDECIN")) {
                username.setEditable(false);
                activerChamps();
                choixBTX.setText("SUPPRIMER  MEDECIN");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MedecinUser> listItemMedecinUser() {
        try {

            MedecinUserDAO dao = new MedecinUserDAO();
            ArrayList<MedecinUser> list = dao.getAllMedecinuser();
            if (list.size() > 0) {
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void remplirComboChoix() {
        ComboChoix.removeAll();
        try {
            for (int i = 0; i < listItemCombo().size(); i++) {
                ComboChoix.addItem(listItemCombo().get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> listItemCombo() {
        try {
            ArrayList<String> list = new ArrayList<String>();
            list.add("");
            list.add("AJOUTER  MEDECIN");
            list.add("MODIFIER  MEDECIN");
            list.add("SUPPRIMER  MEDECIN");
            if (list.size() > 0) {
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void activerChamps() {
        PanelDonner.setVisible(true);
        tablemedecin.setEnabled(true);

    }

    public void desactiverChamps() {
        PanelDonner.setVisible(false);
        tablemedecin.setEnabled(false);

    }

    public void viderChamps() {
        nom.setText("");
        prenom.setText("");
        username.setText("");
        password1.setText("");
        password2.setText("");

    }

    private void refrechALL() {
        desactiverChamps();
        remplirComboChoix();
        model.setRowCount(1);
        remplirTableMedecin();
    }

    public boolean verifChamps() {
        if (username.getText().equals("") || nom.getText().equals("") || prenom.getText().equals("") || password1.getText().equals("") || password2.getText().equals("")) {
            return false;
        } else {
            return true;
        }

    }

    public void reitialiserPwd() {

        password1.setText("");
        password2.setText("");
    }

    public boolean VerifPassword(String pwd1, String pwd2) {
        if (!pwd1.equals(pwd2)) {
            return false;
        } else {
            return true;
        }
    }

    public GestionMedecinUser() {
        initComponents();
        refrechALL();
    }//GEN-initComponents

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ComboChoix = new javax.swing.JComboBox<>();
        PanelDonner = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        nom = new javax.swing.JTextField();
        prenom = new javax.swing.JTextField();
        password1 = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        password2 = new javax.swing.JPasswordField();
        choixBTX = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablemedecin = new javax.swing.JTable();

        jLabel1.setText("Choix Action");

        ComboChoix.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboChoixItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(ComboChoix, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(14, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(ComboChoix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        jLabel2.setText("Username");

        jLabel3.setText("Nom");

        jLabel4.setText("Prenom");

        jLabel5.setText("Password");

        jLabel6.setText("Confirmer Password");

        choixBTX.setText("jButton1");
        choixBTX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choixBTXActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelDonnerLayout = new javax.swing.GroupLayout(PanelDonner);
        PanelDonner.setLayout(PanelDonnerLayout);
        PanelDonnerLayout.setHorizontalGroup(
                PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelDonnerLayout.createSequentialGroup()
                                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(PanelDonnerLayout.createSequentialGroup()
                                                .addGap(26, 26, 26)
                                                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(70, 70, 70)
                                                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(username)
                                                        .addComponent(nom)
                                                        .addComponent(prenom)
                                                        .addComponent(password1)
                                                        .addComponent(password2, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)))
                                        .addGroup(PanelDonnerLayout.createSequentialGroup()
                                                .addGap(143, 143, 143)
                                                .addComponent(choixBTX, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelDonnerLayout.setVerticalGroup(
                PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelDonnerLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(prenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(password1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(password2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6))
                                .addGap(45, 45, 45)
                                .addComponent(choixBTX)
                                .addContainerGap(51, Short.MAX_VALUE))
        );

        tablemedecin.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String[]{
                    "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        tablemedecin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablemedecinMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablemedecin);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(PanelDonner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(PanelDonner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void ComboChoixItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboChoixItemStateChanged
        checkChoixSelect(ComboChoix.getSelectedItem().toString());
    }//GEN-LAST:event_ComboChoixItemStateChanged

    private void choixBTXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choixBTXActionPerformed

        //Ajout Medecin
        if (choixBTX.getText().equalsIgnoreCase("AJOUTER  MEDECIN")) {

            if (verifChamps()) {
                if (VerifPassword(password1.getText(), password2.getText())) {
                    MedecinUserDAO dao = new MedecinUserDAO();
                    MedecinUser medecinUserCheck = dao.findUserByMatricule(username.getText());
                    if (medecinUserCheck == null) {
                        MedecinUser medecinUserNew = new MedecinUser();

                        medecinUserNew.setNom(nom.getText());
                        medecinUserNew.setPrenom(prenom.getText());
                        medecinUserNew.setUsername(username.getText());
                        medecinUserNew.setPassword(password1.getText());
                        if (dao.saveUser(medecinUserNew)) {
                            JOptionPane.showMessageDialog(null, "MEDECIN AJOUTER AVEC SUCCESS", "GESTION  MEDECIN", JOptionPane.INFORMATION_MESSAGE);
                            model.setRowCount(0);
                            remplirTableMedecin();
                            viderChamps();
                            reset = true;

                        } else {
                            JOptionPane.showMessageDialog(null, "ECHEC AJOUT DU MEDECIN ! ", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                            model.setRowCount(0);
                            remplirTableMedecin();
                            viderChamps();
                            reset = true;
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "CE MEDECIN EXISTE DEJA MEERCI !", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Les deux mots de passe ne correspond pas", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                    reitialiserPwd();
                }
            } else {
                JOptionPane.showMessageDialog(null, "TOUS LES CHAMPS SONT OBLIGATOIRE MERCI", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Modifier Medecin
        if (choixBTX.getText().equalsIgnoreCase("MODIFIER  MEDECIN")) {

            if (verifChamps()) {
                if (VerifPassword(password1.getText(), password2.getText())) {
                    MedecinUserDAO dao = new MedecinUserDAO();
                    MedecinUser medecinUserCheck = dao.findUserByMatricule(username.getText());
                    if (medecinUserCheck != null) {
                        MedecinUserDAO dao1 = new MedecinUserDAO();
                        MedecinUser medecinUserNew = new MedecinUser();

                        medecinUserNew.setId(medecinUserCheck.getId());
                        medecinUserNew.setNom(nom.getText());
                        medecinUserNew.setPrenom(prenom.getText());
                        medecinUserNew.setUsername(username.getText());
                        medecinUserNew.setPassword(password1.getText());
                        if (dao1.UpdateMedecinUser(medecinUserNew)) {
                            JOptionPane.showMessageDialog(null, "MEDECIN MODIFIER AVEC SUCCESS", "GESTION  MEDECIN", JOptionPane.INFORMATION_MESSAGE);
                            model.setRowCount(0);
                            remplirTableMedecin();
                            viderChamps();
                            reset = true;

                        } else {
                            JOptionPane.showMessageDialog(null, "ECHEC MODIFICATION DU MEDECIN ! ", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                            model.setRowCount(0);
                            remplirTableMedecin();
                            viderChamps();
                            reset = true;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Les deux mots de passe ne correspond pas", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                    reitialiserPwd();
                }
            } else {
                JOptionPane.showMessageDialog(null, "TOUS LES CHAMPS SONT OBLIGATOIRE MERCI", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Suppression Medecin
        if (choixBTX.getText().equalsIgnoreCase("SUPPRIMER  MEDECIN")) {

            if (verifChamps()) {
                if (VerifPassword(password1.getText(), password2.getText())) {
                    MedecinUserDAO dao = new MedecinUserDAO();
                    MedecinUser medecinUserCheck = dao.findUserByMatricule(username.getText());
                    if (medecinUserCheck != null) {
                        MedecinUserDAO dao1 = new MedecinUserDAO();
                        MedecinUser medecinUserNew = new MedecinUser();

                        medecinUserNew.setId(medecinUserCheck.getId());
                        medecinUserNew.setNom(nom.getText());
                        medecinUserNew.setPrenom(prenom.getText());
                        medecinUserNew.setUsername(username.getText());
                        medecinUserNew.setPassword(password1.getText());
                        if (dao1.DeleteMedecinUser(medecinUserNew)) {
                            JOptionPane.showMessageDialog(null, "MEDECIN SUPPRIMER AVEC SUCCESS", "GESTION  MEDECIN", JOptionPane.INFORMATION_MESSAGE);
                            model.setRowCount(0);
                            remplirTableMedecin();
                            viderChamps();
                            reset = true;

                        } else {
                            JOptionPane.showMessageDialog(null, "ECHEC SUPPRESSION DU MEDECIN ! ", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                            model.setRowCount(0);
                            remplirTableMedecin();
                            viderChamps();
                            reset = true;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Les deux mots de passe ne correspond pas", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                    reitialiserPwd();
                }
            } else {
                JOptionPane.showMessageDialog(null, "TOUS LES CHAMPS SONT OBLIGATOIRE MERCI", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
            }
        }


    }//GEN-LAST:event_choixBTXActionPerformed

    private void tablemedecinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablemedecinMouseClicked
        if (reset == true) {
            if (tablemedecin.getSelectedRow() == -1) {
                remplirTableMedecin();
                return;
            } else {
                JOptionPane.showMessageDialog(null, "Merci de refaire le choix d'action (AJOUTER / MODIFER / SUPPRIMER )", "GESTION PROFESSION", JOptionPane.ERROR_MESSAGE);
                //desactiverChamps();
                tablemedecin.setEnabled(false);
                reset = false;
            }

        } else {

            if (ComboChoix.getSelectedItem().toString().equalsIgnoreCase("MODIFIER  MEDECIN") || ComboChoix.getSelectedItem().toString().equalsIgnoreCase("SUPPRIMER  MEDECIN")) {
                DefaultTableModel defaultTableModel = (DefaultTableModel) tablemedecin.getModel();
                int selectRowsIndex = tablemedecin.getSelectedRow();
                //JOptionPane.showMessageDialog (null, selectRowsIndex , "TEST", JOptionPane.ERROR_MESSAGE);
                MedecinUserDAO dao = new MedecinUserDAO();
                MedecinUser medecinUser = dao.findUserByMatricule(defaultTableModel.getValueAt(selectRowsIndex, 1).toString());

                if (medecinUser != null) {
                    username.setText(medecinUser.getUsername());
                    nom.setText(medecinUser.getNom());
                    prenom.setText(medecinUser.getPrenom());
                    password1.setText(medecinUser.getPassword());
                    password2.setText(medecinUser.getPassword());
                }

            } else {
                JOptionPane.showMessageDialog(null, "Merci de faire le choix d'action Merci", "TEST", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_tablemedecinMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboChoix;
    private javax.swing.JPanel PanelDonner;
    private javax.swing.JButton choixBTX;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nom;
    private javax.swing.JPasswordField password1;
    private javax.swing.JPasswordField password2;
    private javax.swing.JTextField prenom;
    private javax.swing.JTable tablemedecin;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
