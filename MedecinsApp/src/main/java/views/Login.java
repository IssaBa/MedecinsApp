package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import dao.MedecinUserDAO;
import models.MedecinUser;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Login {

	private JFrame frame;
	private JTextField matricule;
	private JSeparator separator;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblConnexion = new JLabel("CONNEXION");
		lblConnexion.setBounds(185, 26, 151, 14);
		frame.getContentPane().add(lblConnexion);
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setBounds(98, 89, 46, 14);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(98, 136, 74, 14);
		frame.getContentPane().add(lblPassword);
		
		matricule = new JTextField();
		matricule.setBounds(235, 83, 173, 20);
		frame.getContentPane().add(matricule);
		matricule.setColumns(10);
		
		separator = new JSeparator();
		separator.setBounds(58, 175, 398, 2);
		frame.getContentPane().add(separator);
		
		JButton btnConnexion = new JButton("LOGIN");
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String username = matricule.getText();
					String paswd = password.getText().toString();
				
			if(!username.equals(null) && !password.equals(null)) {
					MedecinUserDAO medecinUserDAO = new MedecinUserDAO();
					MedecinUser medecinUser =medecinUserDAO.findUserByMatricule(username);
					if(username.equals(medecinUser.getUsername())  && paswd.equals(medecinUser.getPassword()) )  {
						Menu menu = new Menu();
						menu.show();
						menu.setVisible(true);
						frame.setVisible(false);
					
					}else {
						//System.out.println("JE SUIS LA : KO =");
						JOptionPane.showMessageDialog(null, "LOGIN OU PASSWORD ! ","INCORECTE", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					
					JOptionPane.showMessageDialog(null, "Veuillez remplir les champs Merci! ");
				}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnConnexion.setBounds(55, 200, 105, 23);
		frame.getContentPane().add(btnConnexion);
		
		JButton btnAnnuler = new JButton("ANNULER");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				matricule.setText(null);
				password.setText(null);
			}
			
			
		});
		btnAnnuler.setBounds(215, 200, 110, 23);
		frame.getContentPane().add(btnAnnuler);
		
		JButton btnSortir = new JButton("SORTIR");
		btnSortir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jFrame = new JFrame("Exit");
				if(JOptionPane.showConfirmDialog(jFrame, "Voulez-vous quitter","",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		btnSortir.setBounds(357, 200, 99, 23);
		frame.getContentPane().add(btnSortir);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(58, 59, 398, 2);
		frame.getContentPane().add(separator_1);
		
		password = new JPasswordField();
		password.setBounds(235, 133, 173, 20);
		frame.getContentPane().add(password);
	}
}
