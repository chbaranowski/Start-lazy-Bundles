package utils.startlazybundles;

import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static final String ACTIVATE_LAZY_BUNDLES = "activate.lazy.bundles";

	private LazyBundlesActivation lazyBundlesActivation;
	
	@Override
	public void start(final BundleContext context) throws Exception {
		final String activateBundles = context.getProperty(ACTIVATE_LAZY_BUNDLES);
		if(activateBundles != null) {
			Set<String> lazyBundlesToActivate = extractBundlesSymbolicNames(activateBundles);
			lazyBundlesActivation = new LazyBundlesActivation(context, lazyBundlesToActivate);
			lazyBundlesActivation.start();
		}
	}

	private Set<String> extractBundlesSymbolicNames(final String activateBundles) {
		Set<String> lazyBundlesToActivate = new HashSet<String>();
		String[] bundleSymbolicNames = activateBundles.split(",");
		for (String symbolicName : bundleSymbolicNames) {
			lazyBundlesToActivate.add(symbolicName.trim());
		}
		return lazyBundlesToActivate;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if(lazyBundlesActivation != null) {
			lazyBundlesActivation.stop();
		}
	}

}
