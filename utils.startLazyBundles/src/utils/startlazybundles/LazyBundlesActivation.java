package utils.startlazybundles;

import static org.osgi.framework.Constants.*;

import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;

import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

public class LazyBundlesActivation implements BundleTrackerCustomizer {

	private final Set<String> lazyBundlesActivate;
	private final BundleTracker bundleTracker;

	public LazyBundlesActivation(BundleContext context, Set<String> lazyBundlesActivate) {
		this.lazyBundlesActivate = lazyBundlesActivate;
		this.bundleTracker = new BundleTracker(context, Bundle.STARTING, this);
	}

	@Override
	public Object addingBundle(Bundle bundle, BundleEvent event) {
		Object bundleActivationPolicy = bundle.getHeaders().get(BUNDLE_ACTIVATIONPOLICY);
		if(ACTIVATION_LAZY.equals(bundleActivationPolicy)) {
			if(lazyBundlesActivate.contains(bundle.getSymbolicName())) {
				tryStartBundle(bundle);
				return bundle;
			}
		}
		return null;
	}

	protected void tryStartBundle(Bundle bundle) {
		try {
			bundle.start();
		} catch (BundleException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modifiedBundle(Bundle bundle, BundleEvent event, Object object) {}

	@Override
	public void removedBundle(Bundle bundle, BundleEvent event, Object object) {}
	
	public void start() {
		bundleTracker.open();
	}
	
	public void stop() {
		bundleTracker.close();
	}

}
