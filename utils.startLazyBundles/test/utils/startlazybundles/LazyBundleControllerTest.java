package utils.startlazybundles;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.osgi.framework.BundleContext;

import junit.framework.TestCase;

public class LazyBundleControllerTest extends TestCase {

	LazyBundleController sut = new LazyBundleController();
	
	@Mock
	BundleContext mockContext;
	
	@Override
	protected void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	public void testStart() throws Exception {
		sut.start(mockContext);
		assertTrue(sut.activation.isRunning());
	}
	
	public void testStart_TwoTimes() throws Exception {
		sut.start(mockContext);
		try {
			sut.start(mockContext);
			fail("IllegalStateException expected.");
		} catch(IllegalStateException exp){
			assertTrue("Illegal state expected the sut can't be start two times.",true);
		}
	}

	public void testStop() throws Exception {
		sut.start(mockContext);
		sut.stop(mockContext);
		assertFalse(sut.activation.isRunning());
	}
	
	public void testStop_BeforeSutIsRunning() throws Exception {
		try {
			sut.stop(mockContext);
			fail("IllegalStateException expected.");
		} catch(IllegalStateException exp){
			assertTrue("Illegal state expected the sut can't be stopped before the start method was called.", true);
		}
	}

}
