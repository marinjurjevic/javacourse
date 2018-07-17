package hr.fer.zemris.tecaj.swing.p12;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor2  extends JFrame{

	private static final long serialVersionUID = 1L;

	public Prozor2(){
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor2");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
	}
	
	private void initGUI(){
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel labela = new JLabel("<html>Ovo je <u><font color='red'>Labela</font></u></html>");
		labela.setOpaque(true);
		labela.setBackground(Color.YELLOW);
		labela.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel buttonsPanel = new JPanel(new GridLayout(1,0));
		
		cp.add(labela, BorderLayout.CENTER);
		cp.add(buttonsPanel, BorderLayout.PAGE_END);
		
		ActionListener action = a -> {
			JButton b = (JButton)a.getSource();
			labela.setText(b.getText());
		};
		
		JButton[] buttons = new JButton[4];
		for(int i = 0; i<4; i++){
			buttons[i] = new JButton("Gumb "+(i+1) );
			buttons[i].addActionListener(action);
			buttonsPanel.add(buttons[i]);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> new Prozor2().setVisible(true));
		
	}
	
	
}
