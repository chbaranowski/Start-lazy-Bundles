package utils.startlazybundles.tests;

import java.io.File;
import java.io.FileInputStream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import junit.framework.TestCase;

public class LazyBundleControllerTest extends TestCase {
	
	final static BundleContext bundleContext = 
			FrameworkUtil.getBundle(LazyBundleControllerTest.class).getBundleContext();
	
	public void testBundleAStateIsActive() throws Exception {
		assertNotNull(bundleContext);
		Bundle bundleA = findBundle("utils.startLazyBundles.test.bundles.a");
		assertEquals(Bundle.ACTIVE, bundleA.getState());
	}
	
	public void testInstallAndStartLazyBundleB() throws Exception {
		String fileName = "../utils.startLazyBundles.test.bundles/generated/utils.startLazyBundles.test.bundles.b.jar";
		File bundleBFile = new File(fileName);
		assertTrue("Bundle B must exist.", bundleBFile.exists());
		Bundle bundleB = bundleContext.installBundle("utils.startLazyBundles.test.bundles.b", new FileInputStream(bundleBFile ));
		bundleB.start(Bundle.START_ACTIVATION_POLICY);
		assertEquals(Bundle.ACTIVE, bundleB.getState());
	}
	
	public void testInstallAndNotActivateBundleC() throws Exception {
		String fileName = "../utils.startLazyBundles.test.bundles/generated/utils.startLazyBundles.test.bundles.c.jar";
		File bundleCFile = new File(fileName);
		assertTrue("Bundle C must exist.", bundleCFile.exists());
		Bundle bundleC = bundleContext.installBundle("utils.startLazyBundles.test.bundles.c", new FileInputStream(bundleCFile));
		bundleC.start(Bundle.START_ACTIVATION_POLICY);
		assertEquals(Bundle.STARTING, bundleC.getState());
	}
	
	Bundle findBundle(String symbolicName){
		Bundle[] bundles = bundleContext.getBundles();
		for (Bundle bundle : bundles) {
			if(bundle.getSymbolicName().equals(symbolicName)){
				return bundle;
			}
		}
		throw new RuntimeException("No Bundle found with symbolic name " + symbolicName);
	}
	
}
