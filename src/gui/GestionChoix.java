package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
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

import listener.ChangeWindow;
import model.bdd.GestionnaireStock;
import personne.Personne;
import personne.Statut;

public class GestionChoix extends JFrame {

	private GestionnaireStock gdd;
	private GUIManager gui; 
	private JButton addButton;
	private JButton removeButton;
	private JButton backButton;
	private LinkedList<String> texte;


	public GestionChoix(GestionnaireStock gdd, GUIManager gui, LinkedList<String> texte) { 
		
		this.gdd = gdd;
		this.gui = gui; 
		this.texte=texte;

		
		getContentPane().setBackground(Color.DARK_GRAY);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container mainFrame = getContentPane();

		mainFrame.add(gestionChoix(), BorderLayout.CENTER);

		setVisible(true);
		
		setResizable(false);
		
		setLocation(500, 250);  

		pack();

		
	}

	public JPanel gestionChoix() {
		JPanel gestionChoix = new JPanel(new GridLayout(4,1));
		JPanel ajouterPanel = new JPanel(new GridLayout(2,1));
		JPanel supprimerPanel = new JPanel(new GridLayout(2,1));
		JPanel retourPanel = new JPanel(new GridLayout(2,1));

		
		JLabel title = new JLabel(this.texte.get(0));
		JLabel addTitle = new JLabel(texte.get(1));
		JLabel removeTitle = new JLabel(texte.get(2));
		JLabel backTitle = new JLabel("Retour au menu precedent");
		
		
		addButton = new JButton(texte.get(3));
		addButton.setBackground(Color.BLACK);
		addButton.setForeground(GUIManager.THEME_COLOR);
		addButton.addActionListener(new ChangeWindow(this));
		removeButton = new JButton(texte.get(4));
		removeButton.setBackground(Color.BLACK);
		removeButton.setForeground(GUIManager.THEME_COLOR);
		removeButton.addActionListener(new ChangeWindow(this));
		backButton = new JButton("Retour");
		backButton.setBackground(Color.BLACK);
		backButton.setForeground(GUIManager.THEME_COLOR);
		backButton.addActionListener(new ChangeWindow(this));
		
		ajouterPanel.add(addTitle);
		ajouterPanel.add(addButton);
		ajouterPanel.setBackground(GUIManager.THEME_COLOR);	
		
		supprimerPanel.add(removeTitle);
		supprimerPanel.add(removeButton);
		supprimerPanel.setBackground(GUIManager.THEME_COLOR);	
		
		retourPanel.add(backTitle);
		retourPanel.add(backButton);
		retourPanel.setBackground(GUIManager.THEME_COLOR);
		
		gestionChoix.add(title);
		gestionChoix.add(ajouterPanel);
		gestionChoix.add(supprimerPanel);
		gestionChoix.add(retourPanel);
		
		gestionChoix.setBackground(GUIManager.THEME_COLOR);		
		
		return gestionChoix;
	}
	
	public void notifySwitchViews(JFrame destination){
		gui.switchView(this,destination); 
	}
	
	public JButton getFirstButton(){
		return addButton;
	}
	
	public JButton getSecondButton(){
		return removeButton;
	}
	
	public JButton getThirdButton(){
		return backButton;
	}
	
	public GestionnaireStock getGdd(){
		return gdd;
	}
	
	public GUIManager getGui(){
		return gui;
	}

}
