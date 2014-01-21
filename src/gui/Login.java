package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboPopup;

import model.bdd.GestionnaireStock;

import personne.Personne;
import personne.Statut;

public class Login extends JFrame {

	private JTextField name;
	private JTextField surName;
	private String userName;
	private String userSurName;
	private GestionnaireStock gdd;
	private GUIManager gui; 
	private JComboBox<Statut> actors;
	private JButton loginButton;
	private JButton newUserButton;

	public Login(GestionnaireStock gdd, GUIManager gui) { 
		
		getContentPane().setBackground(Color.DARK_GRAY);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container mainFrame = getContentPane();

		mainFrame.add(loginUser(), BorderLayout.CENTER);

//		setVisible(true);
		
		setResizable(false);
		
		setLocation(500, 250);  

		pack();

		this.gdd = gdd;
		this.gui = gui; 

	}

	public JPanel loginUser() {

		JPanel innerUserPanel = new JPanel(new GridBagLayout());
		JPanel outerUserPanel = new JPanel();
		outerUserPanel.add(innerUserPanel);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		innerUserPanel.setBackground(Color.DARK_GRAY);
		outerUserPanel.setBackground(Color.DARK_GRAY);

		JLabel surNameLabel = new JLabel("Prenom: ");
		JLabel nameLabel = new JLabel("Nom: ");
		JLabel typeLabel = new JLabel("Type: ");

		surName = new JTextField();
		name = new JTextField();

		name.getDocument().addDocumentListener(new CheckUserName());
		surName.getDocument().addDocumentListener(new CheckUserName());

		surNameLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
		nameLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
		typeLabel.setFont(new Font("Monospaced", Font.BOLD, 14));

		surNameLabel.setForeground(GUIManager.THEME_COLOR);
		nameLabel.setForeground(GUIManager.THEME_COLOR);
		typeLabel.setForeground(GUIManager.THEME_COLOR);

		surName.setBackground(GUIManager.THEME_COLOR);
		name.setBackground(GUIManager.THEME_COLOR);

		loginButton = new JButton("Connexion");
		newUserButton = new JButton("Creer compte");

		loginButton.setBackground(Color.BLACK);
		loginButton.setForeground(GUIManager.THEME_COLOR);

		newUserButton.setBackground(Color.BLACK);
		newUserButton.setForeground(GUIManager.THEME_COLOR);

		loginButton.addActionListener(new Connect());
		newUserButton.addActionListener(new CreateUser());

		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		innerUserPanel.add(surNameLabel, c);

		c.gridx = 0;
		c.gridy = 1;
		innerUserPanel.add(surName, c);

		c.gridx = 0;
		c.gridy = 2;
		innerUserPanel.add(new JLabel(" "), c);

		c.gridx = 0;
		c.gridy = 3;
		innerUserPanel.add(nameLabel, c);

		c.gridx = 0;
		c.gridy = 4;
		innerUserPanel.add(name, c);

		c.gridx = 0;
		c.gridy = 5;
		innerUserPanel.add(new JLabel(" "), c);

		c.gridx = 0;
		c.gridy = 6;
		innerUserPanel.add(typeLabel, c);

		c.gridx = 0;
		c.gridy = 7;
		innerUserPanel.add(actorsSelection(), c);

		c.gridx = 0;
		c.gridy = 8;
		innerUserPanel.add(new JLabel(" "), c);

		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 9;
		innerUserPanel.add(loginButton, c);

		c.gridx = 1;
		c.gridy = 9;
		innerUserPanel.add(newUserButton, c);

		innerUserPanel.setBorder(BorderFactory
				.createEmptyBorder(20, 20, 20, 20));
		outerUserPanel.setBorder(BorderFactory.createEtchedBorder(Color.BLACK,
				Color.GRAY));

		return outerUserPanel;
	}
	
	public void notifySwitchViews(){
//		gui.switchView(this); 
	}

	public JComboBox actorsSelection() {

		Vector<Statut> comboBoxItems = new Vector<Statut>();

		for (Statut stat : Statut.values()) {
			comboBoxItems.add(stat);
		}

		final DefaultComboBoxModel<Statut> model = new DefaultComboBoxModel<Statut>(
				comboBoxItems);

		actors = new JComboBox<Statut>(model);

		actors.setBackground(Color.BLACK);
		actors.setForeground(GUIManager.THEME_COLOR);

		Object child = actors.getAccessibleContext().getAccessibleChild(0);
		BasicComboPopup popup = (BasicComboPopup) child;
		JList<Statut> list = popup.getList();
		list.setSelectionBackground(GUIManager.THEME_COLOR);
		list.setSelectionForeground(Color.BLACK);

		return actors;
	}

	public void loadRegisteredUser() {
		userName = name.getText().toLowerCase();
		userSurName = surName.getText().toLowerCase();
		Personne pers = gdd.chercherPersonne(userSurName, userName); 
		if (pers != null) {
			actors.setSelectedItem(pers.getStatut());
			actors.setEnabled(false);
			actors.setBackground(Color.BLACK); 
			loginButton.setEnabled(true); 
			newUserButton.setEnabled(false); 
		} else {
			actors.setEnabled(true);
			loginButton.setEnabled(false); 
			newUserButton.setEnabled(true);
		}
	}

	public class CreateUser implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!((name.getText().equals("")) || surName.getText().equals(""))){
				gdd.creerCompte(new Personne(userName, userSurName,
						(Statut) actors.getSelectedItem())); 
				loginButton.setEnabled(true); 
				newUserButton.setEnabled(false); 
			}
				
		}

	}

	public class Connect implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!((name.getText().equals("")) || surName.getText().equals(""))){
				notifySwitchViews(); 
			}
		}

	}

	public class CheckUserName implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			loadRegisteredUser();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			loadRegisteredUser();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			loadRegisteredUser();
		}

	}

}
