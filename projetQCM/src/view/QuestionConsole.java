package view;

import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import controller.ProjetController;
import model.ProjetModel;

/**
 * Cette classe affiche les questions en console. Elle va attendre la r�ponse du joueur et v�rifier cette r�ponse.
 * @author B
 *
 */

public class QuestionConsole extends ProjetVue implements Observer{
	protected Scanner sc;
	protected boolean arreter = true;
	private int i;
	public QuestionConsole(ProjetModel model, ProjetController controller) {
		super(model, controller);
		update(null, null);
		sc = new Scanner(System.in);
		new Thread (new ReadInput()).start();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println(model.toString(2));
		affiche("Choisis la bonne r�ponse en tappant 1, 2, 3 ou 4 (tu as 10 secondes)");
	}

	@Override
	public void affiche(String msg) {
		System.out.println(msg);
	}

	/**
	 * Cette classe est utilis� par le thread;
	 * Elle va scanner ce que le joueur a entr� comme commande et appeler la m�thode repondre pour v�rifier l'exactitude.
	 * @author B
	 *
	 */
	private class ReadInput implements Runnable{
		public void run() {
			while(arreter){
				try{
					String c = sc.next();
					repondre(c);
				}
				catch(InputMismatchException e){
					affiche("Format d'input incorrect");
				}
			}
		}
	}

	/**
	 * Cette m�thode va construire la r�ponse du joueur (rep + un num�ro).
	 * Ceci va me permettre de comparer plus facilement avec la bonne r�ponse dans le mod�le.
	 * Elle lancer ensuite la question suivante apr�s la v�rification.
	 * @param rep C'est la r�ponse que le joueur a rentr� (1, 2, 3 ou 4).
	 */
	public void repondre(String rep) {
		i++;
		if(i == 5) {arreter = false;}
		controller.verification("rep" + rep);
		try {
			controller.questionSuivante();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	@Override
	public void affiche() {
		
	}
}
