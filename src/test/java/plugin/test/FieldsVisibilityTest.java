package plugin.test; 

import org.junit.Before;

public class FieldsVisibilityTest extends CompileTest {
	
	@Override
	@Before
	public void init() {
		init("./src/test/java/tests/fieldsVisibility/","fieldsVisibility");
	} 

}
