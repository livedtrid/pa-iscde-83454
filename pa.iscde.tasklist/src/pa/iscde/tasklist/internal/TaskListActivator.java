package pa.iscde.tasklist.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class TaskListActivator implements BundleActivator {

	private static TaskListActivator instance;
	private ProjectBrowserServices projectBrowserServices;

	private static BundleContext context;

	public static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		instance = this;
		TaskListActivator.context = bundleContext;

		ServiceReference<ProjectBrowserServices> serviceReference = bundleContext.getServiceReference(ProjectBrowserServices.class);
		projectBrowserServices = bundleContext.getService(serviceReference);
		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		TaskListActivator.context = null;
	}

	public ProjectBrowserServices getProjectBrowserServices() {
		return projectBrowserServices;
	}

	public static TaskListActivator getInstance() {
		return instance;
	}

}
