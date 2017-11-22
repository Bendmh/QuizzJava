package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import lombok.Getter;
import lombok.Setter;
/**
 * Cette classe va permettre d'obtenir les questions du QCM. Les questions vont �tre afficher dans la console et dans la partie GUI �galement.
 * Groupe 12
 * @author Benoit de Mahieu
 * @author Jonathan Gossens
 *Classe: 2TL2
 *J'utilise aussi le Jar Lombok qui permet de g�n�rer les getter et setter sans les �crire
 */
@Getter
@Setter
public class DemandeQuestions extends Observable{
	protected String question;
	protected String bonneReponse;
	protected String rep1;
	protected String rep2;
	protected String rep3;
	protected String rep4;
	
	
	protected List<String> questions = new ArrayList<String>();
	protected List<String> rep = new ArrayList<String>();
	protected List<String> reponses = new ArrayList<String>();
	
	/**
	 * Ce constructeur se connecte � la DB et cr�� un tableau de questions(3 pour le moment), un tableau avec toutes les r�ponses 
	 * et pour finir un tableau permettant de m�langer les r�ponses. 
	 * @param j Ce param�tre va permettre de choisir entre les 3 questions s�lectionn�es dans la DB
	 * @throws ClassNotFoundException Cette exception arrive quand il n'y a pas de r�sultat retourn� de la DB.
	 * @throws SQLException Cette exception permet de signaler lorsque la connexion a la DB �choue.
	 */
	public DemandeQuestions() throws ClassNotFoundException, SQLException {
		
		Class.forName("org.postgresql.Driver");
		Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testDB", "postgres", "postgres");
		  
		PreparedStatement st = db.prepareStatement("select question, rep1, rep2, rep3, rep4 FROM public.\"Questions\" where type = 'f' and niveau = 1 order by random() fetch first 3 rows only");
		  ResultSet rs = st.executeQuery();
		  while (rs.next()) {
			  questions.add(rs.getString(1));
			  rep.add(rs.getString(2));
			  rep.add(rs.getString(3));
			  rep.add(rs.getString(4));
			  rep.add(rs.getString(5));
		  }
		  rs.close();
		  st.close();	
	}
		
	/**
	 * Cette m�thode me permet de comparer la r�ponse choisie par le joueur avec la bonne r�ponse.
	 * @param rep rep correspond � la r�ponse renvoy� par le joueur(rep1, rep2, rep3 ou rep4)
	 * @return Retourne vrai si c'est la bonne r�ponse, faux dans le cas contraire
	 */
	public boolean comparaison(String rep) {
		if(rep == bonneReponse) {
			notifyObservers();
			return true;
		}
		return false;
	}
	
	/**
	 * Cr�ation de la question choisis et du tableau avec les r�ponses correspondantes.
	 * On a la bonne r�ponse qui est choisis � cet endroit.
	 * @param j j est le choix de la question allant de 0->4 au final
	 */
	public void questionSuivante(int j) {
		question = questions.get(j);
		reponses.clear();
		reponses.add(rep.get(4*j));
		reponses.add(rep.get(4*j+1));
		reponses.add(rep.get(4*j+2));
		reponses.add(rep.get(4*j+3));
		bonneReponse = rep.get(4*j);
		
		Collections.shuffle(reponses);
		
		rep1 = reponses.get(0);
		rep2 = reponses.get(1);
		rep3 = reponses.get(2);
		rep4 = reponses.get(3);
		
		if (rep1.equals(bonneReponse)) {
			bonneReponse = "rep1";
		}
		else if (rep2.equals(bonneReponse)) {
			bonneReponse = "rep2";
		}
		else if (rep3.equals(bonneReponse)) {
			bonneReponse = "rep3";
		}
		else {
			bonneReponse = "rep4";
		}
	}
	
	/**
	 * Cette m�thode retourne la question et les r�ponses en console sous la forme textuelle.
	 */
	@Override
	public String toString() {
		 return ("Question : " + this.getQuestion() + "\n1)" + this.getRep1() + "\n2)" + this.getRep2() + "\n3)" + this.getRep3() + "\n4)" + this.getRep4());
	}
}
