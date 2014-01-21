package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;

import model.bdd.GestionnaireStock;

public class AjoutChoix extends GestionChoix {

	public AjoutChoix(GestionnaireStock gdd, GUIManager gui,LinkedList<String> texte) {
		super(gdd, gui, texte);
		
	}

}
