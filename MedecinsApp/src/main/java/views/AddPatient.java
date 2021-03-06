/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import dao.PatientDAO;
import dao.ProfessionDAO;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import models.CiviliteEnum;
import models.Patient;
import models.Profession;
import models.Session;
import models.SexeEnum;

/**
 *
 * @author Baye Lahad DIAGNE
 */
public class AddPatient extends javax.swing.JInternalFrame {

    ProfessionDAO professionDAO = new ProfessionDAO();
    PatientDAO patientDAO = new PatientDAO();
    private final List<Profession> professions;

    /**
     * Creates new form AddPatient
     */
    public AddPatient() {
        initComponents();
        professions = professionDAO.findAll();
        remplirProfessionCombo();
    }

    private void remplirProfessionCombo() {
        professionCombo.removeAllItems();
        professionConjointCombo.removeAllItems();
        professionCombo.addItem("");
        professionConjointCombo.addItem("");
        for (Profession p : professions) {
            professionCombo.addItem(p.getLibelle());
            professionConjointCombo.addItem(p.getLibelle());
        }
    }

    private void openListePatients() {
        ListePatients listePatients = new ListePatients();
        this.getDesktopPane().add(listePatients).setVisible(true);
        this.dispose();
    }

    private void savePatient() {
        Patient patient = new Patient();
        patient.setConnuDepuis(new Date());
        if (nomTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "LE NOM NE DOIT PAS ETRE VIDE !", "NOUVEAU PATIENT", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            patient.setNom(nomTxt.getText());
        }

        if (prenomTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "LE PRENOM NE DOIT PAS ETRE VIDE !", "NOUVEAU PATIENT", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            patient.setPrenom(prenomTxt.getText());
        }

        if (naissancePicker.getDate() == null) {
            JOptionPane.showMessageDialog(null, "LA DATE DE NAISSANCE NE DOIT PAS ETRE VIDE !", "NOUVEAU PATIENT", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            patient.setDateNaissance(naissancePicker.getDate());
        }

        patient.setLieuNaissance(lieuTxt.getText().isEmpty() ? null : lieuTxt.getText());
        patient.setSexe(SexeEnum.valueOf(sexeCombo.getSelectedItem().toString()));
        patient.setCivilite(CiviliteEnum.valueOf(civiliteCombo.getSelectedItem().toString()));
        if (professionCombo.getSelectedItem().toString().isEmpty()) {
            patient.setProfession(null);
        } else {
            Profession p = professions.get(professionCombo.getSelectedIndex() - 1);
            patient.setProfession(p);
        }
        patient.setNumeroDossier(numDossierTxt.getText());
        patient.setAdresse(adresseTxt.getText());
        patient.setTelephonePortable(telPortableTxt.getText());
        patient.setTelephoneDomicile(telDomicileTxt.getText());
        patient.setTelephoneBureau(telBureauTxt.getText());
        patient.setPrenomConjoint(prenomConjointTxt.getText());
        patient.setNomConjoint(nomConjointTxt.getText());
        patient.setTelephoneConjoint(telConjointTxt.getText());
        if (professionConjointCombo.getSelectedItem().toString().isEmpty()) {
            patient.setProfession(null);
        } else {
            Profession p = professions.get(professionConjointCombo.getSelectedIndex() - 1);
            patient.setProfession(p);
        }

        patient.setMedecinTraitant(Session.getUser());

        if (patientDAO.save(patient)) {
            JOptionPane.showMessageDialog(null, "PATIENT AJOUTé AVEC SUCCéS !", "NOUVEAU PATIENT", JOptionPane.INFORMATION_MESSAGE);
            openListePatients();
        } else {
            JOptionPane.showMessageDialog(null, "ECHEC DE LA SAUVEGARDE !", "NOUVEAU PATIENT", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        naissancePicker = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        prenomTxt = new javax.swing.JTextField();
        nomTxt = new javax.swing.JTextField();
        lieuTxt = new javax.swing.JTextField();
        sexeCombo = new javax.swing.JComboBox<>();
        civiliteCombo = new javax.swing.JComboBox<>();
        numDossierTxt = new javax.swing.JTextField();
        telPortableTxt = new javax.swing.JTextField();
        telDomicileTxt = new javax.swing.JTextField();
        telBureauTxt = new javax.swing.JTextField();
        prenomConjointTxt = new javax.swing.JTextField();
        nomConjointTxt = new javax.swing.JTextField();
        telConjointTxt = new javax.swing.JTextField();
        professionConjointCombo = new javax.swing.JComboBox<>();
        enregistrerBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        professionCombo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        adresseTxt = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Prénom");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Nom");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Date de naissance");

        naissancePicker.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Lieu de naissance");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Sexe");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Civilité");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Numéro de dossier");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Adresse");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Télephone (portable)");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Téléphone (domicile)");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Téléphone (bureau)");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Prénom (conjoint)");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Nom (conjoint)");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Téléphone (conjoint)");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Profession");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Profession (conjoint)");

        prenomTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        nomTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lieuTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lieuTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lieuTxtActionPerformed(evt);
            }
        });

        sexeCombo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sexeCombo.setModel(new DefaultComboBoxModel(SexeEnum.values()));

        civiliteCombo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        civiliteCombo.setModel(new DefaultComboBoxModel(CiviliteEnum.values()));

        numDossierTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        telPortableTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        telDomicileTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        telBureauTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        telBureauTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telBureauTxtActionPerformed(evt);
            }
        });

        prenomConjointTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        prenomConjointTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenomConjointTxtActionPerformed(evt);
            }
        });

        nomConjointTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        telConjointTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        professionConjointCombo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        enregistrerBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        enregistrerBtn.setText("Enregistrer");
        enregistrerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enregistrerBtnActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("Annuler");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        professionCombo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        adresseTxt.setColumns(20);
        adresseTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        adresseTxt.setRows(3);
        jScrollPane1.setViewportView(adresseTxt);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(prenomTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nomTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(111, 111, 111)
                .addComponent(jLabel8)
                .addGap(123, 123, 123)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(naissancePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(telPortableTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addGap(22, 22, 22)
                .addComponent(lieuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(telDomicileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addGap(123, 123, 123)
                .addComponent(sexeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111)
                .addComponent(jLabel11)
                .addGap(28, 28, 28)
                .addComponent(telBureauTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel6)
                .addGap(109, 109, 109)
                .addComponent(civiliteCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111)
                .addComponent(jLabel12)
                .addGap(41, 41, 41)
                .addComponent(prenomConjointTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel15)
                .addGap(79, 79, 79)
                .addComponent(professionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111)
                .addComponent(jLabel13)
                .addGap(64, 64, 64)
                .addComponent(nomConjointTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel7)
                .addGap(12, 12, 12)
                .addComponent(numDossierTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(telConjointTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(professionConjointCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(287, 287, 287)
                .addComponent(enregistrerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(prenomTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(nomTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(naissancePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel9))
                    .addComponent(telPortableTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lieuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(telDomicileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(sexeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel11))
                    .addComponent(telBureauTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(civiliteCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel12))
                    .addComponent(prenomConjointTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel15))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(professionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel13))
                    .addComponent(nomConjointTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(numDossierTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel14)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel16))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(telConjointTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(professionConjointCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(enregistrerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void enregistrerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enregistrerBtnActionPerformed
        savePatient();
    }//GEN-LAST:event_enregistrerBtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        openListePatients();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void lieuTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lieuTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lieuTxtActionPerformed

    private void telBureauTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telBureauTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telBureauTxtActionPerformed

    private void prenomConjointTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prenomConjointTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prenomConjointTxtActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea adresseTxt;
    private javax.swing.JComboBox<String> civiliteCombo;
    private javax.swing.JButton enregistrerBtn;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lieuTxt;
    private com.toedter.calendar.JDateChooser naissancePicker;
    private javax.swing.JTextField nomConjointTxt;
    private javax.swing.JTextField nomTxt;
    private javax.swing.JTextField numDossierTxt;
    private javax.swing.JTextField prenomConjointTxt;
    private javax.swing.JTextField prenomTxt;
    private javax.swing.JComboBox<String> professionCombo;
    private javax.swing.JComboBox<String> professionConjointCombo;
    private javax.swing.JComboBox<String> sexeCombo;
    private javax.swing.JTextField telBureauTxt;
    private javax.swing.JTextField telConjointTxt;
    private javax.swing.JTextField telDomicileTxt;
    private javax.swing.JTextField telPortableTxt;
    // End of variables declaration//GEN-END:variables
}
