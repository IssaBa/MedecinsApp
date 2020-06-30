package facture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import dao.PatientAntecedentDAO;

import dao.PatientDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.ClasseAntecedent;
import models.Consultation;
import models.Ordonnance;
import models.Patient;
import models.PatientAntecedent;
import org.hibernate.Hibernate;

public class DossierPdf {

    private static File file;
    private static File file1;
    private static final Document DOCUMENT = new Document();
    private static final Font SUB_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
    private static final Font FONT = new Font(FontFamily.TIMES_ROMAN, 8);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMMM YYYY");
    private static String message;
    private static Patient patient;
    private static PatientAntecedentDAO paDAO = new PatientAntecedentDAO();
    static PatientDAO patientDAO = new PatientDAO();
    private static final Logger LOGGER = Logger.getLogger(DossierPdf.class.getName());

    public static File generationRepertoireFacture(String dossierMedical) {
        if (!dossierMedical.equals("")) {
            String dossierCentral = "FAYEMILOG\\";
            file1 = new File("C:\\" + dossierCentral);
            file = new File(file1.getPath() + "\\" + dossierMedical);

            try {
                if (!file1.exists()) {
                    if (file1.mkdir()) {
                    }
                }
                if (!file.exists()) {
                    if (file.mkdir()) {
                    }
                }
                return file;

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return file;
    }

    // Verife  liste
    public static Boolean verifListeNull(List list) {

        if (patient.getConsultationListe().size() < 0) {
            message = "la Liste consultation du patient" + patient.getPrenom() + " est vide Merci";
            return true;
        }
        if (patient.getOrdonnanceListe().size() < 0) {
            message = " Le patient" + patient.getPrenom() + " n'a pas d'ordonnace Merci";
            return true;
        }
        return false;
    }

    // Generation aléatoire du numero de facture
    public static String generateNumeroFacture() {
        try {
            String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            StringBuffer pass = new StringBuffer();
            for (int x = 0; x < 8; x++) {
                int i = (int) Math.floor(Math.random() * (chars.length() - 1));
                pass.append(chars.charAt(i));
            }
            return pass.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // verification si le repertoire existe
    public static boolean verifRep(File file) {
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    //  close document a la fin du job
    public static void closeDocument() throws DocumentException {
        DOCUMENT.close();
    }

    // separateur
    public static void separateur() throws DocumentException {
        LineSeparator sep = new LineSeparator();
        sep.setOffset(5);
        DOCUMENT.add(sep);
    }

    public static void sautLigne() throws DocumentException {
        Paragraph sautligne = new Paragraph(" ");
        DOCUMENT.add(sautligne);
    }

    // dossier patient
    public static void dossierPatient() throws DocumentException {
        String text = "Dossier du patient ";
        if (patient.getNumeroDossier() != null && !patient.getNumeroDossier().isEmpty()) {
            text += patient.getNumeroDossier();
        }
        Paragraph paragraphnumerodossier = new Paragraph(text, SUB_FONT);
        paragraphnumerodossier.setAlignment(Element.ALIGN_CENTER);
        DOCUMENT.add(paragraphnumerodossier);
        sautLigne();
    }

    // getInfosPersoPatient
    public static void infoPersoPatient() throws DocumentException {
        ArrayList<Paragraph> paralist = new ArrayList<>();
        Hibernate.initialize(patient.getProfession());
        Hibernate.initialize(patient.getProfessionConjoint());

        if (patient != null) {
            String prenom = "Non renseigné";
            String nom = "Non renseigné";
            String dateNaissance = "Non renseigné";
            String lieuNaissance = "Non renseigné";
            String civilite = "Non renseigné";
            String profession = "Non renseigné";
            String sexe = "Non renseigné";
            String adresse = "Non renseigné";
            String medecin = "Non renseigné";
            String dateSuivi = "Non renseigné";
            if (patient.getPrenom() != null && !patient.getPrenom().isEmpty()) {
                prenom = patient.getPrenom();
            }
            if (patient.getNom() != null && !patient.getNom().isEmpty()) {
                nom = patient.getNom();
            }
            if (patient.getDateNaissance() != null) {
                dateNaissance = DATE_FORMAT.format(patient.getDateNaissance());
            }
            if (patient.getLieuNaissance() != null && !patient.getLieuNaissance().isEmpty()) {
                lieuNaissance = patient.getLieuNaissance();
            }
            if (patient.getCivilite() != null) {
                civilite = patient.getCivilite().getName();
            }
            if (patient.getProfession() != null) {
                profession = patient.getProfession().getLibelle();
            }
            if (patient.getSexe() != null) {
                sexe = patient.getSexe().getName();
            }
            if (patient.getAdresse() != null && !patient.getAdresse().isEmpty()) {
                adresse = patient.getAdresse();
            }
            if (patient.getMedecinTraitant() != null) {
                medecin = "Docteur " + patient.getMedecinTraitant().getPrenom() + " " + patient.getMedecinTraitant().getNom();
            }
            if (patient.getConnuDepuis() != null) {
                dateSuivi = DATE_FORMAT.format(patient.getConnuDepuis());
            }
            Paragraph p1 = new Paragraph("Prénom :  " + prenom);
            Paragraph p2 = new Paragraph("Nom :  " + nom);
            Paragraph p3 = new Paragraph("Né(e) le  " + dateNaissance + " à " + lieuNaissance);
            Paragraph p4 = new Paragraph("Civilité :  " + civilite);
            Paragraph p5 = new Paragraph("Profession :  " + profession);
            Paragraph p6 = new Paragraph("Sexe :  " + sexe);
            Paragraph p7 = new Paragraph("Adresse :  " + adresse);
            Paragraph p8 = new Paragraph("Suivi depuis : " + dateSuivi + " par " + medecin);

            paralist.add(p1);
            paralist.add(p2);
            paralist.add(p3);
            paralist.add(p4);
            paralist.add(p5);
            paralist.add(p6);
            paralist.add(p7);
            paralist.add(p8);

            for (int i = 0; i < paralist.size(); i++) {
                centrerPara(paralist.get(i), 20);
                paralist.get(i).setLeading(1, 1);
                paralist.get(i).setAlignment(Element.ALIGN_JUSTIFIED);
                DOCUMENT.add(paralist.get(i));
                sautLigne();
            }
        }
    }

    // contacts titre
    public static void contactTitre() throws DocumentException {
        Paragraph contact = new Paragraph("Contacts", SUB_FONT);
        DOCUMENT.add(contact);
        sautLigne();
        separateur();
    }

    // getInfoContact
    public static void getInfoContact() throws DocumentException {
        ArrayList<Paragraph> paralist = new ArrayList<>();

        if (patient != null) {
            if (patient.getTelephonePortable() != null && !patient.getTelephonePortable().isEmpty()) {
                Paragraph p1 = new Paragraph("Téléphone portable : " + patient.getTelephonePortable());
                paralist.add(p1);
            }
            if (patient.getTelephoneDomicile() != null && !patient.getTelephoneDomicile().isEmpty()) {
                Paragraph p2 = new Paragraph("Téléphone domicile : " + patient.getTelephoneDomicile());
                paralist.add(p2);
            }
            if (patient.getTelephoneBureau() != null && !patient.getTelephoneBureau().isEmpty()) {
                Paragraph p3 = new Paragraph("Téléphone bureau : " + patient.getTelephoneBureau());
                paralist.add(p3);
            }
            if (patient.getNomConjoint() != null && !patient.getNomConjoint().isEmpty()) {
                Paragraph p4 = new Paragraph("Nom du conjoint : " + patient.getPrenomConjoint() + " " + patient.getNomConjoint());
                paralist.add(p4);
            }
            if (patient.getTelephoneConjoint() != null && !patient.getTelephoneConjoint().isEmpty()) {
                Paragraph p5 = new Paragraph("Contact conjoint : " + patient.getTelephoneConjoint());
                paralist.add(p5);
            }
            if (patient.getProfessionConjoint() != null) {
                Paragraph p6 = new Paragraph("Profession conjoint :  " + patient.getProfessionConjoint().getLibelle());
                paralist.add(p6);
            }

            if (paralist.isEmpty()) {
                paralist.add(new Paragraph("Aucun contact renseigné"));
            }

            for (int i = 0; i < paralist.size(); i++) {
                centrerPara(paralist.get(i), 20);
                paralist.get(i).setLeading(1, 1);
                paralist.get(i).setAlignment(Element.ALIGN_JUSTIFIED);
                DOCUMENT.add(paralist.get(i));
                sautLigne();
            }

        }

    }

    public static void ficheSignaletiqueTitre() throws DocumentException {
        Paragraph ficheSignaletique = new Paragraph("Fiche Signalétique", SUB_FONT);
        DOCUMENT.add(ficheSignaletique);
        sautLigne();
        separateur();
    }

    // AntecedantTitre
    public static void antecedentTitre() throws DocumentException {
        Paragraph ficheSignaletique = new Paragraph("Antécédents", SUB_FONT);
        DOCUMENT.add(ficheSignaletique);
        sautLigne();
        separateur();
    }

    // funtion pour centre les paragraphes
    public static void centrerPara(Paragraph paragraphe, int valeur) {
        paragraphe.setFont(FONT);
        paragraphe.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraphe.setIndentationLeft(valeur);
        paragraphe.setIndentationRight(valeur);
    }

    // Info antécedant
    public static void getInfoAntecedent() throws DocumentException {
        List<PatientAntecedent> pas = patient.getPatientAntecedentListe();
        if (pas.isEmpty()) {
            DOCUMENT.add(new Paragraph("Aucun antécédent"));
        } else {
            List<ClasseAntecedent> lca = paDAO.getClassesFrom(pas);
            for (ClasseAntecedent ca : lca) {
                Paragraph paragrapheClasseAnt = new Paragraph("- " + ca.getLibelle());
                centrerPara(paragrapheClasseAnt, 20);
                DOCUMENT.add(paragrapheClasseAnt);
                for (PatientAntecedent pa : pas) {
                    Paragraph paragrapheAntecedant = new Paragraph(new Chunk("\u2022", SUB_FONT) + " " + pa.getAntecedent().getLibelle());
                    centrerPara(paragrapheAntecedant, 40);
                    DOCUMENT.add(paragrapheAntecedant);
                }
            }
        }
    }

    // Info Historique
    public static void getInfoConsultations() throws DocumentException {
        List<Consultation> listecon = patient.getConsultationListe();
        if (!listecon.isEmpty()) {
            for (int i = 0; i < listecon.size(); i++) {
                Paragraph paragraphehistorique = new Paragraph(new Chunk("\u2022", SUB_FONT) + " " + "[" + DATE_FORMAT.format(listecon.get(i).getDateConsultation()) + "] - " + listecon.get(i).getTypeConsultation().getLibelle() + " - " + listecon.get(i).getDonnees());
                DOCUMENT.add(paragraphehistorique);
            }
        } else {
            DOCUMENT.add(new Paragraph("Aucune consultation"));
        }
    }

    // Historique consultation
    public static void consultationTitre() throws DocumentException {
        Paragraph contact = new Paragraph("Historique consultation", SUB_FONT);
        DOCUMENT.add(contact);
        sautLigne();
        separateur();
    }

    // Info Ordonnances
    public static void getInfoOrdonnances() throws DocumentException {
        List<Ordonnance> listeordonance = patient.getOrdonnanceListe();
        if (!listeordonance.isEmpty()) {
            for (int i = 0; i < listeordonance.size(); i++) {
                String donne = listeordonance.get(i).getDonnees().replace(";", " - ");
                Paragraph paragrapheordonance = new Paragraph(" " + DATE_FORMAT.format(listeordonance.get(i).getDateOrdonnance()) + " - " + donne + "   ");
                centrerPara(paragrapheordonance, 20);
                DOCUMENT.add(paragrapheordonance);
            }
        } else {
            DOCUMENT.add(new Paragraph("Aucune ordonnance"));
        }

    }

    // Historique Ordonnances
    public static void ordonnancesTitre() throws DocumentException {
        Paragraph contact = new Paragraph("Ordonnances", SUB_FONT);
        DOCUMENT.add(contact);
        sautLigne();
        separateur();
    }

    // get data patient
    public static void getDataPatient() {

        try {

            // dossier numero :
            dossierPatient();

            // ficheSignaletiqueTitre
            ficheSignaletiqueTitre();
            // getInfosPersoPatient
            infoPersoPatient();

            //contact titre
            contactTitre();
            // info contact
            getInfoContact();

            // AntécédentTitre
            antecedentTitre();
            // GetInfoAntécédents
            getInfoAntecedent();

            //Historique consultation
            consultationTitre();
            getInfoConsultations();

            // InfoOrdonnances()
            ordonnancesTitre();
            getInfoOrdonnances();

        } catch (DocumentException | SecurityException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    // initDocument
    public static String initDocument(Long idpatient) throws FileNotFoundException, DocumentException {
        if (DossierPdf.patient == null) {
            patient = patientDAO.findById(idpatient);
        }
        File fileDoc = generationRepertoireFacture("patient");
        String extention = ".pdf";
        String path = fileDoc.getPath() + "\\" + patient.getPrenom() + patient.getNom() + generateNumeroFacture() + extention;
        if (verifRep(fileDoc)) {
            // creation document
            PdfWriter.getInstance(DOCUMENT, new FileOutputStream(path));
            // Ouverture document
            DOCUMENT.open();
            // getDataPatient
            getDataPatient();
            // infoPatient
            // close document
            closeDocument();
        }
        return path;
    }

}
