/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import dao.PatientDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.jboss.logging.Message;

import models.Patient;

/**
 *
 * @author Baye Lahad DIAGNE
 */
public class ListePatients extends javax.swing.JInternalFrame {

    private final PatientDAO patientDAO;
    private Integer idPatientSelected;
    private final DefaultTableModel model;
    private final String[] entete = new String[]{"ID", "Prénom", "Nom", "Date de naissance", "Civilité", "Suivi depuis"};

    private void remplirTablePatients() {
        this.model.setRowCount(0);
        this.model.setColumnIdentifiers(this.entete);
        List<Patient> patients = patientDAO.findAll();
        for (int i = 0; i < patients.size(); i++) {
            this.model.addRow(new Object[]{
                patients.get(i).getId(),
                patients.get(i).getPrenom(),
                patients.get(i).getNom(),
                patients.get(i).getDateNaissance(),
                patients.get(i).getCivilite().getName(),
                patients.get(i).getConnuDepuis()
            });
        }
        this.jTable1.setModel(this.model);
        this.jTable1.setEnabled(true);
        /*
        Je cache l'id
         */
        TableColumnModel tcm = this.jTable1.getColumnModel();
        tcm.removeColumn(tcm.getColumn(0));
    }

    /**
     * Creates new form ListePatients
     */
    public ListePatients() {
        initComponents();
        this.model = new DefaultTableModel();
        this.patientDAO = new PatientDAO();
        this.remplirTablePatients();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        ajouterBtn = new javax.swing.JToggleButton();
        modifierBtn = new javax.swing.JToggleButton();
        supprimerBtn = new javax.swing.JToggleButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Liste des Patients");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        ajouterBtn.setText("AJOUTER");
        ajouterBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajouterBtnActionPerformed(evt);
            }
        });

        modifierBtn.setText("MODIFIER");
        modifierBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifierBtnActionPerformed(evt);
            }
        });

        supprimerBtn.setText("SUPPRIMER");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ajouterBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(modifierBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(supprimerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ajouterBtn)
                        .addGap(18, 18, 18)
                        .addComponent(modifierBtn)
                        .addGap(18, 18, 18)
                        .addComponent(supprimerBtn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void modifierBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifierBtnActionPerformed
        if (idPatientSelected == null) {
            JOptionPane.showMessageDialog(null, "VEUILLEZ SELECTIONNER UN PATIENT", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            Patient p = patientDAO.findById(idPatientSelected);
            EditPatient editPatientForm = new EditPatient(p);
            this.getDesktopPane().add(editPatientForm).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_modifierBtnActionPerformed

    private void ajouterBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajouterBtnActionPerformed
        AddPatient addPatientForm = new AddPatient();
        this.getDesktopPane().add(addPatientForm).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ajouterBtnActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        int selectedRowIndex = jTable1.getSelectedRow();
        idPatientSelected = (Integer) dtm.getValueAt(selectedRowIndex, 0);
    }//GEN-LAST:event_jTable1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton ajouterBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToggleButton modifierBtn;
    private javax.swing.JToggleButton supprimerBtn;
    // End of variables declaration//GEN-END:variables
}
