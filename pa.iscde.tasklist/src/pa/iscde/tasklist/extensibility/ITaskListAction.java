package pa.iscde.tasklist.extensibility;

import java.io.File;

import pt.iscte.pidesco.projectbrowser.model.ClassElement;

import pt.iscte.pidesco.projectbrowser.model.PackageElement;

public interface ITaskListAction {

	
	void run(String taskType, ClassElement e, int line);	
	
	void run(String taskType, PackageElement e, int line);
	
	void run(File file, int offset, int lenght);
	
}