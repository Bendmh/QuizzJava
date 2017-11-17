package controller;

import model.DemandeQuestions;
import model.Joueur;
import view.ProjetVue;

public class ProjetController {
	DemandeQuestions model;
	Joueur modelJoueur;
	ProjetVue vue;
	
	public ProjetController(DemandeQuestions model) {
		this.model = model;
	}
	
	public ProjetController(Joueur modelJoueur) {
		this.modelJoueur = modelJoueur;
	}
	
	public void addview(ProjetVue vue) {
		this.vue = vue;
	}
	
	public void verification(String choix) {
		System.out.println(choix);
		if(model.comparaison(choix)) {
			vue.affiche("Bonne r�ponse");
		}
		else {
			vue.affiche("Mauvaise r�ponse");
		}
	}
	
	public boolean verifIdentite(String pseudo) {
		if(modelJoueur.verifIdentifier(pseudo)) {
			vue.affiche("Cette identifiant existe d�j�");
			return false;
		}
		else {
			vue.affiche("Identifiant correct");
			return true;
		}
	}
	
	public boolean verifconnecte(String pseudo, String prenom) {
		if(modelJoueur.verifConnecter(pseudo, prenom)) {
			vue.affiche("Cette compte est correct");
			return true;
		}
		else {
			vue.affiche("Identifiant ou prenom incorrect");
			return false;
		}
	}
}
