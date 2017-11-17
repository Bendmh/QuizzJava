package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Joueur;

class JoueurTest {
	
	@Test
	void testToString() {
		Joueur Jon = new Joueur("JonGoo","Jonathan");
		assertEquals("Pseudo: JonGoo\nPr�nom: Jonathan\nPoint: 0\nLevel: 1", Jon.toString());
		Jon.setPoint(20);
		assertEquals("Pseudo: JonGoo\nPr�nom: Jonathan\nPoint: 20\nLevel: 1", Jon.toString());
	}
	@Test
	void testVerifJoueur() {
		Joueur Jon = new Joueur("Jon","Jonathan");
		//Si la base de donn�e est allum�e sinon, il faut faire un assetFalse !
		assertTrue(Jon.verifJoueur(Jon.getPseudo(), Jon.getPrenom()));
	}
	
	
}
