package pa.iscde.tasklist.testextension;

import pa.iscde.tasklist.definition.*;

/**
 * 
 * @author livedtrid
 *
 */
public class TestExtensionPoint implements ITaskList{

	@Override
	public void doSomething() {
		System.out.println("Moin, moin!");
		
	}

}
