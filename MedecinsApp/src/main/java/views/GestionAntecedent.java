/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.AntecedentDAO;
import dao.ClasseAntecedentDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import models.Antecedent;
import models.ClasseAntecedent;

/**
 *
 * @author A663588
 */
public class GestionAntecedent extends javax.swing.JInternalFrame {

    /**
     * Creates new form GestionAntecedent
     */
    private final String AJOUTER = "AJOUTER ANTECEDENT";
    private final String MODIFIER = "MODIFIER ANTECEDENT";
    private final String SUPPRIMER = "SUPPRIMER ANTECEDENT";
    private final AntecedentDAO antecedentDAO;
    private final ClasseAntecedentDAO classeAntecedentDAO;
    private final List<ClasseAntecedent> classesA;

    Long idAntecedantModif;
    private Long selectedTableID;
    String libeleModif = null;
    private static final Logger LOGGER = Logger.getLogger(GestionAntecedent.class.getName());

    public void remplirTableAntecedent() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.setRowCount(0);
            AntecedentDAO dao = new AntecedentDAO();
            model.setColumnIdentifiers(new String[]{"ID", "NOM DE L'ANTECEDENT", "CLASSE", "CIM10"});

            List<Antecedent> antecedents = dao.findAll();
            if (antecedents.size() > 0) {

                for (int i = 0; i < antecedents.size(); i++) {
                    model.addRow(new Object[]{antecedents.get(i).getId(), antecedents.get(i).getLibelle(), antecedents.get(i).getClasse().getLibelle(), antecedents.get(i).getCim10()});
                }
                tableAntecedent.setModel(model);
            } 
            /*
        HIDE IDs
             */
            tableAntecedent.getColumnModel().getColumn(0).setMinWidth(0);
            tableAntecedent.getColumnModel().getColumn(0).setMaxWidth(0);
            tableAntecedent.getColumnModel().getColumn(0).setWidth(0);
            tableAntecedent.setAutoCreateRowSorter(true);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public void checkChoixSelect() {
        String choixAction = comboChoix.getSelectedItem().toString();
        try {
            if (choixAction.isEmpty()) {
                desactiverChamps();
            } else if (choixAction.equalsIgnoreCase(AJOUTER)) {
                activerChamps();
                choixBTX.setText(AJOUTER);
            } else if (choixAction.equalsIgnoreCase(MODIFIER)) {
                activerChamps();
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

    public void remplirComboClasseAntecedant() {
        comboClasseAntecedent.removeAll();
        try {
            comboClasseAntecedent.addItem("");
            for (int i = 0; i < classesA.size(); i++) {
                comboClasseAntecedent.addItem(classesA.get(i).getLibelle());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void remplirComboChoix() {
        comboChoix.removeAll();
        comboChoix.addItem("");
        comboChoix.addItem(AJOUTER);
        comboChoix.addItem(MODIFIER);
        comboChoix.addItem(SUPPRIMER);
    }

    public void activerChamps() {
        libelleAntecedentTxt.setEnabled(true);
        cim10Txt.setEnabled(true);
        choixBTX.setEnabled(true);
        comboClasseAntecedent.setEnabled(true);
    }

    public void desactiverChamps() {
        libelleAntecedentTxt.setEnabled(false);
        cim10Txt.setEnabled(false);
        choixBTX.setEnabled(false);
        comboClasseAntecedent.setEnabled(false);
    }

    public void viderChamps() {
        libelleAntecedentTxt.setText("");
        cim10Txt.setText("");
        comboClasseAntecedent.setSelectedIndex(0);
    }

    private void refreshAll() {
        desactiverChamps();
        remplirComboClasseAntecedant();
        remplirComboChoix();
        remplirTableAntecedent();
    }

    public boolean verifChamps() {
        return !(libelleAntecedentTxt.getText().equals("") || comboClasseAntecedent.getSelectedItem().equals(""));
    }

    public GestionAntecedent() {
        antecedentDAO = new AntecedentDAO();
        classeAntecedentDAO = new ClasseAntecedentDAO();
        classesA = classeAntecedentDAO.findAll();
        initComponents();
        refreshAll();
        choixBTX.setEnabled(false);
        choixBTX.setText("Aucune action");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelDonner = new javax.swing.JPanel();
        labelLibelle = new javax.swing.JLabel();
        labelClasseAnt = new javax.swing.JLabel();
        labelcm10 = new javax.swing.JLabel();
        libelleAntecedentTxt = new javax.swing.JTextField();
        comboClasseAntecedent = new javax.swing.JComboBox<>();
        cim10Txt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        comboChoix = new javax.swing.JComboBox<>();
        choixBTX = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableAntecedent = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        searchTxt = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Liste des antécedents");

        labelLibelle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelLibelle.setText("Libellé*");

        labelClasseAnt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelClasseAnt.setText("Classe Antécédent*");

        labelcm10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelcm10.setText("CIM10");

        libelleAntecedentTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        comboClasseAntecedent.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        cim10Txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Choix Action ");

        comboChoix.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboChoix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboChoixActionPerformed(evt);
            }
        });

        choixBTX.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        choixBTX.setText("jButton1");
        choixBTX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choixBTXActionPerformed(evt);
            }
        });

        tableAntecedent.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tableAntecedent.setModel(new javax.swing.table.DefaultTableModel(
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
        tableAntecedent.setRowHeight(25);
        tableAntecedent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableAntecedentMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableAntecedent);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Rechercher : ");

        searchTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTxtKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout PanelDonnerLayout = new javax.swing.GroupLayout(PanelDonner);
        PanelDonner.setLayout(PanelDonnerLayout);
        PanelDonnerLayout.setHorizontalGroup(
            PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDonnerLayout.createSequentialGroup()
                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDonnerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelDonnerLayout.createSequentialGroup()
                                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelcm10)
                                    .addComponent(labelClasseAnt)
                                    .addComponent(labelLibelle, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(59, 59, 59)
                                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(libelleAntecedentTxt)
                                    .addComponent(comboClasseAntecedent, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cim10Txt, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18))
                            .addGroup(PanelDonnerLayout.createSequentialGroup()
                                .addComponent(jSeparator1)
                                .addGap(26, 26, 26))
                            .addGroup(PanelDonnerLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                                .addComponent(comboChoix, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 18, Short.MAX_VALUE))))
                    .addGroup(PanelDonnerLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(choixBTX, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)))
                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDonnerLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelDonnerLayout.setVerticalGroup(
            PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDonnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDonnerLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34))
                    .addGroup(PanelDonnerLayout.createSequentialGroup()
                        .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(comboChoix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelLibelle)
                            .addComponent(libelleAntecedentTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboClasseAntecedent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelClasseAnt))
                        .addGap(18, 18, 18)
                        .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelcm10)
                            .addComponent(cim10Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(choixBTX)
                        .addGap(0, 161, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(PanelDonner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelDonner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(42, 42, 42))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tableAntecedentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAntecedentMouseClicked
        String action = (String) comboChoix.getSelectedItem();
        selectedTableID = null;
        DefaultTableModel defaultTableModel = (DefaultTableModel) tableAntecedent.getModel();
        int selectRowsIndex = tableAntecedent.getSelectedRow();

        if (action.isEmpty()) {
            tableAntecedent.clearSelection();
            JOptionPane.showMessageDialog(null, "Merci de choisir une action", "GESTION DES ANTECEDENTS", JOptionPane.ERROR_MESSAGE);
        } else if (action.equals(MODIFIER) || action.equals(SUPPRIMER)) {
            try {
                selectedTableID = (Long) tableAntecedent.getValueAt(selectRowsIndex, 0);
                Antecedent antecedent = antecedentDAO.findById(selectedTableID);
                if (antecedent != null) {
                    libelleAntecedentTxt.setText(antecedent.getLibelle());
                    comboClasseAntecedent.setSelectedItem(antecedent.getClasse().getLibelle());
                    cim10Txt.setText(antecedent.getCim10());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Champ selectionné invalide ! Veuillez réessayer", "GESTION DES ANTECEDENTS", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_tableAntecedentMouseClicked

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) tableAntecedent.getModel());
        sorter.setRowFilter(RowFilter.regexFilter(searchTxt.getText()));
        tableAntecedent.setRowSorter(sorter);
    }//GEN-LAST:event_searchTxtKeyReleased

    private void comboChoixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboChoixActionPerformed
        checkChoixSelect();
    }//GEN-LAST:event_comboChoixActionPerformed

    private void choixBTXActionPerformed(java.awt.event.ActionEvent evt) {
        if (comboChoix.getSelectedItem().toString().equalsIgnoreCase(AJOUTER)) {
            if (verifChamps()) {
                Antecedent antecedentCheck = antecedentDAO.findByLibelle(libelleAntecedentTxt.getText());
                if (antecedentCheck == null) {
                    Antecedent antecedentNew = new Antecedent();
                    antecedentNew.setCim10(cim10Txt.getText());
                    antecedentNew.setLibelle(libelleAntecedentTxt.getText());
                    ClasseAntecedent ca = classesA.get(comboClasseAntecedent.getSelectedIndex() - 1);
                    antecedentNew.setClasse(ca);
                    if (antecedentDAO.save(antecedentNew)) {
                        JOptionPane.showMessageDialog(null, "ANTECEDENT AJOUTE AVEC SUCCES", "GESTION DES ANTECEDENTS", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "ECHEC AJOUT DE L'ANTECEDENT !", "GESTION DES ANTECEDENTS", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "UN ANTECEDENT PORTE DEJA CE NOM", "GESTION DES ANTECEDENTS", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "REMPLISSEZ LES CHAMPS OBLIGATOIRES", "GESTION MEDECIN", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (comboChoix.getSelectedItem().toString().equalsIgnoreCase(MODIFIER)) {
            if (selectedTableID == null) {
                JOptionPane.showMessageDialog(null, "Champ sélectionné invalide ! Veuillez réessayer", "GESTION DES ANTECEDENTS", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                if (verifChamps()) {
                    Antecedent antecedentToUpdate = antecedentDAO.findById(selectedTableID);
                    if (antecedentToUpdate != null) {
                        antecedentToUpdate.setCim10(cim10Txt.getText());
                        antecedentToUpdate.setLibelle(libelleAntecedentTxt.getText());
                        ClasseAntecedent ca = classesA.get(comboClasseAntecedent.getSelectedIndex() - 1);
                        antecedentToUpdate.setClasse(ca);
                        if (antecedentDAO.update(antecedentToUpdate)) {
                            JOptionPane.showMessageDialog(null, "ANTECEDENT MODIFIE AVEC SUCCES", "GESTION DES ANTECEDENTS", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "ECHEC MODIFICATION DE L'ANTECEDENT ! ", "GESTION DES ANTECEDENTS", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "REMPLISSEZ LES CHAMPS OBLIGATOIRES", "GESTION DES ANTECEDENTS", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } else if (comboChoix.getSelectedItem().toString().equalsIgnoreCase(SUPPRIMER)) {
            if (selectedTableID == null) {
                JOptionPane.showMessageDialog(null, "Champ sélectionné invalide ! Veuillez réessayer", "GESTION DES ANTECEDENTS", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                Antecedent antecedentToDelete = antecedentDAO.findById(selectedTableID);
                if (antecedentToDelete != null) {
                    if (antecedentDAO.delete(antecedentToDelete)) {
                        JOptionPane.showMessageDialog(null, "ANTECEDENT SUPPRIME AVEC SUCCES", "GESTION DES ANTECEDENTS", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "ECHEC SUPPRESSION DE L'ANTECEDENT !", "GESTION DES ANTECEDENTS", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ANTECEDENT INTROUVABLE !", "GESTION DES ANTECEDENTS", JOptionPane.ERROR_MESSAGE);
                }

            }

        }

        remplirTableAntecedent();
        viderChamps();
        desactiverChamps();
        comboChoix.setSelectedIndex(0);
        choixBTX.setEnabled(false);
        choixBTX.setText("Aucune action");
        searchTxt.setText("");
        selectedTableID = null;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDonner;
    private javax.swing.JButton choixBTX;
    private javax.swing.JTextField cim10Txt;
    private javax.swing.JComboBox<String> comboChoix;
    private javax.swing.JComboBox<String> comboClasseAntecedent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelClasseAnt;
    private javax.swing.JLabel labelLibelle;
    private javax.swing.JLabel labelcm10;
    private javax.swing.JTextField libelleAntecedentTxt;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JTable tableAntecedent;
    // End of variables declaration//GEN-END:variables
}
