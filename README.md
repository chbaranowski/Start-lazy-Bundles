# Start lazy Bundles

This util bundle could be used to start buggy lazy bundles, with Bundle-ActivationPolicy header lazy. 
The bundle could be used in bnd / bndtools or any other OSGi environment outside of bnd / bndtools. 

## How to use in bnd / bndtools

To use the util bundle in bnd / bndtools download the bundle from [here](https://github.com/tux2323/Start-lazy-Bundles/blob/bndtools-v2.0.0/cnf/releaserepo/utils.startLazyBundles/utils.startLazyBundles-2.0.0.jar?raw=true). 
And add the bundle in one of the workspace (cnf) repositories.

Now the util bundle could be used in bnd, for that add the bundle to the -runpath or in the -runbundles option.

bnd/bndtools Runpath Example:

	# Add the start lazy bundle to the runpath
	-runpath: utils.startLazyBundles;version='2.0.0'
	-runfw: org.eclipse.osgi;version='3.8.0'
	-runbundles: \
		org.eclipse.equinox.console,\
		org.apache.felix.gogo.runtime,\
		org.apache.felix.gogo.shell
	# Activate all lazy bundles with the symbolic name org.eclipse.equinox.console
	-runproperties: org.eclipse.equinox.console=activate,\
		osgi.console

bnd/bndtools Runbundles Example:

	-runfw: org.eclipse.osgi;version='3.8.0'
	# Add the start lazy bundles to the runbundles or runrequires option
	-runbundles: \
		utils.startLazyBundles;version='2.0.0',\
		org.eclipse.equinox.console,\
		org.apache.felix.gogo.runtime,\
		org.apache.felix.gogo.shell
	# Activate all lazy bundles with the symbolic name org.eclipse.equinox.console
	-runproperties: org.eclipse.equinox.console=activate,\
		osgi.console
		
See also the runbnd demos for the equinox 3.8 console in the utils.startLazyBundles project.

## Download 

Download the "Start Lazy Bundles" utils bundle from: [here](https://github.com/tux2323/Start-lazy-Bundles/blob/bndtools-v2.0.0/cnf/releaserepo/utils.startLazyBundles/utils.startLazyBundles-2.0.0.jar?raw=true). 