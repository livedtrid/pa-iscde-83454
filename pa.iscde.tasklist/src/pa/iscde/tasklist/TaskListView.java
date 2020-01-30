package pa.iscde.tasklist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.tasklist.extensibility.ITaskListAction;
import pa.iscde.tasklist.internal.TaskListActivator;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class TaskListView implements PidescoView {

	public static final String VIEW_ID = "pa.iscde.tasklist.view";
	// private static final String EXT_POINT_FILTER =
	// "pa.iscde.pidesco.projectbrowser.filter";

	Table table;
	private String root;

	/**
	 * Store a Map of a Set of Tasks - Uses the file path to return its tasks
	 */
	private Map<String, Set<Task>> taskList = new HashMap<String, Set<Task>>();

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

		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}

		System.out.println("projServ getRootPackage "
				+ TaskListActivator.getInstance().getProjectBrowserServices().getRootPackage().getFile());

		TaskListActivator.getInstance().getProjectBrowserServices().addListener(new ProjectBrowserListener() {
			@Override
			public void doubleClick(SourceElement element) {
				System.out.println("element " + element.getName());
				System.out.println("element isClass " + element.isClass());
				System.out.println("element getFile " + element.getFile());
				System.out.println("element getParent " + element.getParent());
				new Label(viewArea, SWT.NONE).setText(element.getName() + "Stuff");
				viewArea.layout();
			}
		});

		// Exemplo registo JavaEditorServices

		ServiceReference<JavaEditorServices> editorServiceReference = TaskListActivator.getContext().getServiceReference(JavaEditorServices.class);
		JavaEditorServices editorServ = TaskListActivator.getContext().getService(editorServiceReference);
		editorServ.addListener(new JavaEditorListener() {

			@Override
			public void fileOpened(File file) {

			}

			@Override
			public void fileSaved(File file) {
				updateTableView(file);

				System.out.println("fileSaved");

			}

			@Override
			public void fileClosed(File file) {

			}

			@Override
			public void selectionChanged(File file, String text, int offset, int length) {

			}
		});

		table.addListener(SWT.MouseDoubleClick, new Listener() {
			public void handleEvent(Event e) {

				TableItem[] selection = table.getSelection();

				String fileID = selection[0].getText(2);
				Set<Task> taskinhas = taskList.get(fileID);

				File f = null; //////////// TODO Hack this way more
				int l = 0;
				for (Task t : taskinhas) {

					f = t.getFile();
					l = t.getLine();
				}
				
				
				
				System.out.println(selection[0].getText() + " " + selection[0].getText(1) + " "
						+ selection[0].getText(2) + " " + selection[0].getText(3));

				Task t = (Task) selection[0].getData();
				System.out.println("Data " + t.getDescription());
				
				editorServ.selectText(t.getFile(), t.getOffset(), t.getDescription().length());
			}

		});

//		Button button = new Button(viewArea, SWT.PUSH);
//		button.setText("Description");
//
//		button.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				File f = editorServ.getOpenedFile();
//				if (f != null) {
//					Livedtrid
//					ITextSelection sel = editorServ.getTextSelected(f);
//					new Label(viewArea, SWT.NONE).setText(sel.getText());
//					viewArea.layout();
//				}
//			}
//		});

		root = TaskListActivator.getInstance().getProjectBrowserServices().getRootPackage().getFile().getPath();
		fileReader(new File(root));

		/// Exemplo para utilizar extensões

		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = reg.getConfigurationElementsFor("pa.iscde.tasklist.actions");
		for (IConfigurationElement e : elements) {
			String name = e.getAttribute("name");
			Button b = new Button(viewArea, SWT.PUSH);
			b.setText(name);

			try {
				ITaskListAction action = (ITaskListAction) e.createExecutableExtension("class");
				b.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						PackageElement dummy = TaskListActivator.getInstance().getProjectBrowserServices().getRootPackage();
												
						action.run("test", dummy, 0);
						viewArea.layout();
					}

				});
			} catch (CoreException e1) {
				e1.printStackTrace();
			}

		}

	}

	/**
	 * Iterates through the root file and its children and get all java files found
	 * 
	 * @param file - directory root path
	 */
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

	/**
	 * Updates the Java Tasks Table view
	 * 
	 * @param file
	 */
	public void updateTableView(File file) {

		TaskManager taskManager = new TaskManager();

		List<String> tokens = new ArrayList<String>();
		tokens.add("TODO");
		tokens.add("FIXME");
		tokens.add("BUG");

		try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
			String line;
			StringBuilder sb = new StringBuilder();

			System.out.println(file.getName());

			while ((line = buffer.readLine()) != null) {

				sb.append(line);
				sb.append(System.lineSeparator());
			}

			String everything = sb.toString();
			table.removeAll();
			taskManager.findComments(tokens, file, everything);

		} catch (IOException e) {
			e.printStackTrace();
		}

		taskList.put(file.getPath(), taskManager.getTasks());

		saveDataInTable(taskList);

	}

	/**
	 * Stores the Tasks on the table view
	 * 
	 * @param map of Tasks
	 */
	private void saveDataInTable(Map<String, Set<Task>> map) {

		for (Set<Task> s : map.values())
			for (Task t : s) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, t.getToken().toString() + t.getDescription());
				item.setText(1, "Project: " + t.getProject());
				item.setText(2, t.getFile().toString());
				item.setText(3, "Line " + t.getLine());
				item.setData(t);				

			}

		for (TableColumn column : table.getColumns()) {
			column.pack();
		}

		table.redraw();
	}

}
