package plugin;

import com.sun.source.util.JavacTask;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import plugin.phases.PersistentifyPhase;

public class CodePatternTaskListener implements TaskListener {
 
	/**
	 * JAVAC PLUGIN METHODS
	 */
	
	private final JavacTask task ;

	public CodePatternTaskListener(JavacTask task) {
    	this.task = task ;
	}
    
    /* 
     * 4 compilation phases
     * 
	 * PARSE
	 * For events related to the parsing of a file.
	 * ENTER 
	 * For events relating to elements being entered.
	 * 
	 * ##################################################################
	 * !!! Begins after ENTER phase, repeats ENTER phase when it ends !!!
	 * ##################### ############################################
	 * ANNOTATION_PROCESSING
	 * For events relating to overall annotation processing.
	 * ANNOTATION_PROCESSING_ROUND
	 * For events relating to an individual annotation processing round.
	 * 
     * ANALYZE 
	 * For events relating to elements being analyzed for errors.
	 * ------> DESUGAR HAPPENS BETWEEN THESE, SO AT GENERATE.START
	 * GENERATE
	 * For events relating to class files being generated.
	 * ------> SOURCE CODE IS GENERATED AND DISAPEARED NOW
	 * 
	 * This ones for some reason are not beeing called...
	 * 
     */
    @Override
    public void started(TaskEvent taskEvent) {
    	JCCompilationUnit jcc = (JCCompilationUnit)taskEvent.getCompilationUnit();

//		if (taskEvent.getKind().equals(TaskEvent.Kind.GENERATE)) {
//			out.println("########## START ENTER\n\n\n"+jcc.toString());
//			savingModdedSources(taskEvent);
//		}
    }
     
	@Override 
    public void finished(TaskEvent taskEvent) {
    	JCCompilationUnit jcc = (JCCompilationUnit)taskEvent.getCompilationUnit();

		if (taskEvent.getKind().equals(TaskEvent.Kind.ENTER)) {
        	persistentify(jcc);
//			out.println("########## FINISH ENTER\n\n\n"+jcc.toString());
		}
    }

	private void persistentify(JCCompilationUnit jcc) {
		for(JCTree tree : jcc.getTypeDecls()) {
			PersistentifyPhase.translate(tree, task, jcc);
		}
	}

//	private void savingModdedSources(TaskEvent taskEvent) {
//		JCCompilationUnit jcc = (JCCompilationUnit)taskEvent.getCompilationUnit();
//
//		//For each class in this source file, save it in a new file for debugging
//		for(Tree each : jcc.getTypeDecls()) {
//
//			JCTree jct = (JCTree) each ;
//			jct = jct.getTree() ;
//
//			//Attain new modded source file name
//			int dir = jcc.getSourceFile().getName().lastIndexOf("\\") ;
//			String generatedDir = "gen\\" ;
//			String fname = generatedDir+"__MODDED__" + jcc.getSourceFile().getName().substring(dir+1);
//
//			//Print the translated source into it's own file
//			try {
//				File fDir = new File(generatedDir) ;
//				fDir.mkdir();
//				File newSourceFile = new File(fname) ;
//				newSourceFile.createNewFile() ;
//				newSourceFile.setWritable(true) ;
//				PrintWriter p = new PrintWriter(newSourceFile) ;
//				Pretty prettyPrint = new Pretty(p, true) ;
//				prettyPrint.width = 999 ;
//				prettyPrint.print(jct);
//				p.flush(); p.close();
//			} catch (IOException e) {e.printStackTrace();}
//		}
//	}

}
