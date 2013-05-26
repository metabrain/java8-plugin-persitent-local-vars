package plugin.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.*;
import org.junit.rules.ExpectedException;

import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;

public abstract class CompileTest {

	public String currTestPath = null ;
	static String pwd = null ;
	String pluginjar ;
	String bin ; 
	String src ;
	String JDK_8_HOME = "C:\\Program Files\\Java\\jdk1.8.0\\" ;
	String JDK_8_JAVAC = "C:\\Program Files\\Java\\jdk1.8.0\\bin\\javac.exe" ;
	
	static {
		Path currentRelativePath = Paths.get("");
		pwd = currentRelativePath.toAbsolutePath().toString();
//		System.out.println("Current relative path is: " + s);
	}
	
	@Before
	public abstract void init() ;
		
	public void init(String srcDir, String testNum) {
		src = srcDir ;
		
		//Make test subDir
	    File subdir = new File(currTestPath, "tests/"+testNum);
	    subdir.mkdirs();
//	    System.out.println(subdir.exists());
//	    System.out.println(subdir.getAbsolutePath());
	    bin = "./tests/"+testNum;
	   
	    pluginjar = "./target/classes" ;
	}
	
	public String join(String[] array) {
		StringBuffer buf = new StringBuffer();
		for(int i = 0 ; i<array.length ; i++) {
			buf.append(array[i]);
			buf.append(" ");
		}
		return buf.toString();
	}
	
	//Get relative names of all the files in the src folder
	public String[] getSrcFiles() {
		File srcFolder = new File(src);
        System.out.println("folder"+srcFolder.getAbsolutePath());
		ArrayList<String> srcFiles = new ArrayList<String>(32);
		for(File f : srcFolder.listFiles()) {
			if(f.getAbsolutePath().endsWith(".java")) {
				srcFiles.add(f.getAbsolutePath().replace((pwd+"\\"), "")) ;
				System.out.println(srcFiles.get(srcFiles.size()-1));
			}
		}
		String[] arr = new String[srcFiles.size()];
		srcFiles.toArray(arr);
		return arr ;
	}
	
	String[] concat(String[] A, String[] B) {
		   int aLen = A.length;
		   int bLen = B.length;
		   String[] C= new String[aLen+bLen];
		   System.arraycopy(A, 0, C, 0, aLen);
		   System.arraycopy(B, 0, C, aLen, bLen);
		   return C;
		}
	
	@Test
	public void compile() {
		String[] cmd = {
//				"-verbose",
				"-source", "1.8",
				"-target", "1.8",
				"-d", bin,
				"-classpath",
					JDK_8_HOME+"lib\\tools.jar"+";"+ 
					JDK_8_HOME+"lib\\"+";"+
					JDK_8_HOME,
				"-cp", pluginjar,
				"-processorpath", pluginjar, "-Xplugin:Persistent_Plugin"
		};

		String[] srcFiles = getSrcFiles();
		String[] fullCmd = concat(cmd, srcFiles) ; 
		System.out.println(join(fullCmd));

		int exitCode = -1 ;
		//Print error to standard Eclipse console and not JUnit console
		try{
			exitCode = com.sun.tools.javac.Main.compile(fullCmd);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
//		int exitCode = com.sun.tools.javac.Main.compile(cmd.split(" "));
		Assert.assertTrue(exitCode == 0) ;

		//TODO actually run the generated code and check if it doesnt thrown an exception

		System.out.flush() ;
	}
	
	@After
	public void end() {
		
	}
	
}
