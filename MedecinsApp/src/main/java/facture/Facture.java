package facture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import dao.PatientDAO;
import models.Consultation;
import models.Ordonnance;
import models.Patient;
import models.PatientAntecedent;

public class Facture {

    private static File file;
    private static File file1;
    private static Document document = new Document();
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
    private static Font font = new Font(FontFamily.TIMES_ROMAN, 8);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY");

    private static Patient patient;

    static PatientDAO patientDAO = new PatientDAO();

    public static File generationRepertoireFacture(String dossierMedical) {
        if (!dossierMedical.equals("")) {
            String dossierCentral = "factures\\";
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
        document.close();
    }

    // separateur
    public static void separateur() throws DocumentException {
        LineSeparator sep = new LineSeparator();
        sep.setOffset(5);
        document.add(sep);
    }

    public static void sautLigne() throws DocumentException {
        Paragraph sautligne = new Paragraph(" ");
        document.add(sautligne);
    }

    // dossier patient
    public static void dossierPatient(Long idpatient) throws DocumentException {
        patient = patientDAO.findById(idpatient);
        Paragraph paragraphnumerodossier = new Paragraph(
                "Dossier du patient n°  " + patient.getNumeroDossier() + "", subFont);
        paragraphnumerodossier.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraphnumerodossier);
        sautLigne();
    }

