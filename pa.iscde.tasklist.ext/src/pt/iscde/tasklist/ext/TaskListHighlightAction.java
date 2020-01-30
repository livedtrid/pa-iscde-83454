package pt.iscde.tasklist.ext;
import org.osgi.framework.BundleContext;
import pa.iscde.tasklist.extensibility.ITaskListAction;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import org.osgi.framework.ServiceReference;

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
		
		System.out.println("getFile " + e.getFile()); 
		System.out.println("getName " + e.getName());
		System.out.println("getParent " + e.getParent());
	
		
	}

}
