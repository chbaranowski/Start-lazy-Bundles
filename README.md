# Start lazy Bundles

This util bundle could be used to start buggy bundles with a Bundle-ActivationPolicy lazy.
 
To start a lazy bundle the system property "activate.lazy.bundles" must contain the symbolic name of the bundle.
Format of the system property is a comma separated string. Only bundles with the Bundle-ActivationPolicy lazy
and which are in defined in the comma separated list will be started by this util bundle.

Example define the system property in a bnd run configuration:

		-runproperties: activate.lazy.bundles=" \
			test.bundle.a, \
			test.bundle.b"
	
	
## Download 

Download the "Start Lazy Bundles" utils bundle from: TODO

## Build

Build the bundle with apache ant version > 1.8 

	git clone
	
	cd utils.startLazyBundles
	
	ant clean build

## License

Apache License, Version 2.0 (current) 
	
	http://www.apache.org/licenses/LICENSE-2.0