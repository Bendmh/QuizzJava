package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;

import lombok.Getter;
import lombok.Setter;
import view.ProjetVue;

/**
 * Cette classe impl�mente un joueur qui a un pseudo, un pr�nom, des points et un level
 * Groupe 12
 * @author Jonathan Goossens 2TL2
 * @author Benoit de Mahieu 2TL2
 */

@Getter
@Setter
public class Joueur extends Observable{
	private String pseudo;
	private String prenom;
	private int point;
	private int level;
	private int nivMath;
	private int nivInfo;
	private int nivElec;
	
	/**
	 * Ce constructeur cr�e un joueur et l'ajoute dans la base de donn�e
	 * @param pseudo unique
	 * @param prenom 
	 */
	
	public Joueur() {
		
	}
	
	public Joueur(String pseudo, String prenom) {
		this.pseudo = pseudo;
		this.prenom = prenom;
		point = 0;
		nivInfo = 1;
		nivMath = 1;
		nivElec = 1;
		try {
			Class.forName("org.postgresql.Driver");
			Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testDB", "postgres", "postgres");
			Statement st = db.createStatement();
			st.executeQuery("INSERT INTO public.\"Joueur\"(\r\n" + 
											"	identifiant, prenom)\r\n" + 
											"	VALUES ('"+ this.pseudo +"', '"+ this.prenom +"');");
		} catch (SQLException | ClassNotFoundException e) {
			
		}
	}
	
	
	public boolean verifIdentifier(String pseudo) {
		try {
			Class.forName("org.postgresql.Driver");
			Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testDB", "postgres", "postgres");
			Statement st = db.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM public.\"Joueur\" ");
			while(rs.next()) {
				if(pseudo.equals(rs.getString(1))) {
					return true;
				}
			} 
		} 
		catch (ClassNotFoundException | SQLException e) {
			
		}
			return false;
	}
	
	
	public boolean verifConnecter(String pseudo, String prenom) {
		try {
			Class.forName("org.postgresql.Driver");
			Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testDB", "postgres", "postgres");
			Statement st = db.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM public.\"Joueur\" ");
			while(rs.next()) {
				if(pseudo.equals(rs.getString(1))&& prenom.equals(rs.getString(2))) {
					return true;
				}
			} 
		} 
		catch (ClassNotFoundException | SQLException e) {
			
		}
			return false;
	}
}