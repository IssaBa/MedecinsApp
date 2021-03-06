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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import models.CiviliteEnum;
import models.Patient;
import models.Profession;
import models.SexeEnum;
import org.hibernate.Hibernate;

/**
 *
 * @author Baye Lahad DIAGNE
 */
public class EditPatient extends javax.swing.JInternalFrame {

    ProfessionDAO professionDAO = new ProfessionDAO();
    PatientDAO patientDAO = new PatientDAO();
    Patient editedPatient;

    /**
     * Creates new form AddPatient
     * @param patient
     */
    public EditPatient(Patient patient) {
        initComponents();
        remplirProfessionCombo();
        this.editedPatient = patient;
        Hibernate.initialize(this.editedPatient.getProfession());
        Hibernate.initialize(this.editedPatient.getProfessionConjoint());
        setChampsForm();
    }

    private void remplirProfessionCombo() {
        List<Profession> professions = professionDAO.findAll();
        professionCombo.removeAllItems();
        professionConjointCombo.removeAllItems();
        professionCombo.addItem("");
        professionConjointCombo.addItem("");
        for (Profession p : professions) {
            professionCombo.addItem(p.getLibelle());
            professionConjointCombo.addItem(p.getLibelle());
        }
    }
    
    private void setChoixCombo(JComboBox<String> combo, String choix) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if(combo.getItemAt(i).equals(choix)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }
    
