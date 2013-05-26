package plugin;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;

import java.io.*;

public class Persistent_Plugin implements Plugin{

	public static final boolean PLUGIN_DEBUG = true ;
	public static PrintStream out = null ;

	@Override
	public void init(JavacTask task, String... arg1) {
		if(PLUGIN_DEBUG) //print to standard out if debug is ON
			out = System.out ;
		else //redirect output to a 'fake' output stream (poor guy's /dev/null) if debug is OFF
			out = new PrintStream(new ByteArrayOutputStream()) ;

		out.println("Plugin Running!");
		task.setTaskListener(new CodePatternTaskListener(task));
	}
	
	@Override
	public String getName() {
		return "Persistent_Plugin";
	}
}