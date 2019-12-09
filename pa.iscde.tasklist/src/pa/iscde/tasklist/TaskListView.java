package pa.iscde.tasklist;

import java.io.File;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class TaskListView implements PidescoView {

	public TaskListView() {
		// TODO Auto-generated constructor stub
	}

	private static TaskListView instance;

	public static TaskListView getInstance() {

		return instance;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {

		instance = this;



	final List list = new List (viewArea, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		for (int i=0; i<128; i++) list.add ("Item " + i);
		Rectangle clientArea = viewArea.getClientArea ();
		list.setBounds (clientArea.x, clientArea.y, 100, 100);
		list.addListener (SWT.Selection, e -> {
			String string = "";
			int [] selection = list.getSelectionIndices ();
			for (int i=0; i<selection.length; i++) string += selection [i] + " ";
			System.out.println ("Selection={" + string + "}");
		});
		list.addListener (SWT.DefaultSelection, e -> {
			String string = "";
			int [] selection = list.getSelectionIndices ();
			for (int i=0; i<selection.length; i++) string += selection [i] + " ";
			System.out.println ("DefaultSelection={" + string + "}");
		});
	
		
		//viewArea.setLayout(new RowLayout(SWT.HORIZONTAL));
		

		BundleContext context = Activator.getContext();
		ServiceReference<ProjectBrowserServices> serviceReference = context
				.getServiceReference(ProjectBrowserServices.class);
		ProjectBrowserServices projServ = context.getService(serviceReference);

		projServ.addListener(new ProjectBrowserListener.Adapter() {
			@Override
			public void doubleClick(SourceElement element) {
				new Label(viewArea, SWT.NONE).setText(element.getName());
				viewArea.layout();
			}
		});

		ServiceReference<JavaEditorServices> serviceReference2 = context.getServiceReference(JavaEditorServices.class);
		JavaEditorServices javaServ = context.getService(serviceReference2);

		Button button = new Button(viewArea, SWT.PUSH);
		button.setText("Description");
		
		Button button2 = new Button(viewArea, SWT.PUSH);
		button2.setText("Description");

		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				File f = javaServ.getOpenedFile();
				if (f != null) {
					ITextSelection sel = javaServ.getTextSelected(f);
					new Label(viewArea, SWT.NONE).setText(sel.getText());
					viewArea.layout();
				}
			}
		});

		
		/// Exemplo para utilizar extensões
		/*
		 * IExtensionRegistry reg = Platform.getExtensionRegistry();
		 * IConfigurationElement[] elements =
		 * reg.getConfigurationElementsFor("pt.iscte.pidesco.demo.actions");
		 * for(IConfigurationElement e : elements) { String name =
		 * e.getAttribute("name"); Button b = new Button(viewArea, SWT.PUSH);
		 * b.setText(name);
		 * 
		 * 
		 * try { DemoAction action = (DemoAction) e.createExecutableExtension("class");
		 * b.addSelectionListener(new SelectionAdapter() {
		 * 
		 * @Override public void widgetSelected(SelectionEvent e) {
		 * action.run(viewArea); viewArea.layout(); }
		 * 
		 * }); } catch (CoreException e1) { e1.printStackTrace(); }
		 * 
		 * }
		 * 
		 * 
		 * 
		 */
	}

}
