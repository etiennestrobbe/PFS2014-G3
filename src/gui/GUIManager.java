package gui;

import java.awt.Color;

import javax.swing.JFrame;

import model.bdd.GestionnaireStock;

public class GUIManager {

	public final static Color THEME_COLOR = new Color(0xFF8000); 
	Login login; 
	Account account; 
	public GUIManager(GestionnaireStock gdd) {

		login = new Login(gdd, this); 
		account = new Account(gdd, this); 
	}
	
	public void switchView(JFrame f){
		
		if (f == login){
			login.setVisible(false);
			account.setVisible(true);
		}
		
		if (f == account){
			login.setVisible(true); 
			account.setVisible(false);
		}
	}

}
