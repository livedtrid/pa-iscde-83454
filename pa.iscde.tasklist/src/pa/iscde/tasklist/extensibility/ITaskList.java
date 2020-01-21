package pa.iscde.tasklist.extensibility;

import pt.iscte.pidesco.projectbrowser.model.ClassElement;

public interface ITaskList {

	
	void action(String taskType, ClassElement e, int line);

	
}