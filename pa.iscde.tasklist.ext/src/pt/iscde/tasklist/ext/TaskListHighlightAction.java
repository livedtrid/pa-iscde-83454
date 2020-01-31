package pt.iscde.tasklist.ext;

import java.io.File;

import pa.iscde.tasklist.extensibility.ITaskListAction;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;

public class TaskListHighlightAction implements ITaskListAction {

	public TaskListHighlightAction() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public void run(String taskType, PackageElement e, int line) {
		// TODO Auto-generated method stub
		System.out.println("action run");

	}

	@Override
	public void run(String taskType, ClassElement e, int line) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(File file, int offset, int lenght) {
		
		System.out.println("FUNCIONA");

		Activator.getInstance().getEditorServ().selectText(file, offset, lenght);

	}

}
