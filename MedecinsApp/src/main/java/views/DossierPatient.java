/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import dao.AntecedantDAO;
import dao.ClasseAntecedentDAO;
import dao.PatientAntecedentDAO;
import dao.PatientDAO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import models.Antecedent;
import models.ClasseAntecedent;
import models.Patient;
import models.PatientAntecedent;
import org.hibernate.Hibernate;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Baye Lahad DIAGNE
 */
public class DossierPatient extends javax.swing.JInternalFrame {

    private Patient patient;
    private final Integer ID_PATIENT;
    private final PatientDAO patientDAO;
    private final ClasseAntecedentDAO classeAntecedentDAO;
    private final AntecedantDAO antecedentDAO;
    private final PatientAntecedentDAO paDAO;
    private DefaultTreeModel myTreeModel;

    public class TableauAntecedentsModel extends DefaultTableModel {

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class clazz = String.class;
            switch (columnIndex) {
                case 0:
                    clazz = String.class;
                    break;
                case 1:
                    clazz = Boolean.class;
                    break;
                case 2:
                    clazz = Long.class;
                    break;
            }
            return clazz;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1;
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            if (aValue instanceof Boolean && column == 1) {
                System.out.println(aValue);
                Vector rowData = (Vector) getDataVector().get(row);
                rowData.set(1, (boolean) aValue);
                fireTableCellUpdated(row, column);
            }
        }
    }

    /**
     * Creates new form DossierPatient
     *
     * @param idPatient
     */
    public DossierPatient(Integer idPatient) {
        initComponents();
        ID_PATIENT = idPatient;
        patientDAO = new PatientDAO();
        classeAntecedentDAO = new ClasseAntecedentDAO();
        antecedentDAO = new AntecedantDAO();
        paDAO = new PatientAntecedentDAO();
        patient = patientDAO.findById(idPatient);
        Hibernate.initialize(patient.getPatientAntecedentListe());
        remplirSignaletique();
        this.setTitle(patient.getPrenom() + " " + patient.getNom());
        remplirComboClassesAntecedent();
        remplirTree();
    }

    private void remplirTree() {
        myTreeModel = null;
        antecedentsTree.removeAll();
        DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Antécédents de " + patient.getPrenom() + " " + patient.getNom());
        //replace with the get
        List<PatientAntecedent> pas = patient.getPatientAntecedentListe();
        List<ClasseAntecedent> lca = paDAO.getClassesFrom(pas);
        for (ClasseAntecedent ca : lca) {
            DefaultMutableTreeNode classeNode = new DefaultMutableTreeNode(ca.getLibelle());
            racine.add(classeNode);
            for (PatientAntecedent pa : pas) {
                if (pa.getAntecedent().getClasse().getId().equals(ca.getId())) {
                    DefaultMutableTreeNode antecedentNode = new DefaultMutableTreeNode(pa.getAntecedent().getLibelle());
                    classeNode.add(antecedentNode);
                }
            }
        }
        myTreeModel = new DefaultTreeModel(racine);
        antecedentsTree.setModel(myTreeModel);

        /*Ouvrir tous les noeuds (dossier)*/
        for (int i = 0; i < antecedentsTree.getRowCount(); i++) {
            antecedentsTree.expandRow(i);
        }
    }

    private void remplirComboClassesAntecedent() {
        try {
            List<ClasseAntecedent> classes = classeAntecedentDAO.getAllClasseAntecedent();
            comboClasses.removeAll();
            comboClasses.addItem("");
            for (ClasseAntecedent c : classes) {
                comboClasses.addItem(c.getLibelle());
            }
            AutoCompleteDecorator.decorate(comboClasses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PatientAntecedent findAntecedentInPatient(Long idAntecedent) {
        for (int i = 0; i < this.patient.getPatientAntecedentListe().size(); i++) {
            if (this.patient.getPatientAntecedentListe().get(i).getAntecedent().getId().equals(idAntecedent)) {
                return this.patient.getPatientAntecedentListe().get(i);
            }
        }
        return null;
    }

    private void remplirTableauAntecedents(String libelleClasse) {
        try {
            TableauAntecedentsModel model = new TableauAntecedentsModel();
            model.setRowCount(0);
            model.setColumnIdentifiers(new String[]{"Nom de l'antécédent", "Selectionné", "ID"});
            ArrayList<Antecedent> antecedents = antecedentDAO.findByClasseName(libelleClasse);
            for (Antecedent a : antecedents) {
                Boolean checkBox = paDAO.findByPatientAndAntecedent(patient.getId(), a.getId()) != null;
                model.addRow(new Object[]{a.getLibelle(), checkBox, a.getId()});
            }
            tableAntecedentsPatient.setModel(model);
            TableColumnModel tcm = tableAntecedentsPatient.getColumnModel();
            tcm.removeColumn(tcm.getColumn(2));
        } catch (Exception e) {
        }
    }

    private void remplirSignaletique() {
        //Replace all empty labels with NEANT
        //Add suivi depuis et nom du médecin traitant
        //Reouvrir liste on close
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY");
        prenomLabel.setText(patient.getPrenom());
        nomLabel.setText(patient.getNom());
        dateNaissanceLabel.setText(dateFormat.format(patient.getDateNaissance()));
        lieuNaissanceLabel.setText(patient.getLieuNaissance());
        sexeLabel.setText(patient.getSexe().getName());
        civiliteLabel.setText(patient.getCivilite().getName());
        professionLabel.setText(patient.getProfession() == null ? "NEANT" : patient.getProfession().getLibelle());
        numeroDossierLabel.setText(patient.getNumeroDossier());
        adresseLabel.setText(patient.getAdresse());
        telPortableLabel.setText(patient.getTelephonePortable());
        telDomicileLabel.setText(patient.getTelephoneDomicile());
        telBureauLabel.setText(patient.getTelephoneBureau());
        prenomConjointLabel.setText(patient.getPrenomConjoint());
        nomConjointLabel.setText(patient.getNomConjoint());
        telConjointLabel.setText(patient.getTelephoneConjoint());
        professionConjointLabel.setText(patient.getProfessionConjoint() == null ? "Neant" : patient.getProfessionConjoint().getLibelle());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
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
        prenomLabel = new javax.swing.JLabel();
        dateNaissanceLabel = new javax.swing.JLabel();
        nomLabel = new javax.swing.JLabel();
        lieuNaissanceLabel = new javax.swing.JLabel();
        sexeLabel = new javax.swing.JLabel();
        civiliteLabel = new javax.swing.JLabel();
        professionLabel = new javax.swing.JLabel();
        numeroDossierLabel = new javax.swing.JLabel();
        adresseLabel = new javax.swing.JLabel();
        telPortableLabel = new javax.swing.JLabel();
        telDomicileLabel = new javax.swing.JLabel();
        telBureauLabel = new javax.swing.JLabel();
        prenomConjointLabel = new javax.swing.JLabel();
        nomConjointLabel = new javax.swing.JLabel();
        professionConjointLabel = new javax.swing.JLabel();
        telConjointLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        antecedentsTree = new javax.swing.JTree();
        comboClasses = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableAntecedentsPatient = new javax.swing.JTable();
        updateAntecedantsBtn = new javax.swing.JToggleButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        imprimerBtn = new javax.swing.JToggleButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);

        jLabel1.setText("Prénom :");

        jLabel2.setText("Nom :");

        jLabel3.setText("Né(e) le :");

        jLabel4.setText("À :");

        jLabel5.setText("Sexe :");

        jLabel6.setText("Civilité :");

        jLabel7.setText("Profession :");

        jLabel8.setText("No de dossier :");

        jLabel9.setText("Adresse : ");

        jLabel10.setText("Téléphone (port.) : ");

        jLabel11.setText("Téléphone (dom.) :");

        jLabel12.setText("Téléphone (bur.) :");

        jLabel13.setText("Prénom du conjoint :");

        jLabel14.setText("Nom du conjoint :");

        jLabel15.setText("Profession du conjoint :");

        jLabel16.setText("Téléphone du conjoint :");

        prenomLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        prenomLabel.setText("jLabel17");

        dateNaissanceLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dateNaissanceLabel.setText("jLabel17");

        nomLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nomLabel.setText("jLabel17");

        lieuNaissanceLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lieuNaissanceLabel.setText("jLabel17");

        sexeLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sexeLabel.setText("jLabel17");

        civiliteLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        civiliteLabel.setText("jLabel17");

        professionLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        professionLabel.setText("jLabel17");

        numeroDossierLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        numeroDossierLabel.setText("jLabel17");

        adresseLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adresseLabel.setText("jLabel17");

        telPortableLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        telPortableLabel.setText("jLabel17");

        telDomicileLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        telDomicileLabel.setText("jLabel17");

        telBureauLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        telBureauLabel.setText("jLabel17");

        prenomConjointLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        prenomConjointLabel.setText("jLabel17");

        nomConjointLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nomConjointLabel.setText("jLabel17");

        professionConjointLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        professionConjointLabel.setText("jLabel17");

        telConjointLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        telConjointLabel.setText("jLabel17");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(66, 66, 66)
                        .addComponent(dateNaissanceLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(104, 104, 104)
                        .addComponent(lieuNaissanceLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(84, 84, 84)
                        .addComponent(sexeLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(75, 75, 75)
                        .addComponent(civiliteLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(53, 53, 53)
                        .addComponent(professionLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(34, 34, 34)
                        .addComponent(numeroDossierLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(68, 68, 68)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nomLabel)
                            .addComponent(prenomLabel))))
                .addGap(150, 150, 150)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(telConjointLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(professionConjointLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nomConjointLabel)
                            .addComponent(prenomConjointLabel)
                            .addComponent(telBureauLabel)
                            .addComponent(telDomicileLabel)
                            .addComponent(telPortableLabel)
                            .addComponent(adresseLabel))))
                .addContainerGap(166, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel9)
                    .addComponent(prenomLabel)
                    .addComponent(adresseLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel10)
                    .addComponent(nomLabel)
                    .addComponent(telPortableLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel11)
                    .addComponent(dateNaissanceLabel)
                    .addComponent(telDomicileLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel12)
                    .addComponent(lieuNaissanceLabel)
                    .addComponent(telBureauLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel13)
                    .addComponent(sexeLabel)
                    .addComponent(prenomConjointLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel14)
                    .addComponent(civiliteLabel)
                    .addComponent(nomConjointLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel15)
                    .addComponent(professionLabel)
                    .addComponent(professionConjointLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel16)
                    .addComponent(numeroDossierLabel)
                    .addComponent(telConjointLabel))
                .addContainerGap(89, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Signalétique", jPanel1);

        antecedentsTree.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        antecedentsTree.setModel(this.myTreeModel);
        antecedentsTree.setFocusable(false);
        jScrollPane1.setViewportView(antecedentsTree);

        comboClasses.setEditable(true);
        comboClasses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboClassesActionPerformed(evt);
            }
        });

        jLabel17.setText("Classe de l'antécédent");

        jScrollPane2.setMaximumSize(new java.awt.Dimension(32767, 10000));

        tableAntecedentsPatient.setRowSelectionAllowed(false);
        jScrollPane2.setViewportView(tableAntecedentsPatient);

        updateAntecedantsBtn.setText("Mettre à jour les éléments sélectionnés");
        updateAntecedantsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateAntecedantsBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(comboClasses, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(updateAntecedantsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboClasses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(updateAntecedantsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(169, 169, 169))
        );

        jTabbedPane1.addTab("Antécédents", jPanel2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 721, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 364, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Ordonnances", jPanel4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 721, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 364, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Consultations", jPanel3);

        imprimerBtn.setText("IMPRIMER");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(imprimerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imprimerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboClassesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboClassesActionPerformed
        remplirTableauAntecedents((String) comboClasses.getSelectedItem());
    }//GEN-LAST:event_comboClassesActionPerformed

    private void updateAntecedantsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateAntecedantsBtnActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) tableAntecedentsPatient.getModel();
        if (dtm.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "AUCUNE VALEUR SELECTIONNEE", "PATIENT", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < dtm.getRowCount(); i++) {
            Long idAntecedent = (Long) dtm.getValueAt(i, 2);
            PatientAntecedent patAnt = this.findAntecedentInPatient(idAntecedent);
            if ((Boolean) dtm.getValueAt(i, 1) && patAnt == null) {
                patAnt = new PatientAntecedent();
                //patAnt.setId(paDAO.newID());
                patAnt.setPatient(this.patient);
                Antecedent ant = antecedentDAO.findById(idAntecedent);
                patAnt.setAntecedent(ant);
                patAnt.setCommentaire("");
                patAnt.setDateEntree(new Date());
                this.patient.addPatientAntecedent(patAnt);
            } else if (!(Boolean) dtm.getValueAt(i, 1) && patAnt != null) {
                this.patient.removePatientAntecedent(patAnt);
            }
        }

        if (patientDAO.edit(patient)) {
            this.patient = patientDAO.findById(this.ID_PATIENT);
            this.remplirTree();
            JOptionPane.showMessageDialog(null, "ANTECEDENTS MIS A JOUR", "PATIENT", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "ECHEC DE LA MISE A JOUR", "PATIENT", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_updateAntecedantsBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adresseLabel;
    private javax.swing.JTree antecedentsTree;
    private javax.swing.JLabel civiliteLabel;
    private javax.swing.JComboBox<String> comboClasses;
    private javax.swing.JLabel dateNaissanceLabel;
    private javax.swing.JToggleButton imprimerBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lieuNaissanceLabel;
    private javax.swing.JLabel nomConjointLabel;
    private javax.swing.JLabel nomLabel;
    private javax.swing.JLabel numeroDossierLabel;
    private javax.swing.JLabel prenomConjointLabel;
    private javax.swing.JLabel prenomLabel;
    private javax.swing.JLabel professionConjointLabel;
    private javax.swing.JLabel professionLabel;
    private javax.swing.JLabel sexeLabel;
    private javax.swing.JTable tableAntecedentsPatient;
    private javax.swing.JLabel telBureauLabel;
    private javax.swing.JLabel telConjointLabel;
    private javax.swing.JLabel telDomicileLabel;
    private javax.swing.JLabel telPortableLabel;
    private javax.swing.JToggleButton updateAntecedantsBtn;
    // End of variables declaration//GEN-END:variables
}
