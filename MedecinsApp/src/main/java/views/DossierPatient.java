/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.itextpdf.text.DocumentException;
import dao.AntecedantDAO;
import dao.ClasseAntecedentDAO;
import dao.PatientAntecedentDAO;
import dao.PatientDAO;
import dao.TypeConsultationDAO;
import facture.DossierPdf;
import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import models.Antecedent;
import models.ClasseAntecedent;
import models.Consultation;
import models.Ordonnance;
import models.Patient;
import models.PatientAntecedent;
import models.Session;
import models.TypeConsultation;
import org.hibernate.Hibernate;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Baye Lahad DIAGNE
 */


public class DossierPatient extends javax.swing.JInternalFrame {

    private Patient patient;
    private final Long ID_PATIENT;
    private final PatientDAO patientDAO;
    private final ClasseAntecedentDAO classeAntecedentDAO;
    private final AntecedantDAO antecedentDAO;
    private final PatientAntecedentDAO paDAO;
    private final TypeConsultationDAO typeConsultationDAO;
    private DefaultTreeModel myTreeModel;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY");
    private final String AJOUTER_CONSULTATION = "Nouvelle consultation";
    private final String MODIFIER_CONSULTATION = "Modifier consultation";
    private final String SUPPRIMER_CONSULTATION = "Supprimer consultation";
    private final String AJOUTER_ORDONNANCE = "Nouvelle ordonnance";
    private final String MODIFIER_ORDONNANCE = "Modifier ordonnance";
    private final String SUPPRIMER_ORDONNANCE = "Supprimer ordonnance";
    private Consultation consultationEdit;
    private Ordonnance ordonnanceEdit;
    private static final Logger LOGGER = Logger.getLogger(DossierPatient.class.getName());

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

    static class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {

        WordWrapCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
    }

