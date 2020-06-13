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

import dao.AntecedantDAO;
import dao.ClasseAntecedentDAO;
import javax.swing.RowFilter;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import models.Antecedent;
import models.ClasseAntecedent;

/**
 *
 * @author A663588
 */
public class GestionAntecedant extends javax.swing.JInternalFrame {

    /**
     * Creates new form GestionAntecedant
     */
    DefaultTableModel model = new DefaultTableModel();

    Long idAntecedantModif;

    Long idClasseAntecedant;

    String libeleModif = null;

    boolean reset = false;

    public void remplirTableANTECEDANT() {
        try {
            model.setRowCount(0);
            AntecedantDAO dao = new AntecedantDAO();

            model.setColumnIdentifiers(new String[]{"ID", "LIBELLE", "CLASSE ANTECEDANT", "CIM10"});

            List<Antecedent> antecedents = dao.getAllAntecedent();
            if (antecedents.size() > 0) {

                for (int i = 0; i < antecedents.size(); i++) {
                    model.addRow(new String[]{antecedents.get(i).getId().toString(), antecedents.get(i).getLibelle(), antecedents.get(i).getClasse().getLibelle(), antecedents.get(i).getCim10()});
                }
                tableAntecedant.setModel(model);
                tableAntecedant.setEnabled(false);
            } else {
                tableAntecedant.setEnabled(true);
            }
            
            TableColumnModel tcm = tableAntecedant.getColumnModel();
            tcm.removeColumn(tcm.getColumn(0));
            tableAntecedant.setAutoCreateRowSorter(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void checkChoixSelect(String choixAction) {
        try {
            if (choixAction.equalsIgnoreCase("")) {
                desactiverChamps();
            } else if (choixAction.equalsIgnoreCase("AJOUTER  ANTECEDANT")) {

                activerChamps();
                choixBTX.setText("AJOUTER  ANTECEDANT");

            } else if (choixAction.equalsIgnoreCase("MODIFIER  ANTECEDANT")) {

                activerChamps();
                choixBTX.setText("MODIFIER  ANTECEDANT");

            } else if (choixAction.equalsIgnoreCase("SUPPRIMER  ANTECEDANT")) {
                activerChamps();
                choixBTX.setText("SUPPRIMER  ANTECEDANT");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ClasseAntecedent> listItemClasseAntecedant() {
        try {

            ClasseAntecedentDAO dao = new ClasseAntecedentDAO();
            ArrayList<ClasseAntecedent> list = dao.getAllClasseAntecedent();
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

    public void remplirComboClasseAntecedant() {
        ComboClasseAntecedant.removeAll();
        try {
            ComboClasseAntecedant.addItem("");
            for (int i = 0; i < listItemClasseAntecedant().size(); i++) {
                ComboClasseAntecedant.addItem(listItemClasseAntecedant().get(i).getLibelle());
                idClasseAntecedant = listItemClasseAntecedant().get(i).getId();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            list.add("AJOUTER  ANTECEDANT");
            list.add("MODIFIER  ANTECEDANT");
            list.add("SUPPRIMER  ANTECEDANT");
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
        libelleAn.setVisible(true);
        cim10.setVisible(true);
        labelClasseAnt.setVisible(true);
        labelLibelle.setVisible(true);
        labelcm10.setVisible(true);
        choixBTX.setVisible(true);
        ComboClasseAntecedant.setVisible(true);
        tableAntecedant.setEnabled(true);

    }

    public void desactiverChamps() {
        libelleAn.setVisible(false);
        cim10.setVisible(false);
        labelClasseAnt.setVisible(false);
        labelLibelle.setVisible(false);
        labelcm10.setVisible(false);
        choixBTX.setVisible(false);
        ComboClasseAntecedant.setVisible(false);
        tableAntecedant.setEnabled(false);

    }

    public void viderChamps() {
        libelleAn.setText("");
        cim10.setText("");
        ComboClasseAntecedant.setSelectedIndex(-1);

    }

    private void refrechALL() {
        desactiverChamps();
        remplirComboChoix();
        remplirComboClasseAntecedant();
        model.setRowCount(1);
        remplirTableANTECEDANT();
    }

    public boolean verifChamps() {
        if (libelleAn.getText().equals("") || cim10.getText().equals("") || ComboClasseAntecedant.getSelectedItem().equals("") || ComboChoix.getSelectedItem().equals("")) {
            System.out.println("CHAMPS VIDE");
            return false;
        } else {
            System.out.println("CHAMPS NON VIDE");
            return true;
        }

    }

    public GestionAntecedant() {
        initComponents();
        refrechALL();
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
        libelleAn = new javax.swing.JTextField();
        ComboClasseAntecedant = new javax.swing.JComboBox<>();
        cim10 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        ComboChoix = new javax.swing.JComboBox<>();
        choixBTX = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAntecedant = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        searchTxt = new javax.swing.JTextField();
        showAllBtn = new javax.swing.JToggleButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Liste des antécedents");

        labelLibelle.setText("Liibelle");

        labelClasseAnt.setText("Classe Antecedant");

        labelcm10.setText("CIM10");

        ComboClasseAntecedant.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboClasseAntecedantItemStateChanged(evt);
            }
        });

        jLabel1.setText("Choix Action ");

        ComboChoix.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboChoixItemStateChanged(evt);
            }
        });

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
                        .addContainerGap()
                        .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PanelDonnerLayout.createSequentialGroup()
                                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelLibelle, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelClasseAnt)
                                    .addComponent(labelcm10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(libelleAn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cim10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ComboClasseAntecedant, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PanelDonnerLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(ComboChoix, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PanelDonnerLayout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(choixBTX, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        PanelDonnerLayout.setVerticalGroup(
            PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDonnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(ComboChoix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLibelle)
                    .addComponent(libelleAn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelClasseAnt)
                    .addComponent(ComboClasseAntecedant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(PanelDonnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelcm10)
                    .addComponent(cim10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addComponent(choixBTX)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        tableAntecedant.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableAntecedant);

        jLabel2.setText("Rechercher : ");

        searchTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTxtActionPerformed(evt);
            }
        });
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchTxtKeyTyped(evt);
            }
        });

        showAllBtn.setText("Tout afficher");
        showAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(PanelDonner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(showAllBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PanelDonner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(showAllBtn))
                        .addGap(29, 29, 29))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTxtActionPerformed
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel)tableAntecedant.getModel());
        sorter.setRowFilter(RowFilter.regexFilter(searchTxt.getText()));
        tableAntecedant.setRowSorter(sorter);
    }//GEN-LAST:event_searchTxtActionPerformed

    private void showAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllBtnActionPerformed
        searchTxt.setText("");
        this.remplirTableANTECEDANT();
    }//GEN-LAST:event_showAllBtnActionPerformed

