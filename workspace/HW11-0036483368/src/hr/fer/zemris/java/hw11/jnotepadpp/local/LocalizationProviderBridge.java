package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * LocalizationProviderBridge is a mid-layer class for preventing memory leaks
 * in multiframe applications. This class behaves like a bridge between
 * singleton object that is central component and each frame (consumer of
 * provided service).
 * 
 * @author Marin Jurjevic
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * is consumer connected on the bridge
	 */
	private boolean connected;

	/**
	 * reference to singleton object
	 */
	private ILocalizationProvider provider;

	/**
	 * Creates new bridge between provider and singleton
	 * 
	 * @param provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

	/**
	 * connects to decorated ILocalizationProvider, in this case singleton
	 */
	public void connect() {
		if (connected) {
			return;
		}
		provider.addLocalizationListener(() -> {
			this.fire();
		});
		connected = true;
	}

	/**
	 * disconnects from decorated ILocalizationProvider, in this case singleton
	 */
	public void disconnect() {
		connected = false;
		provider.removeLocalizationListener(() -> {
			this.fire();
		});
	}
}
