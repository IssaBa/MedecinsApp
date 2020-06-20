package facture;

import java.io.File;

import com.itextpdf.text.Document;

public class facture {

	private Document document = new Document();
	
	// Generation aléatoire du numero de facture 
	public  String generateNumeroFacture() {
		try {
			
		} catch (Exception e) {
			System.out.println(e);
		}
	        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; 
	        StringBuffer pass = new StringBuffer();
	        for(int x=0;x<8;x++)   {
	           int i = (int)Math.floor(Math.random() * (chars.length() -1));
	           pass.append(chars.charAt(i));
	        }
	        return pass.toString();
	}
	
	
	/* Emplacement fichier factu
	 * Ici je creer les repertoir en fonction de la facture demande 
	 * si c'est le Patient je cree un repertoire patient la ou je genere le dossier patient dynamiquement
	 * Le patient peut vouloir avec la liste de ses antécedants  je cree un dossier antécedant la ou je genere la document antécedant dynamiquement
	 */
	public boolean generationDossierFacture(String dossier) {
		try {
			
			 File file = new File("C:\\"+dossier+"");
		        if (!file.exists()) {
		            if (file.mkdir()) {
		                System.out.println("Directory is created!");
		                return true;
		            } else {
		                System.out.println("Failed to create directory!");
		                return false;
		            }
		        }
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
}
