/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import dao.MedecinUserDAO;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.util.logging.Level;
import java.util.logging.Logger;
import models.MedecinUser;

/**
 *
 * @author A663588
 */
public class GestionMedecinUser extends javax.swing.JInternalFrame {

    DefaultTableModel model = new DefaultTableModel();
    private final String AJOUTER = "AJOUTER MEDECIN";
    private final String MODIFIER = "MODIFIER MEDECIN";
    private final String SUPPRIMER = "SUPPRIMER MEDECIN";
    private final MedecinUserDAO MU_DAO;
    private static final Logger LOGGER = Logger.getLogger(GestionMedecinUser.class.getName());

    public void remplirTableMedecin() {
        try {
            model.setRowCount(0);
            model.setColumnIdentifiers(new String[]{"ID", "USERNAME", "NOM", "PRENOM"});
            List<MedecinUser> medecinUsers = MU_DAO.getAllMedecinuser();
            for (int i = 0; i < medecinUsers.size(); i++) {
                model.addRow(new Object[]{medecinUsers.get(i).getId(), medecinUsers.get(i).getUsername(), medecinUsers.get(i).getNom(), medecinUsers.get(i).getPrenom()});
            }
            tablemedecin.setModel(model);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public void checkChoixSelect(String choixAction) {
        try {
            if (choixAction.equalsIgnoreCase("")) {
                desactiverChamps();
                choixBTX.setEnabled(false);
                choixBTX.setText("Aucune action");
            } else if (choixAction.equalsIgnoreCase(AJOUTER)) {
                activerChamps();
                choixBTX.setEnabled(true);
                choixBTX.setText(AJOUTER);
            } else if (choixAction.equalsIgnoreCase(MODIFIER)) {
                activerChamps();
                usernameTxt.setEnabled(false);
                choixBTX.setEnabled(true);
                choixBTX.setText(MODIFIER);
            } else if (choixAction.equalsIgnoreCase(SUPPRIMER)) {
                desactiverChamps();
                choixBTX.setEnabled(true);
                choixBTX.setText(SUPPRIMER);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public List<MedecinUser> listItemMedecinUser() {
        ArrayList<MedecinUser> list = MU_DAO.getAllMedecinuser();
        return list;
    }

    public void remplirComboChoix() {
        comboChoix.removeAll();
        comboChoix.addItem("");
        comboChoix.addItem(AJOUTER);
        comboChoix.addItem(MODIFIER);
        comboChoix.addItem(SUPPRIMER);
        comboChoix.setSelectedIndex(0);
    }

    public void activerChamps() {
        usernameTxt.setEnabled(true);
        nomTxt.setEnabled(true);
        prenomTxt.setEnabled(true);
        password1Txt.setEnabled(true);
        password2Txt.setEnabled(true);
    }

    public void desactiverChamps() {
        usernameTxt.setEnabled(false);
        nomTxt.setEnabled(false);
        prenomTxt.setEnabled(false);
        password1Txt.setEnabled(false);
        password2Txt.setEnabled(false);

    }

    public void viderChamps() {
        nomTxt.setText("");
        prenomTxt.setText("");
        usernameTxt.setText("");
        password1Txt.setText("");
        password2Txt.setText("");

    }

    private void refreshAll() {
        desactiverChamps();
        remplirComboChoix();
        remplirTableMedecin();
        choixBTX.setEnabled(false);
        choixBTX.setText("Aucune action");
    }

    public boolean verifChamps() {
        return !(usernameTxt.getText().equals("") || nomTxt.getText().equals("") || prenomTxt.getText().equals("") || password1Txt.getText().equals("") || password2Txt.getText().equals(""));
    }

    public void reinitialiserPwd() {
        password1Txt.setText("");
        password2Txt.setText("");
    }

    public boolean verifPassword() {
        return String.copyValueOf(password1Txt.getPassword()).equals(String.copyValueOf(password2Txt.getPassword()));
    }

    public GestionMedecinUser() {
        initComponents();
        MU_DAO = new MedecinUserDAO();
        refreshAll();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDonnees = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        usernameTxt = new javax.swing.JTextField();
        nomTxt = new javax.swing.JTextField();
        prenomTxt = new javax.swing.JTextField();
        password1Txt = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        password2Txt = new javax.swing.JPasswordField();
        choixBTX = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        comboChoix = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablemedecin = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Nom d'utilisateur");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Nom");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Prénom");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Mot de passe");

        usernameTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        nomTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        prenomTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        password1Txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Confirmer mot de passe");

        password2Txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        choixBTX.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        choixBTX.setText("jButton1");
        choixBTX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choixBTXActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Choix Action");

        comboChoix.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboChoix.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboChoixItemStateChanged(evt);
            }
        });
        comboChoix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboChoixActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDonneesLayout = new javax.swing.GroupLayout(panelDonnees);
        panelDonnees.setLayout(panelDonneesLayout);
        panelDonneesLayout.setHorizontalGroup(
            panelDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDonneesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDonneesLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(password2Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDonneesLayout.createSequentialGroup()
                        .addGroup(panelDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nomTxt)
                            .addComponent(prenomTxt)
                            .addComponent(password1Txt, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDonneesLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboChoix, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(panelDonneesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDonneesLayout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(panelDonneesLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))))
            .addGroup(panelDonneesLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(choixBTX, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDonneesLayout.setVerticalGroup(
            panelDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDonneesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboChoix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(usernameTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nomTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prenomTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(panelDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password1Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(panelDonneesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password2Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addComponent(choixBTX, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        tablemedecin.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tablemedecin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

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
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(panelDonnees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDonnees, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboChoixItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboChoixItemStateChanged

    }//GEN-LAST:event_comboChoixItemStateChanged

    private void choixBTXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choixBTXActionPerformed

        //Ajout Medecin
        if (comboChoix.getSelectedItem().toString().equalsIgnoreCase(AJOUTER)) {

            if (verifChamps()) {
                if (verifPassword()) {

                    MedecinUser medecinUserCheck = MU_DAO.findUserByUsername(usernameTxt.getText());
                    if (medecinUserCheck == null) {
                        MedecinUser medecinUserNew = new MedecinUser();
                        medecinUserNew.setNom(nomTxt.getText());
                        medecinUserNew.setPrenom(prenomTxt.getText());
                        medecinUserNew.setUsername(usernameTxt.getText());
                        medecinUserNew.setPassword(String.copyValueOf(password1Txt.getPassword()));
                        if (MU_DAO.save(medecinUserNew)) {
                            JOptionPane.showMessageDialog(this, "UTILISATEUR AJOUTE AVEC SUCCES", "GESTION  MEDECIN", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "ECHEC AJOUT D'UTILISATEUR !", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Un utilisateur porte déjà ce nom d'utilisateur", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Les deux mots de passe doivent être identiques", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                    reinitialiserPwd();
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "TOUS LES CHAMPS SONT OBLIGATOIRES MERCI", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (comboChoix.getSelectedItem().toString().equalsIgnoreCase(MODIFIER)) {
            // Modifier Medecin
            if (verifChamps()) {
                if (verifPassword()) {

                    MedecinUser medecinUserCheck = MU_DAO.findUserByUsername(usernameTxt.getText());
                    if (medecinUserCheck != null) {
                        MedecinUser medecinUserNew = new MedecinUser();

                        medecinUserNew.setId(medecinUserCheck.getId());
                        medecinUserNew.setNom(nomTxt.getText());
                        medecinUserNew.setPrenom(prenomTxt.getText());
                        medecinUserNew.setUsername(usernameTxt.getText());
                        medecinUserNew.setPassword(String.copyValueOf(password1Txt.getPassword()));
                        if (MU_DAO.update(medecinUserNew)) {
                            JOptionPane.showMessageDialog(null, "UTILISATEUR MODIFIE AVEC SUCCESS", "GESTION  MEDECIN", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "ECHEC MODIFICATION DE L'UTILISATEUR ! ", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Les deux mots de passe ne correspondent pas", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                    reinitialiserPwd();
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "TOUS LES CHAMPS SONT OBLIGATOIRES MERCI", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (comboChoix.getSelectedItem().toString().equalsIgnoreCase(SUPPRIMER)) {
            // Suppression Medecin
            MedecinUser medecinUserCheck = MU_DAO.findUserByUsername(usernameTxt.getText());
            if (medecinUserCheck != null) {
                if (MU_DAO.delete(medecinUserCheck)) {
                    JOptionPane.showMessageDialog(null, "MEDECIN SUPPRIME AVEC SUCCESS", "GESTION  MEDECIN", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "ECHEC SUPPRESSION DU MEDECIN ! ", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "UTILISATEUR INTROUVABLE !", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
            }

        }

        remplirTableMedecin();
        viderChamps();
        desactiverChamps();
        comboChoix.setSelectedIndex(0);
        choixBTX.setEnabled(false);
        choixBTX.setText("Aucune action");

    }//GEN-LAST:event_choixBTXActionPerformed

    private void tablemedecinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablemedecinMouseClicked
        String action = (String) comboChoix.getSelectedItem();
        DefaultTableModel defaultTableModel = (DefaultTableModel) tablemedecin.getModel();
        int selectRowsIndex = tablemedecin.getSelectedRow();

        if (action.isEmpty()) {
            tablemedecin.clearSelection();
            JOptionPane.showMessageDialog(this, "Merci de choisir une action", "GESTION DE MEDECINS", JOptionPane.ERROR_MESSAGE);
        } else if (action.equals(MODIFIER) || action.equals(SUPPRIMER)) {
            try {
                String username = defaultTableModel.getValueAt(selectRowsIndex, 1).toString();
                MedecinUser medecinUser = MU_DAO.findUserByUsername(username);
                if (medecinUser != null) {
                    usernameTxt.setText(medecinUser.getUsername());
                    nomTxt.setText(medecinUser.getNom());
                    prenomTxt.setText(medecinUser.getPrenom());
                    password1Txt.setText(medecinUser.getPassword());
                    password2Txt.setText(medecinUser.getPassword());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Champ selectionné invalide ! Veuillez réessayer", "GESTION DE MEDECINS", JOptionPane.ERROR_MESSAGE);
            }

        }

    }//GEN-LAST:event_tablemedecinMouseClicked

    private void comboChoixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboChoixActionPerformed
        tablemedecin.clearSelection();
        checkChoixSelect(comboChoix.getSelectedItem().toString());
    }//GEN-LAST:event_comboChoixActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton choixBTX;
    private javax.swing.JComboBox<String> comboChoix;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField nomTxt;
    private javax.swing.JPanel panelDonnees;
    private javax.swing.JPasswordField password1Txt;
    private javax.swing.JPasswordField password2Txt;
    private javax.swing.JTextField prenomTxt;
    private javax.swing.JTable tablemedecin;
    private javax.swing.JTextField usernameTxt;
    // End of variables declaration//GEN-END:variables
}
