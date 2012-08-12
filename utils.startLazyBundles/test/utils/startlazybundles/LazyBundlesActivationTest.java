package utils.startlazybundles;

import static org.mockito.Mockito.when;

import java.util.Dictionary;
import java.util.Properties;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

public class LazyBundlesActivationTest extends TestCase {
	
	LazyBundlesActivation sut = new LazyBundlesActivation();
	
	@Mock
	BundleContext mockContext;
	
	@Mock
	Bundle mockBundle;
	
	Dictionary<Object, Object> mockBundleHeaders = new Properties();

	@Override
	protected void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(mockBundle.getHeaders()).thenReturn(mockBundleHeaders);
		sut.start(mockContext);
	}
	
	public void testIsLazyBundle() {
		mockBundleHeaders.put(Constants.BUNDLE_ACTIVATIONPOLICY, Constants.ACTIVATION_LAZY);
		assertTrue(sut.isLazyBundle(mockBundle));
	}
	
	public void testIsLazyBundleFalse() {
		mockBundleHeaders.put(Constants.BUNDLE_ACTIVATIONPOLICY, "NOT LAZY");
		assertFalse(sut.isLazyBundle(mockBundle));
	}

	public void testHasToActivateLazyBundle_ACTIVATE_ALL_LAZY_BUNDLES_BundleStateStarting() {
		when(mockContext.getProperty(LazyBundlesActivation.ACTIVATE_ALL_LAZY_BUNDLES))
			.thenReturn(LazyBundlesActivation.TRUE_PROP_VALUE);
		when(mockBundle.getState()).thenReturn(Bundle.STARTING);
		assertTrue(sut.hasToActivateLazyBundle(mockBundle));
	}
	
	public void testHasToActivateLazyBundle_ACTIVATE_ALL_LAZY_BUNDLES_BundleStateActive() {
		when(mockContext.getProperty(LazyBundlesActivation.ACTIVATE_ALL_LAZY_BUNDLES))
			.thenReturn(LazyBundlesActivation.TRUE_PROP_VALUE);
		when(mockBundle.getState()).thenReturn(Bundle.ACTIVE);
		assertFalse(sut.hasToActivateLazyBundle(mockBundle));
	}
	
	public void testHasToActivateLazyBundle_ACTIVATE_ALL_LAZY_BUNDLES_BundleStateInstalled() {
		when(mockContext.getProperty(LazyBundlesActivation.ACTIVATE_ALL_LAZY_BUNDLES))
			.thenReturn(LazyBundlesActivation.TRUE_PROP_VALUE);
		when(mockBundle.getState()).thenReturn(Bundle.INSTALLED);
		assertFalse(sut.hasToActivateLazyBundle(mockBundle));
	}
	
	public void testHasToActivateLazyBundle_BundleStateStarting() {
		String symbolicName = "demo.bundle";
		when(mockBundle.getState()).thenReturn(Bundle.STARTING);
		when(mockBundle.getSymbolicName()).thenReturn(symbolicName);
		assertFalse(sut.hasToActivateLazyBundle(mockBundle));
	}
	
	public void testHasToActivateLazyBundle_BundleIsMarkedToActivate_BundleStateStarting() {
		String symbolicName = "demo.bundle";
		when(mockContext.getProperty(symbolicName))
			.thenReturn(LazyBundlesActivation.ACTIVATE);
		when(mockBundle.getState()).thenReturn(Bundle.STARTING);
		when(mockBundle.getSymbolicName()).thenReturn(symbolicName);
		assertTrue(sut.hasToActivateLazyBundle(mockBundle));
	}

}
