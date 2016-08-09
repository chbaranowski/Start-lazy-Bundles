# Start lazy Bundles

This util bundle could be used to start buggy bundles with a Bundle-ActivationPolicy lazy.
 
To start a lazy bundle the system property "activate.lazy.bundles" must contain the symbolic name of the bundle.
Format of the system property is a comma separated string. Only bundles with the Bundle-ActivationPolicy lazy
and which are in defined in the comma separated list will be started by this util bundle.

Example set system property in a bnd run configuration:

		-runproperties: activate.lazy.bundles=" \
			test.bundle.a, \
			test.bundle.b"
			
Full bnd example to start the lazy equinox 3.8 console bundle:

	-runfw: org.eclipse.osgi;version='[3.8.0.v20120123-1419,3.8.0.v20120123-1419]'
	-runee: JavaSE-1.6
	-runbundles: \
		org.eclipse.equinox.console,\
		org.apache.felix.gogo.runtime,\
		org.apache.felix.gogo.shell,\
		utils.startLazyBundles
	-runproperties:\
		activate.lazy.bundles="org.eclipse.equinox.console",\
		osgi.console=""
	
	
## Download 

Download the "Start Lazy Bundles" utils bundle from: [here](https://github.com/tux2323/Start-lazy-Bundles/blob/master/cnf/repo/utils.startLazyBundles/utils.startLazyBundles-1.0.0.jar?raw=true
)

## Build

Build the bundle with apache ant version > 1.8 

	git clone
	
	cd utils.startLazyBundles
	
	ant clean build

## License

Apache License, Version 2.0 (current) 
	
	http://www.apache.org/licenses/LICENSE-2.0
