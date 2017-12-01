package controller;

import java.awt.Color;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import lombok.Getter;
import lombok.Setter;
import model.ProjetModel;
import view.IntroConsole;
import view.ProjetVue;
import view.QuestionConsole;
import view.SujetConsole;
import view.VueIntro;
import view.VueQuestion;
import view.VueSujet;

/**
 * Cette classe est le controller du mod�le MVC, il sert � lier les vues aux diff�rents mod�les.
 * @author Jonathan Goossens 2TL2
 * @author Benoit de Mahieu 2TL2
 * On utilise aussi le Jar Lombok qui permet de g�n�rer les getter et setter sans les �crire
 */
@Getter
@Setter
public class ProjetController {
	
	private ProjetModel model;
	private ProjetVue vue;
	private ProjetVue console;
	private int i=0;
	private int points = 0;
	private int nombre;
	
	
	/**
	 * Constructeur qui instancie le model de ce pattern MVC
	 * @param model � instancier
	 */
	public ProjetController(ProjetModel model) {
		this.model = model;
	}
	
	/**
	 * Cette m�thode instancie une vue GUI
	 * @param vue � instancier
	 */
	public void addview(ProjetVue vue) {
		this.vue = vue;
	}
	
	/**
	 * Cette m�thode instancie une vue console
	 * @param vue console � instancier
	 */
	public void addview2(ProjetVue console) {
		this.console = console;
	}
	
	/**
	 * Cette m�thode va v�rifier la r�ponse � la question et fait donc appel � la m�thode comparaison de la classe ProjetModel
	 * @param choix de la r�ponse � la question
	 */
	public void verification(String choix) {
		if(model.comparaison(choix)) {
			console.affiche("Bonne r�ponse");
			vue.affiche("Bonne r�ponse");
			points++;
		}
		else {
			console.affiche("Mauvaise r�ponse");
			vue.affiche("Mauvaise r�ponse");
		}
	}
	
	/**
	 * Cette m�thode utilise la m�thode verifIdentifier de la classe ProjetModel pour pouvoir l'utiliser dans la vue
	 * @param identifiant � v�rifier
	 * @return false si le pseudo est en BDD et affiche que l'identifiant existe d�j� 
	 * @return true dans les autres cas et affiche que l'identifiant est correct
	 */
	public boolean verifIdentite(String identifiant) {
		if(model.verifIdentifier(identifiant)) {
			console.affiche("Cette identifiant existe d�j�");
			vue.affiche("Cette identifiant existe d�j�");
			return false;
		}
		else {
			console.affiche("Identifiant correct");
			vue.affiche("Identifiant correct");
			return true;
		}
	}
	
	/**
	 * Cette m�thode utilise la m�thode verifConnecter de la classe ProjetModel pour pouvoir l'utiliser dans la vue
	 * @param identifiant unique du joueur
	 * @param prenom qui permet la v�rification de la combinaison avec le pseudo 
	 * @return true si la combinaison est bonne et affiche que le compte est correct
	 * @return false dans les autres cas et affiche que l'identifiant ou le pr�nom est incorrect
	 */
	public boolean verifconnecte(String identifiant, String prenom) {
		if(model.verifConnecter(identifiant, prenom)) {
			console.affiche("Ce compte est correct");
			vue.affiche("Ce compte est correct");
			return true;
		}
		else {
			console.affiche("Identifiant ou prenom incorrect");
			vue.affiche("Identifiant ou prenom incorrect");
			return false;
		}
	}
	
	/**
	 * Cette m�thode utilise la m�thode questionSuivante de la classe ProjetModel pour pouvoir l'utiliser dans la vue
	 * 
	 */
	public void questionSuivante() {
		if(i<4) {
			i++;
			model.questionSuivante(i);
			vue.affiche();
		}
		else {
			console.affiche("C'est termin�");
			vue.affiche("C'est termin�");
			try {
				points = model.getJoueur().getPoint() + points;
				model.getQuest().changerPoints(model.getJoueur().getIdentifiant(), points);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} 
			vue.setVisible(false);
			PageSujet(model.getJoueur().getIdentifiant(), model.getJoueur().getPrenom());
		}
		
	}
	
	public boolean niv(String choix, int niveau) {
		if(choix.equals("info")) {
			if (model.getJoueur().getNivInfo() < niveau && model.getJoueur().getPoint() < 200)return true;
			return false;
		}
		if (choix.equals("math")) {
			if (model.getJoueur().getNivMath() < niveau && model.getJoueur().getPoint() < 200) return true;
			return false;
		}
		if (choix.equals("elec")) {
			if (model.getJoueur().getNivElec() < niveau && model.getJoueur().getPoint() < 200) return true;
			return false;
		}
		return false;
	}
	
