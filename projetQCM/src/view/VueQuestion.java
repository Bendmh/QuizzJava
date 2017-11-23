package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.ProjetController;
import model.ProjetModel;

public class VueQuestion extends ProjetVue implements ActionListener{
	
	private JTextField text;
	private JTextArea question;
	private JButton rep1;
	private JButton rep2;
	private JButton rep3;
	private JButton rep4;
	private int i=0;
		
	public VueQuestion(ProjetModel model, ProjetController controller) {
		super(model, controller);
		JFrame projetJFrame = new JFrame();
		model.questionSuivante(i);
		projetJFrame.setTitle("Page des questions");
		//projetJFrame.setResizable(false); // impossible � redimensionner
		projetJFrame.setSize(400, 200);
		//projetJFrame.setLocationRelativeTo(null); Pour afficher au milieu mais mettre apr�s setSize
		projetJFrame.setLocation(700, 50); //(horizontal, vertical)
		projetJFrame.setAlwaysOnTop(true);
		projetJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		projetJFrame.setBackground(Color.BLUE);
		projetJFrame.setVisible(true);
		
		Box main = Box.createVerticalBox();
		Container contentpane = projetJFrame.getContentPane();
		contentpane.add (main);
		
		Color c_bleu = new Color(0,191,255);
		question = new JTextArea (model.getQuest().getQuestion()); 
		question.setPreferredSize (new Dimension (400, 100));
		question.setBackground(c_bleu);
		question.setForeground(Color.WHITE);
		question.setEditable (false); 
		main.add(question);
		
		Box bottom = Box.createHorizontalBox(); 
		main.add(bottom);
		
		Box bottom1 = Box.createHorizontalBox(); 
		main.add(bottom1);
		
		text = new JTextField (""); 
		text.setPreferredSize (new Dimension (250, 50));
		text.setBackground(Color.lightGray);
		main.add(text);
		
		rep1 = new JButton ();
		bottom.add(rep1);
		rep2 = new JButton (); 
		bottom.add(rep2);
		
		rep3 = new JButton (); 
		bottom1.add(rep3);
		rep4 = new JButton (); 
		bottom1.add(rep4);
		afficherTexte();
		
		rep1.addActionListener(this);
		rep2.addActionListener(this);
		rep3.addActionListener(this);
		rep4.addActionListener(this);
		
	}
	
	public void afficherTexte() {
		question.setText(model.getQuest().getQuestion());
		rep1.setText(model.getQuest().getRep1());
		rep2.setText(model.getQuest().getRep2());
		rep3.setText(model.getQuest().getRep3());
		rep4.setText(model.getQuest().getRep4());
	}
	
	
	
		
	@Override
	public void update(Observable arg0, Object arg1) {
		
	}

	@Override
	public void affiche(String msg) {
		text.setText(msg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == rep1) {
			controller.verification("rep" + 1);
			if(i<2) {
				i++;
				model.questionSuivante(i);;
				afficherTexte();
			}
			else {
				affiche("C'est termin�");
			}
		}
		else if(e.getSource() == rep2) {
			controller.verification("rep" + 2);
			if(i<2) {
				i++;
				model.questionSuivante(i);
				afficherTexte();
			}
			else {
				affiche("C'est termin�");
			}
		}
		else if(e.getSource() == rep3){
			controller.verification("rep" + 3);
			if(i<2) {
				i++;
				model.questionSuivante(i);
				afficherTexte();
			}
			else {
				affiche("C'est termin�");
			}
		}
		else if(e.getSource() == rep4){
			controller.verification("rep" + 4);
			if(i<2) {
				i++;
				model.questionSuivante(i);
				afficherTexte();
			}
			else {
				affiche("C'est termin�");
			}
		}
	}

}
