package views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Ouvrir extends JFrame implements ActionListener {

    JButton open = new JButton("Selectionner un fichier"); //nouveau bouton open

    public Ouvrir() {
        super("Explorateur de fichier"); //titre
        setSize(450, 100); //taille
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//fermeture
        open.addActionListener(this);//ajout d'un actionlistener
        JPanel pane = new JPanel();
        BorderLayout bord = new BorderLayout();
        pane.setLayout(bord);
        pane.add("Center", open);
        setContentPane(pane);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JFileChooser chooser = new JFileChooser();//création dun nouveau filechosser
            chooser.setApproveButtonText("Choix du fichier..."); //intitulé du bouton
            chooser.showOpenDialog(null); //affiche la boite de dialogue
            JTextArea jTextArea1 = null;
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                jTextArea1.append("Vous avez choisis : " + chooser.getSelectedFile().getAbsolutePath() + "\n"); //si un fichier est selectionné, récupérer le fichier puis sont path et l'afficher dans le champs de texte
                String Firm = chooser.getSelectedFile().getAbsolutePath();
                this.setVisible(false);
                System.out.println(Firm);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }

    }
}
