package pa.iscde.tasklist.extensibility;

//import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;

public interface ITaskListAction {

	
	//void run(String taskType, ClassElement e, int line);	
	void run(String taskType, PackageElement e, int line);
	
}