package listener;

import gui.Test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.JList;

import model.stock.MaterielStock;

public class ActionMajStock implements ActionListener {
	
	private Test frame;
	
	public ActionMajStock(Test f){
		this.frame = f;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> comboSelected = (JComboBox<String>)e.getSource();
		if(comboSelected.equals(frame.getComboName())){
			// on met a jour le stock en fonction du nom choisi
			frame.setCurrentStock(frame.getGestionStock().elementsWhichNameIs((String)frame.getComboName().getSelectedItem(),frame.getGestionStock().getStock()));
			frame.setStockAfterSelectedName( (LinkedList<MaterielStock>) frame.getCurrentStock().clone());
			// TODO afficher la liste des elements mis a jour
			
			//frame.setListStock(new JList<String>(frame.inStockList2()));
			
			// on met a jour le second comboBox
			frame.fetchComboPropertie(frame.getStockAfterSelectedName(),frame.getComboProprieties1());
			
		}
		else if(comboSelected.equals(frame.getComboProprieties1())){
			// TODO mettre a jour le comboBox2 et la liste des elements du stock restant
			// TODO enlever l'argument de la combo precedente (a faire dans le fetch)
			
			frame.setCurrentStock(frame.getGestionStock().elementsWhichHaveProperty((String)frame.getComboProprieties1().getSelectedItem(),frame.getGestionStock().getStock()));
			frame.setStockAfterSelectProperty1( (LinkedList<MaterielStock>) frame.getCurrentStock().clone());
			frame.fetchComboPropertie(frame.getStockAfterSelectProperty1(),frame.getComboProprieties2());
		}
		else if(comboSelected.equals(frame.getComboProprieties2())){
			// TODO mettre a jour le comboBox3 et la liste des elements du stock restant
		}
		else if(comboSelected.equals(frame.getComboProprieties3())){
			// TODO mettre a jour la liste des elements du stock restant
		}
		

	}

}