    private void searchTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyTyped
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel)tableAntecedant.getModel());
        sorter.setRowFilter(RowFilter.regexFilter(searchTxt.getText()));
        tableAntecedant.setRowSorter(sorter);
    }//GEN-LAST:event_searchTxtKeyTyped

    private void ComboClasseAntecedantItemStateChanged(java.awt.event.ItemEvent evt) {

    }

    private void ComboChoixItemStateChanged(java.awt.event.ItemEvent evt) {
        checkChoixSelect(ComboChoix.getSelectedItem().toString());
    }

    private void choixBTXActionPerformed(java.awt.event.ActionEvent evt) {

        // Ajout Antecedant 
        if (choixBTX.getText().equalsIgnoreCase("AJOUTER  ANTECEDANT")) {

            if (verifChamps()) {

                AntecedantDAO dao = new AntecedantDAO();
                Antecedent antecedentCheck = dao.findAntecedant(libelleAn.getText());
                if (antecedentCheck == null) {
                    Antecedent antecedentnew = new Antecedent();
                    antecedentnew.setLibelle(libelleAn.getText());
                    antecedentnew.setCim10(cim10.getText());
                    ClasseAntecedentDAO classeAntecedantDAO = new ClasseAntecedentDAO();
                    ClasseAntecedent lib = classeAntecedantDAO.findClasseAntecedant(ComboClasseAntecedant.getSelectedItem().toString());
                    idClasseAntecedant = lib.getId();
                    JOptionPane.showMessageDialog(null, idClasseAntecedant, "GESTION  ANTECEDANT", JOptionPane.ERROR_MESSAGE);
                    antecedentnew.setClasse(lib);
                    if (dao.saveAntecedant(antecedentnew)) {
                        JOptionPane.showMessageDialog(null, "ANTECEDANT AJOUTER AVEC SUCCESS", "GESTION  ANTECEDANT", JOptionPane.INFORMATION_MESSAGE);
                        model.setRowCount(0);
                        remplirTableANTECEDANT();
                        viderChamps();
                        reset = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "ECHEC AJOUT  ANTECEANT", "GESTION  ANTECEDANT", JOptionPane.ERROR_MESSAGE);
                        model.setRowCount(0);
                        remplirTableANTECEDANT();
                        viderChamps();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "CETTE   ANTECEDANT EXISTE DEJA MERCI !", "GESTION  ANTECEDANT", JOptionPane.ERROR_MESSAGE);
                    model.setRowCount(0);
                    remplirTableANTECEDANT();
                    viderChamps();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Tous les champs sont obligatoire Merci !", "GESTION  ANTECEDANT", JOptionPane.ERROR_MESSAGE);
                model.setRowCount(0);
                ComboClasseAntecedant.setSelectedItem("");
                remplirTableANTECEDANT();
                viderChamps();
            }
        }

        // Modifier Antecedant 	 
        if (choixBTX.getText().equalsIgnoreCase("MODIFIER  ANTECEDANT")) {
            if (verifChamps()) {

                AntecedantDAO antecedantDAO = new AntecedantDAO();
                Antecedent antecedentmodif = new Antecedent();

                ClasseAntecedentDAO classeAntecedantDAO = new ClasseAntecedentDAO();
                ClasseAntecedent classeAntecedent = new ClasseAntecedent();

                antecedentmodif.setId(idAntecedantModif);
                antecedentmodif.setCim10(cim10.getText());
                antecedentmodif.setLibelle(libelleAn.getText());
                antecedentmodif.setClasse(classeAntecedantDAO.findClasseAntecedant(ComboClasseAntecedant.getSelectedItem().toString()));

                if (antecedantDAO.updateAntecedent(antecedentmodif)) {

                    JOptionPane.showMessageDialog(null, "ANTECEDANT MODIFER AVEC SUCCESS", "GESTION  ANTECEDANT" + cim10.getText(), JOptionPane.INFORMATION_MESSAGE);
                    viderChamps();
                    reset = true;
                    model.setRowCount(0);
                    remplirTableANTECEDANT();

                } else {

                    JOptionPane.showMessageDialog(null, "ECHEC MODIFICATION  ANTECEANT", "GESTION  ANTECEDANT", JOptionPane.ERROR_MESSAGE);
                    model.setRowCount(0);
                    remplirTableANTECEDANT();
                    viderChamps();

                }

            } else {
                JOptionPane.showMessageDialog(null, "Tous les champs sont obligatoire Merci !", "GESTION  ANTECEDANT", JOptionPane.ERROR_MESSAGE);
                model.setRowCount(0);
                ComboClasseAntecedant.setSelectedItem("");
                remplirTableANTECEDANT();
                viderChamps();
            }
        }

        // Supression antecedant
        if (choixBTX.getText().equalsIgnoreCase("SUPPRIMER  ANTECEDANT")) {
            if (verifChamps()) {
                AntecedantDAO antecedantDAO = new AntecedantDAO();
                Antecedent antecedentmodif = new Antecedent();

                ClasseAntecedentDAO classeAntecedantDAO = new ClasseAntecedentDAO();
                ClasseAntecedent classeAntecedent = new ClasseAntecedent();

                antecedentmodif.setId(idAntecedantModif);
                antecedentmodif.setCim10(cim10.getText());
                antecedentmodif.setLibelle(libelleAn.getText());
                antecedentmodif.setClasse(classeAntecedantDAO.findClasseAntecedant(ComboClasseAntecedant.getSelectedItem().toString()));

                if (antecedantDAO.deleteAntecedent(antecedentmodif)) {

                    JOptionPane.showMessageDialog(null, "ANTECEDANT SUPPRESSION AVEC SUCCESS", "GESTION  ANTECEDANT" + cim10.getText(), JOptionPane.INFORMATION_MESSAGE);
                    viderChamps();
                    reset = true;
                    model.setRowCount(0);
                    remplirTableANTECEDANT();

                } else {

                    JOptionPane.showMessageDialog(null, "ECHEC SUPPRESSION  ANTECEANT", "GESTION  ANTECEDANT", JOptionPane.ERROR_MESSAGE);
                    model.setRowCount(0);
                    remplirTableANTECEDANT();
                    viderChamps();

                }
            } else {
                JOptionPane.showMessageDialog(null, "Tous les champs sont obligatoire Merci !", "GESTION  ANTECEDANT", JOptionPane.ERROR_MESSAGE);
                model.setRowCount(0);
                ComboClasseAntecedant.setSelectedItem("");
                remplirTableANTECEDANT();
                viderChamps();
            }
        }

    }

    private void tableAntecedantMouseClicked(java.awt.event.MouseEvent evt) {
        if (reset == true) {
            if (tableAntecedant.getSelectedRow() == -1) {
                return;
            } else {
                JOptionPane.showMessageDialog(null, "Merci de refaire le choix d'action (AJOUTER / MODIFER / SUPPRIMER )", "GESTION PROFESSION", JOptionPane.ERROR_MESSAGE);
                //desactiverChamps();
                tableAntecedant.setEnabled(false);
                reset = false;
            }

        } else {
            if (ComboChoix.getSelectedItem().toString().equalsIgnoreCase("MODIFIER  ANTECEDANT") || ComboChoix.getSelectedItem().toString().equalsIgnoreCase("SUPPRIMER  ANTECEDANT")) {

                DefaultTableModel defaultTableModel = (DefaultTableModel) tableAntecedant.getModel();
                int selectRowsIndex = tableAntecedant.getSelectedRow();

                AntecedantDAO antecedantDAO = new AntecedantDAO();
                Antecedent antecedent = antecedantDAO.findAntecedant(defaultTableModel.getValueAt(selectRowsIndex, 1).toString());

                //JOptionPane.showMessageDialog (null, antecedent.getClasse().getId() , "TEST", JOptionPane.ERROR_MESSAGE);
                if (antecedent != null) {
                    ClasseAntecedentDAO dao = new ClasseAntecedentDAO();
                    ClasseAntecedent classeAntecedent = dao.findClAntById(antecedent.getClasse().getId());
                    libelleAn.setText(antecedent.getLibelle());
                    ComboClasseAntecedant.setSelectedItem(classeAntecedent.getLibelle());
                    cim10.setText(antecedent.getCim10());
                    idAntecedantModif = antecedent.getId();

                } else {
                    JOptionPane.showMessageDialog(null, "ANTECEDANT " + antecedent.getId() + "N'EXISTE PAS", "GESTION PROFESSION", JOptionPane.ERROR_MESSAGE);
                }

            }
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboChoix;
    private javax.swing.JComboBox<String> ComboClasseAntecedant;
    private javax.swing.JPanel PanelDonner;
    private javax.swing.JButton choixBTX;
    private javax.swing.JTextField cim10;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelClasseAnt;
    private javax.swing.JLabel labelLibelle;
    private javax.swing.JLabel labelcm10;
    private javax.swing.JTextField libelleAn;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JToggleButton showAllBtn;
    private javax.swing.JTable tableAntecedant;
    // End of variables declaration//GEN-END:variables
}
