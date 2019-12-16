package pa.iscde.tasklist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class TaskListView implements PidescoView {

	Table table;
	private String root;
	
	public TaskListView() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {

		viewArea.setLayout(new GridLayout());
		table = new Table(viewArea, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);
		String[] titles = { "Description", "Project", "File", "Line" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}
		int count = 10;
		for (int i = 0; i < count; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, "TODO: test");
			item.setText(1, "Test Project");
			item.setText(2, "Test.java");
			item.setText(3, "line " + i);
		}
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}	

		// viewArea.setLayout(new RowLayout(SWT.HORIZONTAL));

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
		
		
		root = projServ.getRootPackage().getFile().getPath();
		fileReader(new File(root));
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}

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
	
	
	private void fileReader(File file) {
		for (File f : file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory())
					return true;
				String name = pathname.getName();
				int index = name.lastIndexOf(".");
				if (index == -1)
					return false;
				return name.substring(index + 1).equals("java");
			}
		})) {
			if (f.isFile())
				updateTableView(f);
			else
				fileReader(f);
		}
	}
	
	public void updateTableView(File file) {
		
		try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
			String line;
			int count = 0;
		
			while ((line = buffer.readLine()) != null) {
				count++;
			System.out.println("Line " + line);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	

}