    private void setChoixCombo(JComboBox combo, Object choix) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if(combo.getItemAt(i).equals(choix)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void openListePatients() {
        ListePatients listePatients = new ListePatients();
        this.getDesktopPane().add(listePatients).setVisible(true);
        this.dispose();
    }
    
    private void setChampsForm() {
        prenomTxt.setText(editedPatient.getPrenom());
        nomTxt.setText(editedPatient.getNom());
        naissancePicker.setDate(editedPatient.getDateNaissance());
        lieuTxt.setText(editedPatient.getLieuNaissance());
        String prof = editedPatient.getProfession() != null ? editedPatient.getProfession().getLibelle() : "";
        setChoixCombo(civiliteCombo, editedPatient.getCivilite());
        setChoixCombo(sexeCombo, editedPatient.getSexe());
        setChoixCombo(professionCombo, prof);
        numDossierTxt.setText(editedPatient.getNumeroDossier());
        adresseTxt.setText(editedPatient.getAdresse());
        telPortableTxt.setText(editedPatient.getTelephonePortable());
        telDomicileTxt.setText(editedPatient.getTelephoneDomicile());
        telBureauTxt.setText(editedPatient.getTelephoneBureau());
        prenomConjointTxt.setText(editedPatient.getPrenomConjoint());
        nomConjointTxt.setText(editedPatient.getNomConjoint());
        telConjointTxt.setText(editedPatient.getTelephoneConjoint());
        String profC = editedPatient.getProfessionConjoint() != null ? editedPatient.getProfessionConjoint().getLibelle() : "";
        setChoixCombo(professionConjointCombo, profC);
    }

    private void updatePatient() {
        Patient newPatient = patientDAO.findById(editedPatient.getId());
        newPatient.setMedecinTraitant(editedPatient.getMedecinTraitant());
        newPatient.setConnuDepuis(editedPatient.getConnuDepuis());
        if (nomTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "LE NOM NE DOIT PAS ETRE VIDE !", "EDITION PATIENT", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            newPatient.setNom(nomTxt.getText());
        }

        if (prenomTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "LE PRENOM NE DOIT PAS ETRE VIDE !", "EDITION PATIENT", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            newPatient.setPrenom(prenomTxt.getText());
        }

        if (naissancePicker.getDate() == null) {
            JOptionPane.showMessageDialog(null, "LA DATE DE NAISSANCE NE DOIT PAS ETRE VIDE !", "NOUVEAU PATIENT", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            newPatient.setDateNaissance(naissancePicker.getDate());
        }

        newPatient.setLieuNaissance(lieuTxt.getText().isEmpty() ? null : lieuTxt.getText());
        newPatient.setSexe(SexeEnum.valueOf(sexeCombo.getSelectedItem().toString()));
        newPatient.setCivilite(CiviliteEnum.valueOf(civiliteCombo.getSelectedItem().toString()));
        if (professionCombo.getSelectedItem().toString().isEmpty()) {
            newPatient.setProfession(null);
        } else {
            Profession p = professionDAO.findByLibelle(professionCombo.getSelectedItem().toString());
            newPatient.setProfession(p);
        }
        newPatient.setNumeroDossier(numDossierTxt.getText());
        newPatient.setAdresse(adresseTxt.getText());
        newPatient.setTelephonePortable(telPortableTxt.getText());
        newPatient.setTelephoneDomicile(telDomicileTxt.getText());
        newPatient.setTelephoneBureau(telBureauTxt.getText());
        newPatient.setPrenomConjoint(prenomConjointTxt.getText());
        newPatient.setNomConjoint(nomConjointTxt.getText());
        newPatient.setTelephoneConjoint(telConjointTxt.getText());
        if (professionConjointCombo.getSelectedItem().toString().isEmpty()) {
            newPatient.setProfessionConjoint(null);
        } else {
            Profession p = professionDAO.findByLibelle(professionConjointCombo.getSelectedItem().toString());
            newPatient.setProfessionConjoint(p);
        }

        if (patientDAO.edit(newPatient)) {
            JOptionPane.showMessageDialog(null, "PATIENT MODIFIE AVEC SUCCES !", "EDITION PATIENT", JOptionPane.INFORMATION_MESSAGE);
            openListePatients();
        } else {
            JOptionPane.showMessageDialog(null, "ECHEC DE LA MODIFICATION !", "EDITION PATIENT", JOptionPane.ERROR_MESSAGE);
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

        jLabel1.setText("Prénom");

        jLabel2.setText("Nom");

        jLabel3.setText("Date de naissance");

        jLabel4.setText("Lieu de naissance");

        jLabel5.setText("Sexe");

        jLabel6.setText("Civilité");

        jLabel7.setText("Numéro de dossier");

        jLabel8.setText("Adresse");

        jLabel9.setText("Télephone (portable)");

        jLabel10.setText("Téléphone (domicile)");

        jLabel11.setText("Téléphone (bureau)");

        jLabel12.setText("Prénom (conjoint)");

        jLabel13.setText("Nom (conjoint)");

        jLabel14.setText("Téléphone (conjoint)");

        jLabel15.setText("Profession");

        jLabel16.setText("Profession (conjoint)");

        lieuTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lieuTxtActionPerformed(evt);
            }
        });

        sexeCombo.setModel(new DefaultComboBoxModel(SexeEnum.values()));

        civiliteCombo.setModel(new DefaultComboBoxModel(CiviliteEnum.values()));

        telBureauTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telBureauTxtActionPerformed(evt);
            }
        });

        prenomConjointTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenomConjointTxtActionPerformed(evt);
            }
        });

        enregistrerBtn.setText("Enregistrer");
        enregistrerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enregistrerBtnActionPerformed(evt);
            }
        });

        jButton2.setText("Annuler");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        adresseTxt.setColumns(20);
        adresseTxt.setRows(3);
        jScrollPane1.setViewportView(adresseTxt);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nomTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(naissancePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(prenomTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(84, 84, 84))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel15))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lieuTxt)
                                    .addComponent(sexeCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(civiliteCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(professionCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(numDossierTxt)))
                        .addGap(81, 81, 81)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(professionConjointCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(telBureauTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(prenomConjointTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nomConjointTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(telConjointTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(telDomicileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(telPortableTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(219, 219, 219)
                .addComponent(enregistrerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(prenomTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(nomTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(naissancePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lieuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(sexeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(civiliteCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(professionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(numDossierTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(telPortableTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(telDomicileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(telBureauTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(prenomConjointTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(nomConjointTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(telConjointTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(professionConjointCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(enregistrerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>                                                                        

    private void enregistrerBtnActionPerformed(java.awt.event.ActionEvent evt) {                                               
        updatePatient();
    }                                              

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        openListePatients();
    }                                        

    private void lieuTxtActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void telBureauTxtActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void prenomConjointTxtActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

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
