package bundle.c;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		System.err.println("The bundle C should never be started");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.err.println("The bundle C should never be stopped");
	}

}