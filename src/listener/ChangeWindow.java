package listener;

import gui.AjoutChoix;
import gui.GUIManager;
import gui.GestionChoix;
import gui.Login;
import gui.SuppressionMateriel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ChangeWindow implements ActionListener{
	
	private JFrame frame;
	
	public ChangeWindow(JFrame f){
		this.frame = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton buttonPushed = (JButton)e.getSource();
		if(frame instanceof AjoutChoix){
			if(buttonPushed.equals(((GestionChoix)frame).getFirstButton())){
				// TODO aller a fenetre d'ajout deja existant
			}
			else if(buttonPushed.equals(((GestionChoix)frame).getSecondButton())){
				// TODO aller a la fenetre d'ajout d'un nouveau
			}
			else if(buttonPushed.equals(((GestionChoix)frame).getThirdButton())){
				((AjoutChoix)frame).notifySwitchViews(new GestionChoix(((GestionChoix)frame).getGdd(),((GestionChoix)frame).getGui(),GUIManager.listTexte));
			}
		}
		else if(frame instanceof GestionChoix){
			if(buttonPushed.equals(((GestionChoix)frame).getFirstButton())){
				LinkedList<String> t = new LinkedList<String>();
				t.add("Ajouter un materiel au stock");
				t.add("Deja existant : ");
				t.add("Nouveau materiel : ");
				t.add("Existant");
				t.add("Nouveau");
				((GestionChoix)frame).notifySwitchViews(new AjoutChoix(((GestionChoix)frame).getGdd(),((GestionChoix)frame).getGui(),t));
			}
			else if(buttonPushed.equals(((GestionChoix)frame).getSecondButton())){
				((GestionChoix)frame).notifySwitchViews(new SuppressionMateriel(((GestionChoix)frame).getGdd(),((GestionChoix)frame).getGui()));
			}
			else if(buttonPushed.equals(((GestionChoix)frame).getThirdButton())){
				((GestionChoix)frame).notifySwitchViews(new Login(((GestionChoix)frame).getGdd(),((GestionChoix)frame).getGui()));
			}
		}
		
	}

}
