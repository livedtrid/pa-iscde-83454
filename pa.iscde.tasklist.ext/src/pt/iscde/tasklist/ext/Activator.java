package pt.iscde.tasklist.ext;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class Activator implements BundleActivator {

	private static Activator instance;
	private JavaEditorServices editorServ;

	private static BundleContext context;

	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		Activator.context = context;

		ServiceReference<JavaEditorServices> editorServiceReference = context.getServiceReference(JavaEditorServices.class);
		editorServ = context.getService(editorServiceReference);

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

	public static BundleContext getContext() {
		return context;
	}

	public static Activator getInstance() {
		return instance;
	}

	public JavaEditorServices getEditorServ() {
		return editorServ;
	}

}