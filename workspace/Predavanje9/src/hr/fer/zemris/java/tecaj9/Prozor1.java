package hr.fer.zemris.java.tecaj9;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor1 extends JFrame{
	private static final long serialVersionUID = 2477477019112082429L;

	public Prozor1() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(300, 300);
		setLocation(100, 100);
		
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				int odluka = JOptionPane.showConfirmDialog(
						Prozor1.this,
						"Jeste li sigurni da želite zatvori prozor?",
						"Poruka sustava",
						JOptionPane.YES_NO_OPTION);
				if(odluka != JOptionPane.YES_OPTION){
					return;
				}
				dispose();
			}
		});
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Prozor1().setVisible(true);
		});
	}
}