    /**
     * Creates new form DossierPatient
     *
     * @param idPatient
     */
    //Reouvrir liste on close
    //Change all ToggleButton by Button
    public DossierPatient(Long idPatient) {
        initComponents();
        ID_PATIENT = idPatient;
        patientDAO = new PatientDAO();
        classeAntecedentDAO = new ClasseAntecedentDAO();
        typeConsultationDAO = new TypeConsultationDAO();
        antecedentDAO = new AntecedantDAO();
        paDAO = new PatientAntecedentDAO();
        patient = patientDAO.findById(idPatient);
        Hibernate.initialize(patient.getProfession());
        Hibernate.initialize(patient.getProfessionConjoint());
        Hibernate.initialize(patient.getPatientAntecedentListe());
        remplirSignaletique();
        this.setTitle(patient.getPrenom() + " " + patient.getNom());
        remplirComboClassesAntecedent();
        remplirTree();
        remplirComboTypeConsultation();
        remplirTableauConsultation();
        remplirComboActionConsultation();
        remplirComboActionOrdonnance();
        remplirTableauOrdonnance();
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
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void remplirComboTypeConsultation() {
        try {
            List<TypeConsultation> types = typeConsultationDAO.findAll();
            typeConsultationCbx.removeAll();
            typeConsultationCbx.addItem("");
            for (TypeConsultation tc : types) {
                typeConsultationCbx.addItem(tc.getLibelle());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void remplirTableauConsultation() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.setRowCount(0);
            String[] entete = new String[]{"ID", "Date", "Type de la consultation", "Détails de la consultation"};
            model.setColumnIdentifiers(entete);
            List<Consultation> consultations = this.patient.getConsultationListe();
            for (Consultation c : consultations) {
                model.addRow(new Object[]{c.getId(), dateFormat.format(c.getDateConsultation()), c.getTypeConsultation().getLibelle(), c.getDonnees()});
            }
            this.tableConsultations.setModel(model);
            TableColumnModel tcm = tableConsultations.getColumnModel();
            tcm.getColumn(3).setCellRenderer(new WordWrapCellRenderer());
            tcm.getColumn(2).setCellRenderer(new WordWrapCellRenderer());
            tcm.removeColumn(tcm.getColumn(0));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void remplirTableauOrdonnance() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.setRowCount(0);
            String[] entete = new String[]{"ID", "Date", "Détails de l'ordonnance"};
            model.setColumnIdentifiers(entete);
            List<Ordonnance> ordonnances = this.patient.getOrdonnanceListe();
            for (Ordonnance o : ordonnances) {
                model.addRow(new Object[]{o.getId(), dateFormat.format(o.getDateOrdonnance()), o.getDonnees()});
            }
            this.tableOrdonnance.setModel(model);
            TableColumnModel tcm = tableOrdonnance.getColumnModel();
            tcm.removeColumn(tcm.getColumn(0));
            //setTableOrdonnanceHauteurLigne();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
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

    private Consultation findConsultationInPatient(Integer idConsultation) {
        for (int i = 0; i < this.patient.getConsultationListe().size(); i++) {
            if (this.patient.getConsultationListe().get(i).getId().equals(idConsultation)) {
                return this.patient.getConsultationListe().get(i);
            }
        }
        return null;
    }

    private Ordonnance findOrdonnanceInPatient(Long idOrdo) {
        for (int i = 0; i < this.patient.getOrdonnanceListe().size(); i++) {
            if (this.patient.getOrdonnanceListe().get(i).getId().equals(idOrdo)) {
                return this.patient.getOrdonnanceListe().get(i);
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
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void setLabelValue(JLabel label, String value) {
        String valueString = "Non renseigné(e)";

        if (value != null) {
            if (!value.isEmpty()) {
                valueString = (String) value;
            }
        }

        label.setText(valueString);
    }

    private void remplirSignaletique() {

        this.setLabelValue(prenomLabel, patient.getPrenom());
        this.setLabelValue(nomLabel, patient.getNom());
        dateNaissanceLabel.setText(patient.getDateNaissance() == null ? "Non renseigné(e)" : dateFormat.format(patient.getDateNaissance()));
        this.setLabelValue(lieuNaissanceLabel, patient.getLieuNaissance());
        String libelleSexe = patient.getSexe() != null ? patient.getSexe().getName() : "";
        this.setLabelValue(sexeLabel, libelleSexe);
        String libelleCivilite = patient.getCivilite() != null ? patient.getCivilite().getName() : "";
        this.setLabelValue(civiliteLabel, libelleCivilite);
        String libelleProfession = patient.getProfession() != null ? patient.getProfession().getLibelle() : "";
        this.setLabelValue(professionLabel, libelleProfession);
        this.setLabelValue(numeroDossierLabel, patient.getNumeroDossier());
        this.setLabelValue(adresseLabel, patient.getAdresse());
        this.setLabelValue(telPortableLabel, patient.getTelephonePortable());
        this.setLabelValue(telDomicileLabel, patient.getTelephoneDomicile());
        this.setLabelValue(telBureauLabel, patient.getTelephoneBureau());
        this.setLabelValue(prenomConjointLabel, patient.getPrenomConjoint());
        this.setLabelValue(nomConjointLabel, patient.getNomConjoint());
        this.setLabelValue(telConjointLabel, patient.getTelephoneConjoint());
        String profConjoint = patient.getProfessionConjoint() == null ? "" : patient.getProfessionConjoint().getLibelle();
        this.setLabelValue(professionConjointLabel, profConjoint);
        String nomMedecin = patient.getMedecinTraitant() != null ? patient.getMedecinTraitant().getPrenom() + " " + patient.getMedecinTraitant().getNom() : "";
        this.setLabelValue(medecinTraitantLabel, nomMedecin);
        suiviDepuisLabel.setText(patient.getConnuDepuis() == null ? "Non renseigné(e)" : dateFormat.format(patient.getConnuDepuis()));
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
        jLabel18 = new javax.swing.JLabel();
        medecinTraitantLabel = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        suiviDepuisLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        antecedentsTree = new javax.swing.JTree();
        comboClasses = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableAntecedentsPatient = new javax.swing.JTable();
        updateAntecedantsBtn = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableConsultations = new javax.swing.JTable();
        typeConsultationCbx = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        detailsConsultationTxt = new javax.swing.JTextArea();
        jLabel22 = new javax.swing.JLabel();
        consultationBtn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        actionConsultationCbx = new javax.swing.JComboBox<>();
        dateConsultationTxt = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        actionOrdonnanceCbx = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel25 = new javax.swing.JLabel();
        dateOrdonnanceTxt = new com.toedter.calendar.JDateChooser();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        detailsOrdonnanceTxt = new javax.swing.JTextArea();
        ordonnanceBtn = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableOrdonnance = new javax.swing.JTable();
        imprimerBtn = new javax.swing.JButton();
        closeDossierBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        jLabel18.setText("Patient du docteur ");

        medecinTraitantLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        medecinTraitantLabel.setText("Label18");

        jLabel19.setText("depuis le");

        suiviDepuisLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        suiviDepuisLabel.setText("Label18");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
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
                                    .addComponent(adresseLabel)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(medecinTraitantLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(suiviDepuisLabel)))
                .addContainerGap(206, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(medecinTraitantLabel)
                    .addComponent(jLabel19)
                    .addComponent(suiviDepuisLabel))
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
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

        tableConsultations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableConsultations.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableConsultationsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableConsultations);

        jLabel20.setText("Type");

        jLabel21.setText("Date");

        detailsConsultationTxt.setColumns(20);
        detailsConsultationTxt.setRows(5);
        jScrollPane4.setViewportView(detailsConsultationTxt);

        jLabel22.setText("Détails");

        consultationBtn.setText("Nouvelle Consultation");
        consultationBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultationBtnActionPerformed(evt);
            }
        });

        jLabel23.setText("Action");

        actionConsultationCbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionConsultationCbxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(consultationBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addComponent(actionConsultationCbx, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(typeConsultationCbx, 0, 215, Short.MAX_VALUE)
                            .addComponent(dateConsultationTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(actionConsultationCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(typeConsultationCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21)
                            .addComponent(dateConsultationTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(consultationBtn))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Consultations", jPanel3);

        jLabel24.setText("Action ");

        actionOrdonnanceCbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionOrdonnanceCbxActionPerformed(evt);
            }
        });

        jLabel25.setText("Date");

        jLabel26.setText("Détails");

        detailsOrdonnanceTxt.setColumns(20);
        detailsOrdonnanceTxt.setRows(5);
        jScrollPane5.setViewportView(detailsOrdonnanceTxt);

        ordonnanceBtn.setText("jButton1");
        ordonnanceBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordonnanceBtnActionPerformed(evt);
            }
        });

        tableOrdonnance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableOrdonnance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableOrdonnanceMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tableOrdonnance);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(37, 37, 37)
                        .addComponent(actionOrdonnanceCbx, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateOrdonnanceTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)))
                    .addComponent(ordonnanceBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(actionOrdonnanceCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25)
                            .addComponent(dateOrdonnanceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(ordonnanceBtn)
                        .addGap(0, 63, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Ordonnances", jPanel4);

        imprimerBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        imprimerBtn.setText("Générer dossier en PDF");
        imprimerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimerBtnActionPerformed(evt);
            }
        });

        closeDossierBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        closeDossierBtn.setText("Fermer");
        closeDossierBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeDossierBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(354, 354, 354)
                        .addComponent(imprimerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(closeDossierBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imprimerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(closeDossierBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
                //patAnt.setPatient(this.patient);
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

    private void disableFormConsultation() {
        typeConsultationCbx.setEnabled(false);
        dateConsultationTxt.setEnabled(false);
        detailsConsultationTxt.setEnabled(false);
    }

    private void enableFormConsultation() {
        typeConsultationCbx.setEnabled(true);
        dateConsultationTxt.setEnabled(true);
        detailsConsultationTxt.setEnabled(true);
    }

    private void viderFormConsultation() {
        typeConsultationCbx.setSelectedIndex(0);
        actionConsultationCbx.setSelectedIndex(0);
        dateConsultationTxt.setDate(new Date());
        detailsConsultationTxt.setText("");
    }

    private void disableFormOrdonnance() {
        dateOrdonnanceTxt.setEnabled(false);
        detailsOrdonnanceTxt.setEnabled(false);
    }

    private void enableFormOrdonnance() {
        dateOrdonnanceTxt.setEnabled(true);
        detailsOrdonnanceTxt.setEnabled(true);
    }

    private void viderFormOrdonnance() {
        actionOrdonnanceCbx.setSelectedIndex(0);
        dateOrdonnanceTxt.setDate(new Date());
        detailsOrdonnanceTxt.setText("");
    }

    private void remplirComboActionConsultation() {
        actionConsultationCbx.removeAll();
        actionConsultationCbx.addItem("");
        actionConsultationCbx.addItem(AJOUTER_CONSULTATION);
        actionConsultationCbx.addItem(MODIFIER_CONSULTATION);
        actionConsultationCbx.addItem(SUPPRIMER_CONSULTATION);
    }

    private void remplirComboActionOrdonnance() {
        actionOrdonnanceCbx.removeAll();
        actionOrdonnanceCbx.addItem("");
        actionOrdonnanceCbx.addItem(AJOUTER_ORDONNANCE);
        actionOrdonnanceCbx.addItem(MODIFIER_ORDONNANCE);
        actionOrdonnanceCbx.addItem(SUPPRIMER_ORDONNANCE);
    }

    private void actionConsultationCbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionConsultationCbxActionPerformed
        String action = (String) actionConsultationCbx.getSelectedItem();
        detailsConsultationTxt.setText("");
        switch (action) {
            case "":
                disableFormConsultation();
                consultationBtn.setEnabled(false);
                consultationBtn.setText("Aucune action");
                break;
            case AJOUTER_CONSULTATION:
                enableFormConsultation();
                detailsConsultationTxt.setText("Le " + dateFormat.format(new Date()) + " Docteur " + Session.getUser().getNom());
                consultationBtn.setEnabled(true);
                consultationBtn.setText(AJOUTER_CONSULTATION);
                break;
            case MODIFIER_CONSULTATION:
                enableFormConsultation();
                consultationBtn.setEnabled(true);
                consultationBtn.setText(MODIFIER_CONSULTATION);
                break;
            case SUPPRIMER_CONSULTATION:
                disableFormConsultation();
                consultationBtn.setEnabled(true);
                consultationBtn.setText(SUPPRIMER_CONSULTATION);
                break;
        }
    }//GEN-LAST:event_actionConsultationCbxActionPerformed

    private void tableConsultationsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableConsultationsMouseClicked
        String action = (String) actionConsultationCbx.getSelectedItem();
        Integer selectedID;

        if (action.isEmpty()) {
            JOptionPane.showMessageDialog(null, "VEUILLEZ SELECTIONNER UNE ACTION ", "ERREUR", JOptionPane.ERROR_MESSAGE);
            tableConsultations.clearSelection();
        } else if (action.equals(MODIFIER_CONSULTATION) || action.equals(SUPPRIMER_CONSULTATION)) {
            DefaultTableModel m = (DefaultTableModel) tableConsultations.getModel();
            try {
                selectedID = (Integer) m.getValueAt(tableConsultations.getSelectedRow(), 0);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "VEUILLEZ SELECTIONNER UN ELEMENT VALIDE", "ERREUR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.consultationEdit = findConsultationInPatient(selectedID);
            typeConsultationCbx.setSelectedItem(consultationEdit.getTypeConsultation().getLibelle());
            dateConsultationTxt.setDate(consultationEdit.getDateConsultation());
            detailsConsultationTxt.setText(consultationEdit.getDonnees());
        }
    }//GEN-LAST:event_tableConsultationsMouseClicked

    private void consultationBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultationBtnActionPerformed
        String action = (String) actionConsultationCbx.getSelectedItem();
        switch (action) {
            case AJOUTER_CONSULTATION:
                Consultation newConsultation = new Consultation();
                newConsultation.setDateConsultation(dateConsultationTxt.getDate());
                newConsultation.setDonnees(detailsConsultationTxt.getText());
                newConsultation.setPatient(patient);
                TypeConsultation tc = typeConsultationDAO.findByLibelle((String) typeConsultationCbx.getSelectedItem());
                newConsultation.setTypeConsultation(tc);
                patient.addConsultation(newConsultation);
                if (patientDAO.edit(patient)) {
                    JOptionPane.showMessageDialog(null, "CONSULTATION AJOUTE AVEC SUCCES", "SUCCES", JOptionPane.INFORMATION_MESSAGE);
                    viderFormConsultation();
                } else {
                    JOptionPane.showMessageDialog(null, "ECHEC DE L'AJOUT DE LA CONSULTATION", "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case MODIFIER_CONSULTATION:
                if (this.consultationEdit == null) {
                    JOptionPane.showMessageDialog(null, "CONSULTATION INTROUVABLE", "ERREUR", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (typeConsultationCbx.getSelectedIndex() == 0 || dateConsultationTxt.getDate() == null) {
                        JOptionPane.showMessageDialog(null, "REMPLISSEZ TOUS LES CHAMPS", "ERREUR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        consultationEdit.setDonnees(detailsConsultationTxt.getText());
                        consultationEdit.setDateConsultation(dateConsultationTxt.getDate());
                        TypeConsultation typeEdit = typeConsultationDAO.findByLibelle(typeConsultationCbx.getSelectedItem().toString());
                        consultationEdit.setTypeConsultation(typeEdit);
                        if (patientDAO.editConsultation(patient, consultationEdit)) {
                            JOptionPane.showMessageDialog(null, "CONSULTATION MODIFIEE AVEC SUCCES", "SUCCES", JOptionPane.INFORMATION_MESSAGE);
                            viderFormConsultation();
                        } else {
                            JOptionPane.showMessageDialog(null, "ECHEC DE L'EDITION DE LA CONSULTATION", "ERREUR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                break;
            case SUPPRIMER_CONSULTATION:
                if (consultationEdit == null) {
                    JOptionPane.showMessageDialog(null, "CONSULTATION INTROUVABLE", "ERREUR", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (patientDAO.deleteConsultation(patient, consultationEdit)) {
                        JOptionPane.showMessageDialog(null, "CONSULTATION SUPPRIMEE AVEC SUCCES", "SUCCES", JOptionPane.INFORMATION_MESSAGE);
                        viderFormConsultation();
                    } else {
                        JOptionPane.showMessageDialog(null, "ECHEC DE LA SUPPRESSION DE LA CONSULTATION", "ERREUR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            default:
                break;
        }
        this.patient = patientDAO.findById(ID_PATIENT);
        remplirTableauConsultation();

    }//GEN-LAST:event_consultationBtnActionPerformed

    private void actionOrdonnanceCbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionOrdonnanceCbxActionPerformed
        String action = (String) actionOrdonnanceCbx.getSelectedItem();
        switch (action) {
            case "":
                disableFormOrdonnance();
                ordonnanceBtn.setEnabled(false);
                ordonnanceBtn.setText("Aucune action");
                break;
            case AJOUTER_ORDONNANCE:
                enableFormOrdonnance();
                ordonnanceBtn.setEnabled(true);
                ordonnanceBtn.setText(AJOUTER_ORDONNANCE);
                break;
            case MODIFIER_ORDONNANCE:
                enableFormOrdonnance();
                ordonnanceBtn.setEnabled(true);
                ordonnanceBtn.setText(MODIFIER_ORDONNANCE);
                break;
            case SUPPRIMER_ORDONNANCE:
                disableFormOrdonnance();
                ordonnanceBtn.setEnabled(true);
                ordonnanceBtn.setText(SUPPRIMER_ORDONNANCE);
                break;
        }
    }//GEN-LAST:event_actionOrdonnanceCbxActionPerformed

    private void tableOrdonnanceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOrdonnanceMouseClicked
        String action = (String) actionOrdonnanceCbx.getSelectedItem();
        Long selectedID;

        if (action.isEmpty()) {
            JOptionPane.showMessageDialog(null, "VEUILLEZ SELECTIONNER UNE ACTION ", "ERREUR", JOptionPane.ERROR_MESSAGE);
            tableOrdonnance.clearSelection();
        } else if (action.equals(MODIFIER_ORDONNANCE) || action.equals(SUPPRIMER_ORDONNANCE)) {
            DefaultTableModel m = (DefaultTableModel) tableOrdonnance.getModel();
            try {
                selectedID = (Long) m.getValueAt(tableOrdonnance.getSelectedRow(), 0);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "VEUILLEZ SELECTIONNER UN ELEMENT VALIDE", "ERREUR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.ordonnanceEdit = findOrdonnanceInPatient(selectedID);
            dateOrdonnanceTxt.setDate(ordonnanceEdit.getDateOrdonnance());
            detailsOrdonnanceTxt.setText(ordonnanceEdit.getDonnees());
        }
    }//GEN-LAST:event_tableOrdonnanceMouseClicked

    private void ordonnanceBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordonnanceBtnActionPerformed
        String action = (String) actionOrdonnanceCbx.getSelectedItem();
        switch (action) {
            case AJOUTER_ORDONNANCE:
                Ordonnance newOrdo = new Ordonnance();
                newOrdo.setDateOrdonnance(dateOrdonnanceTxt.getDate());
                newOrdo.setDonnees(detailsOrdonnanceTxt.getText());
                newOrdo.setPatient(patient);
                patient.addOrdonnance(newOrdo);
                if (patientDAO.edit(patient)) {
                    JOptionPane.showMessageDialog(null, "ORDONNANCE AJOUTEE AVEC SUCCES", "SUCCES", JOptionPane.INFORMATION_MESSAGE);
                    viderFormOrdonnance();
                } else {
                    JOptionPane.showMessageDialog(null, "ECHEC DE L'AJOUT DE L'ORDONNANCE", "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case MODIFIER_ORDONNANCE:
                if (this.ordonnanceEdit == null) {
                    JOptionPane.showMessageDialog(null, "ORDONNANCE INTROUVABLE", "ERREUR", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (dateOrdonnanceTxt.getDate() == null || detailsOrdonnanceTxt.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "REMPLISSEZ TOUS LES CHAMPS", "ERREUR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ordonnanceEdit.setDonnees(detailsOrdonnanceTxt.getText());
                        ordonnanceEdit.setDateOrdonnance(dateOrdonnanceTxt.getDate());
                        if (patientDAO.editOrdonnance(patient, ordonnanceEdit)) {
                            JOptionPane.showMessageDialog(null, "ORDONNANCE MODIFIEE AVEC SUCCES", "SUCCES", JOptionPane.INFORMATION_MESSAGE);
                            viderFormOrdonnance();
                        } else {
                            JOptionPane.showMessageDialog(null, "ECHEC DE L'EDITION DE L'ORDONNANCE", "ERREUR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                break;
            case SUPPRIMER_ORDONNANCE:
                if (ordonnanceEdit == null) {
                    JOptionPane.showMessageDialog(null, "ORDONNANCE INTROUVABLE", "ERREUR", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (patientDAO.deleteOrdonnance(patient, ordonnanceEdit)) {
                        JOptionPane.showMessageDialog(null, "ORDONNANCE SUPPRIMEE AVEC SUCCES", "SUCCES", JOptionPane.INFORMATION_MESSAGE);
                        viderFormOrdonnance();
                    } else {
                        JOptionPane.showMessageDialog(null, "ECHEC DE LA SUPPRESSION DE L'ORDONNANCE", "ERREUR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            default:
                break;
        }
        this.patient = patientDAO.findById(ID_PATIENT);
        remplirTableauOrdonnance();

    }//GEN-LAST:event_ordonnanceBtnActionPerformed

    private void imprimerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimerBtnActionPerformed
        try {
            String path = DossierPdf.initDocument(ID_PATIENT);
            JOptionPane.showMessageDialog(null, "DOSSIER GENERE AVEC SUCCES", "SUCCES", JOptionPane.INFORMATION_MESSAGE);
            if (Desktop.isDesktopSupported()) {

                File myFile = new File(path);
                Desktop.getDesktop().open(myFile);
            }
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ECHEC DE LA CREATION", "ERREUR", JOptionPane.ERROR_MESSAGE);
        } catch (DocumentException | IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ECHEC DE LA CREATION", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_imprimerBtnActionPerformed

    private void closeDossierBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeDossierBtnActionPerformed
        ListePatients listePatients = new ListePatients();
        this.getDesktopPane().add(listePatients).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_closeDossierBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> actionConsultationCbx;
    private javax.swing.JComboBox<String> actionOrdonnanceCbx;
    private javax.swing.JLabel adresseLabel;
    private javax.swing.JTree antecedentsTree;
    private javax.swing.JLabel civiliteLabel;
    private javax.swing.JButton closeDossierBtn;
    private javax.swing.JComboBox<String> comboClasses;
    private javax.swing.JButton consultationBtn;
    private com.toedter.calendar.JDateChooser dateConsultationTxt;
    private javax.swing.JLabel dateNaissanceLabel;
    private com.toedter.calendar.JDateChooser dateOrdonnanceTxt;
    private javax.swing.JTextArea detailsConsultationTxt;
    private javax.swing.JTextArea detailsOrdonnanceTxt;
    private javax.swing.JButton imprimerBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lieuNaissanceLabel;
    private javax.swing.JLabel medecinTraitantLabel;
    private javax.swing.JLabel nomConjointLabel;
    private javax.swing.JLabel nomLabel;
    private javax.swing.JLabel numeroDossierLabel;
    private javax.swing.JButton ordonnanceBtn;
    private javax.swing.JLabel prenomConjointLabel;
    private javax.swing.JLabel prenomLabel;
    private javax.swing.JLabel professionConjointLabel;
    private javax.swing.JLabel professionLabel;
    private javax.swing.JLabel sexeLabel;
    private javax.swing.JLabel suiviDepuisLabel;
    private javax.swing.JTable tableAntecedentsPatient;
    private javax.swing.JTable tableConsultations;
    private javax.swing.JTable tableOrdonnance;
    private javax.swing.JLabel telBureauLabel;
    private javax.swing.JLabel telConjointLabel;
    private javax.swing.JLabel telDomicileLabel;
    private javax.swing.JLabel telPortableLabel;
    private javax.swing.JComboBox<String> typeConsultationCbx;
    private javax.swing.JToggleButton updateAntecedantsBtn;
    // End of variables declaration//GEN-END:variables
}
