package bundle.b;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Start Bundle B");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stop Bundle B");
	}

}