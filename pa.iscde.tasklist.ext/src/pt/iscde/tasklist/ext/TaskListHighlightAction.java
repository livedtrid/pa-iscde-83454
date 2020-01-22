package pt.iscde.tasklist.ext;
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

}
