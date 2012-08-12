package utils.startlazybundles;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class LazyBundleController implements BundleActivator {
	
	final LazyBundlesActivation activation = new LazyBundlesActivation();
	
	@Override
	public void start(BundleContext context) throws Exception {
		activation.start(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		activation.stop();
	}

}
