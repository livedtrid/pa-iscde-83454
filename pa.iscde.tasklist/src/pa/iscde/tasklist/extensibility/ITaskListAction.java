package pa.iscde.tasklist.extensibility;

import pt.iscte.pidesco.projectbrowser.model.ClassElement;

public interface ITaskListAction {

	
	void run(String taskType, ClassElement e, int line);

	
}