	public boolean niveau(String choix, int niveau) {
		if (niveau == 2) {
			nombre = 200;
		}
		else {
			nombre = 400;
		}
		if (niv(choix, niveau)) {
			JOptionPane.showMessageDialog(null, "Pas assez de points.\nIl faut " + nombre + " points", "Erreur", JOptionPane.ERROR_MESSAGE); 
			System.out.println("Pas assez de points. Il faut " + nombre + " points");
			return false;
		}
		int points = model.getJoueur().getPoint() - nombre;
		try {
			model.getQuest().changerNiv(model.getJoueur().getIdentifiant(), choix, niveau);
			model.getQuest().changerPoints(model.getJoueur().getIdentifiant(), points);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return true;
	}
	
	/**
	 * Cette m�thode utilise la m�thode choixQuestion de la classe ProjetModel pour pouvoir l'utiliser dans la vue
	 * @param sujet choisi pour �tre interrog� dessus
	 * @param niveau de question qui sera pos�e dans le sujet choisi
	 */
	public void choixQuestion(String sujet, int niveau) {
		try {
			model.choixQuestion(sujet, niveau);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * Cette m�thode utilise la m�thode proposerQuestion de la classe ProjetModel qui pourra donc �tre utilis�e dans la vue
	 * @param q La question propos�e par le Joueur
	 * @param r1 La bonne r�ponse � la question
	 * @param r2 Une autre r�ponse
	 * @param r3 Une autre r�ponse
	 * @param r4 Une autre r�ponse
	 */
	public void proposeQuestion(String question, String r1, String r2, String r3, String r4) {
		model.proposerQuestion(question, r1, r2, r3, r4);
	}
	
	/**
	 * Cette m�thode va cr�er la page d'introduction en utilisant les constructeurs des classes IntroConsole et VueIntro
	 * Des modifiication � la vue GUI sont faites ici
	 */
	public void PageIntro() {
			
		ProjetController ctrlIntro = new ProjetController(model);
		console = new IntroConsole(model, ctrlIntro);
		((IntroConsole)console).affiche();
		ctrlIntro.addview2(console);
		vue = new VueIntro(model, ctrlIntro);
		ctrlIntro.addview(vue);
				
		vue.setTitle("ProjetQCM");
		vue.setLocation(700, 50); //(horizontal, vertical)
		vue.setAlwaysOnTop(true);
		vue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vue.setBackground(Color.BLUE);
		vue.setSize(500,300);
		vue.setVisible(true);
		vue.getContentPane().add(((VueIntro)vue).getIntro());
		
	}
	
	/**
	 * Cette m�thode va cr�er la page de choix de sujet en utilisant les constructeurs des classes SujetConsole et VueSujet
	 * Des modifiication � la vue GUI sont faites ici
	 * @param identifiant qui permettra de r�cup�rer le pr�nom, les points et les niveaux du joueur
	 */
	public void PageSujet(String identifiant, String prenom) {
		vue.setVisible(false);
		model.connecter(identifiant);
		/*System.out.println(((IntroConsole)console).isArret());
		((IntroConsole)console).setArret(false);
		System.out.println(((IntroConsole)console).isArret());*/
		ProjetController ctrlSujet = new ProjetController(model);
		console = new SujetConsole(model, ctrlSujet);
		ctrlSujet.addview2(console);
		console.affiche();
		
		vue = new VueSujet(model, ctrlSujet);

		ctrlSujet.addview(vue);
		
		vue.setTitle("ProjetQCM");
		vue.setLocation(700, 50); //(horizontal, vertical)
		//vue.setAlwaysOnTop(true);
		vue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vue.setBackground(Color.BLUE);
		vue.setSize(450,300);
		vue.setVisible(true);
		vue.getContentPane().add(((VueSujet)vue).getSujet());
	}
	
	/**
	 * Cette m�thode va cr�er la page d'affichage des questions en utilisant les constructeurs des classes QuestionConsole et VueQuestion
	 * Des modifiication � la vue GUI sont faites ici
	 */
	public void PageQuestions() {
		vue.setVisible(false);
		model.questionSuivante(0);
		ProjetController ctrlQuestion = new ProjetController(model);
		vue = new VueQuestion(model, ctrlQuestion);
		ctrlQuestion.addview(vue);
		console = new QuestionConsole(model, ctrlQuestion);
		ctrlQuestion.addview2(console);
		vue.setTitle("ProjetQCM");
		vue.setLocation(700, 50); //(horizontal, vertical)
		//vue.setAlwaysOnTop(true);
		vue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vue.setBackground(Color.BLUE);
		vue.setSize(450,300);
		vue.setVisible(true);
		vue.getContentPane().add(((VueQuestion)vue).getPanel());
	}
	
}
