package pa.iscde.tasklist.definition;

import pt.iscte.pidesco.projectbrowser.model.ClassElement;

public interface ITaskList {

	
	void action(String taskType, ClassElement e, int line);

	
}