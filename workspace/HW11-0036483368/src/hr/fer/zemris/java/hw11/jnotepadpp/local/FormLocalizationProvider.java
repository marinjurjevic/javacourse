package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * FormLocalizationProvider is provider that services one JFrame. When JFrame is
 * created, FLP will connect it to itself, providing connection between JFrame
 * actions and main LocalizationProvider. When JFrame is closed, FLP will
 * disconnect from main LP and will prevent memory leaks
 * 
 * @author Marin Jurjevic
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Creates new FormLocalizationProvider object that will automatically
	 * connects itself to main LP on window creation and disconnect on window
	 * closure.
	 * 
	 * @param provider
	 *            main LP provider
	 * @param frame
	 *            instance of JFrame
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}

			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
		});
	}
}
