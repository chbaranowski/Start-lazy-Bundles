package utils.startlazybundles;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

public class LazyBundlesActivation implements BundleTrackerCustomizer {
	
	BundleTracker bundleTracker;

	BundleContext context;
	
	void start(BundleContext context) {
		this.context = context;
		bundleTracker = new BundleTracker(context, Bundle.STARTING, this);
		bundleTracker.open();
	}

	void stop() {
		bundleTracker.close();
		bundleTracker = null;
		context = null;
	}

	@Override
	public Object addingBundle(Bundle bundle, BundleEvent event) {
		if(isLazyBundle(bundle) && hasToActivateLazyBundle(bundle)){
			tryStartBundle(bundle);
			return bundle;
		}
		return null;
	}

	@Override
	public void modifiedBundle(Bundle bundle, BundleEvent event, Object object) {
		if(hasToActivateLazyBundle(bundle)){
			tryStartBundle(bundle);
		}
	}

	@Override
	public void removedBundle(Bundle bundle, BundleEvent event, Object object) {
		
	}
	
	void tryStartBundle(Bundle bundle) {
		try {
			bundle.start();
		} catch (BundleException e) {
			e.printStackTrace();
		}
	}
	
	boolean isLazyBundle(Bundle bundle){
		Object bundleActivationPolicy = bundle.getHeaders().get(Constants.BUNDLE_ACTIVATIONPOLICY);
		return Constants.ACTIVATION_LAZY.equals(bundleActivationPolicy);
	}
	
	boolean hasToActivateLazyBundle(Bundle bundle){
		String propertyValue = context.getProperty(bundle.getSymbolicName());
		return propertyValue != null && 
			propertyValue.contains("activate") &&
			bundle.getState() != Bundle.ACTIVE && 
			bundle.getState() >= Bundle.RESOLVED;
	}

}