    // getInfosPersoPatient
    public static void infoPersoPatient(Long idPatient) throws DocumentException {
        ArrayList<Paragraph> paralist = new ArrayList<Paragraph>();

        if (patient != null) {
            separateur();
            Paragraph p1 = new Paragraph("Prénom :  " + patient.getPrenom() + "  ");
            Paragraph p2 = new Paragraph("Nom :  " + patient.getNom() + "  ");
            Paragraph p3 = new Paragraph(
                    "Né(e) le " + patient.getDateNaissance() + " à " + patient.getLieuNaissance() + "  ");
            Paragraph p4;
            try {
                p4 = new Paragraph("Civilité :  " + patient.getCivilite().getName() + "  ");
            } catch (Exception e) {
                p4 = new Paragraph("Civilité : Non renseignée");
            }
            Paragraph p5 = new Paragraph("Profession :  " + patient.getProfession().getLibelle() + "  ");
            Paragraph p6 = new Paragraph("Sexe :  " + patient.getSexe().getName() + "  ");
            Paragraph p7 = new Paragraph("Adresse :  " + patient.getAdresse() + "  ");
            Paragraph p8 = new Paragraph("Suivi depuis :  " + patient.getConnuDepuis() + "   par   "
                    + patient.getMedecinTraitant().getNom() + "  ");

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
                document.add(paralist.get(i));
                sautLigne();
            }

        }

    }

    // contacts titre
    public static void contactTitre() throws DocumentException {
        Paragraph contact = new Paragraph("Contacts", subFont);
        document.add(contact);
        sautLigne();
    }

    // getInfoContact
    public static void getInfoContact(Long idPatient) throws DocumentException {
        ArrayList<Paragraph> paralist = new ArrayList<Paragraph>();

        if (patient != null) {
            separateur();
            Paragraph p1 = new Paragraph("Portable : " + patient.getTelephonePortable() + " ");
            Paragraph p2 = new Paragraph("Domicile :  " + patient.getTelephoneDomicile() + " ");
            Paragraph p3 = new Paragraph("Bureau  " + patient.getTelephoneBureau() + "  ");
            Paragraph p4 = new Paragraph("Conjoint :  " + patient.getPrenomConjoint() + "   " + patient.getNomConjoint() + " ");
            Paragraph p5 = new Paragraph("Contact conjoint :  " + patient.getTelephoneConjoint() + "  ");
            Paragraph p6 = new Paragraph("Profession conjoint :  " + patient.getProfessionConjoint() + "  ");

            paralist.add(p1);
            paralist.add(p2);
            paralist.add(p3);
            paralist.add(p4);
            paralist.add(p5);
            paralist.add(p6);

            for (int i = 0; i < paralist.size(); i++) {
                centrerPara(paralist.get(i), 20);
                paralist.get(i).setLeading(1, 1);
                paralist.get(i).setAlignment(Element.ALIGN_JUSTIFIED);
                document.add(paralist.get(i));
                sautLigne();
            }

        }

    }

    public static void ficheSignaletique() throws DocumentException {
        Paragraph ficheSignaletique = new Paragraph("Fiche Signalétique", subFont);
        document.add(ficheSignaletique);
        sautLigne();
    }

    // AntecedantTitre
    public static void antecedantTitre() throws DocumentException {
        Paragraph ficheSignaletique = new Paragraph("Antécédents", subFont);
        document.add(ficheSignaletique);
        sautLigne();
        separateur();
    }

    // funtion pour centre les paragraphes 
    public static void centrerPara(Paragraph paragraphe, int valeur) {
        paragraphe.setFont(font);
        paragraphe.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraphe.setIndentationLeft(valeur);
        paragraphe.setIndentationRight(valeur);
    }

    // Info antécedant
    public static void getInfoAntecedant() throws DocumentException {
        List<PatientAntecedent> listeAnte = patient.getPatientAntecedentListe();
        Set set = new HashSet(listeAnte);
        for (int i = 0; i < listeAnte.size(); i++) {
            Paragraph paragrapheClasseAnt = new Paragraph("- " + listeAnte.get(i).getAntecedent().getClasse().getLibelle());
            centrerPara(paragrapheClasseAnt, 20);
            document.add(paragrapheClasseAnt);

            Paragraph paragrapheAntecedant = new Paragraph("" + new Chunk("\u2022", subFont) + listeAnte.get(i).getAntecedent().getLibelle());
            centrerPara(paragrapheAntecedant, 40);
            document.add(paragrapheAntecedant);
        }
    }

    // Info Historique
    public static void getInfoHistorique() throws DocumentException {
        List<Consultation> listecon = patient.getConsultationListe();
        Set set = new HashSet(listecon);
        for (int i = 0; i < listecon.size(); i++) {

            Paragraph paragraphehistorique = new Paragraph("  " + dateFormat.format(listecon.get(i).getDateConsultation()) + "  -  " + listecon.get(i).getTypeConsultation().getLibelle() + "  -  " + listecon.get(i).getDonnees());
            centrerPara(paragraphehistorique, 20);
            document.add(paragraphehistorique);
        }
    }

    // Historique consultation
    public static void consultationTitre() throws DocumentException {
        Paragraph contact = new Paragraph("Historique consultation", subFont);
        document.add(contact);
        sautLigne();
    }

    // Info Ordonnances
    public static void getInfoOrdonnances() throws DocumentException {
        List<Ordonnance> listeordonance = patient.getOrdonnanceListe();
        Set set = new HashSet(listeordonance);
        for (int i = 0; i < listeordonance.size(); i++) {
            String donne = listeordonance.get(i).getDonnees().replace(";", " - ");
            Paragraph paragrapheordonance = new Paragraph(" " + dateFormat.format(listeordonance.get(i).getDateOrdonnance()) + " - " + donne + "   ");
            centrerPara(paragrapheordonance, 20);
            document.add(paragrapheordonance);
        }
    }

    // Historique Ordonnances
    public static void OrdonnancesTitre() throws DocumentException {
        Paragraph contact = new Paragraph("Ordonnances", subFont);
        document.add(contact);
        sautLigne();
    }

    // contructeur
    public Facture(Long idPatient) {
        patient = patientDAO.findById(idPatient);
    }

    // get data patient
    public static void getDataPatient(Long idpatient) {
        try {
            // dossier numero :
            dossierPatient(idpatient);

            // ficheSignaletique
            ficheSignaletique();
            // getInfosPersoPatient
            infoPersoPatient(idpatient);

            //contact titre 
            contactTitre();
            // info contact
            getInfoContact(idpatient);

            // AntécédentTitre
            antecedantTitre();
            // GetInfoAntécédents
            getInfoAntecedant();

            //Historique consultation 
            consultationTitre();
            getInfoHistorique();

            // InfoOrdonnances()
            OrdonnancesTitre();
            getInfoOrdonnances();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // InitDocument
    public static void InitDocument(Long idpatient) throws FileNotFoundException, DocumentException {
        File file = generationRepertoireFacture("patient");
        String extention = ".pdf";
        if (verifRep(file)) {
            // creation document
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getPath() + "\\" + generateNumeroFacture() + extention));
            // Ouverture document
            document.open();
            // getDataPatient
            getDataPatient(idpatient);
            // infoPatient

            // close document
            closeDocument();
        } else {

        }

    }
}
