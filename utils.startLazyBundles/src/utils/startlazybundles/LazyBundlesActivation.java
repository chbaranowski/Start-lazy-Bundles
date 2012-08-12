package utils.startlazybundles;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * Tracker class to start all lazy bundles which are marked by property or when
 * the property ACTIVATE_ALL_LAZY_BUNDLES is set to true all bundles will be
 * activated.
 */
public class LazyBundlesActivation implements BundleTrackerCustomizer {

	/**
	 * When this property is set to true all lazy bundles will be activated by
	 * this bundle.
	 */
	public static final String ACTIVATE_ALL_LAZY_BUNDLES = "utils.startlazybundles.activate.all";

	static final String TRUE_PROP_VALUE = "true";

	static final String ACTIVATE = "activate";

	BundleTracker bundleTracker;

	BundleContext context;

	/**
	 * Start the tracker with a given bundle context.
	 * 
	 * @param context the bundle context should not be null.
	 */
	void start(BundleContext context) {
		this.context = context;
		bundleTracker = new BundleTracker(context, Bundle.STARTING, this);
		bundleTracker.open();
	}

	/**
	 * Stops the bundle tracker.
	 */
	void stop() {
		bundleTracker.close();
		bundleTracker = null;
		context = null;
	}

	/**
	 * Method is called for all starting bundles which are added to the
	 * framework. When the bundle has to be started by this utils bundle the try
	 * start method is called.
	 * 
	 * Postcondition: When the bundle has to be activated the lazy bundle should
	 * be activate.
	 */
	@Override
	public Object addingBundle(Bundle bundle, BundleEvent event) {
		if (isLazyBundle(bundle) && hasToActivateLazyBundle(bundle)) {
			tryStartBundle(bundle);
			return bundle;
		}
		return null;
	}

	/**
	 * Method is called for all modified starting bundles. When the lazy bundle
	 * is not activate and the bundle has to activate then the try start method
	 * is called for the bundle.
	 * 
	 * Postcondition: When the bundle has to be activated the lazy bundle should
	 * be activate.
	 */
	@Override
	public void modifiedBundle(Bundle bundle, BundleEvent event, Object object) {
		if (hasToActivateLazyBundle(bundle)) {
			tryStartBundle(bundle);
		}
	}

	@Override
	public void removedBundle(Bundle bundle, BundleEvent event, Object object) {
		// DO NOTHING
	}

	/**
	 * Try to start the bundle.
	 * 
	 * @param bundle
	 *            the bundle which should be started.
	 */
	void tryStartBundle(Bundle bundle) {
		try {
			bundle.start();
		} catch (BundleException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks that the given bundles has a activation policy set to lazy.
	 * 
	 * @param bundle
	 *            the bundle which should be checked.
	 * 
	 * @return true if the bundle contains the header activation policy is set
	 *         to lazy.
	 */
	boolean isLazyBundle(Bundle bundle) {
		Object bundleActivationPolicy = bundle.getHeaders().get(
				Constants.BUNDLE_ACTIVATIONPOLICY);
		return Constants.ACTIVATION_LAZY.equals(bundleActivationPolicy);
	}

	/**
	 * Checks that the given bundle should be activate.
	 * 
	 * @param bundle
	 *            the bundle to check.
	 * 
	 * @return true if all lazy bundles should be activated or the bundle is
	 *         marked by property to be activated. And the bundle is not in the
	 *         state active.
	 */
	boolean hasToActivateLazyBundle(Bundle bundle) {
		return (hasToActivateAllLazyBundles() || isBundleMarkedToActivate(bundle))
				&& bundle.getState() != Bundle.ACTIVE
				&& bundle.getState() >= Bundle.RESOLVED;
	}

	/**
	 * Checks that the bundle is marked by property to be activated.
	 * 
	 * @param bundle
	 *            the bundle to check.
	 * @return true if the property with the name of the bundle contains the
	 *         value ACTIVATE.
	 */
	boolean isBundleMarkedToActivate(Bundle bundle) {
		String propertyValue = context.getProperty(bundle.getSymbolicName());
		return propertyValue != null && propertyValue.contains(ACTIVATE);
	}

	/**
	 * Checks that the property ACTIVATE_ALL_LAZY_BUNDLES is set.
	 * 
	 * @return true if the the property ACTIVATE_ALL_LAZY_BUNDLES is set to
	 *         true.
	 */
	boolean hasToActivateAllLazyBundles() {
		String propertyValue = context.getProperty(ACTIVATE_ALL_LAZY_BUNDLES);
		return TRUE_PROP_VALUE.equals(propertyValue);
	}

}
