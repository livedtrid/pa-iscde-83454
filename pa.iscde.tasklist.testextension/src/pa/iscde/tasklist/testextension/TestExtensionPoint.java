package pa.iscde.tasklist.testextension;

import pa.iscde.tasklist.extensibility.*;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;

/**
 * 
 * @author livedtrid
 *
 */
public class TestExtensionPoint implements ITaskList{

	@Override
	public void action(String taskType, ClassElement e, int line) {
		System.out.println("Moin, moin!");
		
	}

}
