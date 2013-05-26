package instrumentation;

import com.sun.source.util.JavacTask;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.code.Types;
import com.sun.tools.javac.comp.Attr;
import com.sun.tools.javac.comp.Enter;
import com.sun.tools.javac.comp.MemberEnter;
import com.sun.tools.javac.comp.Resolve;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.model.JavacTypes;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.tree.TreeInfo;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeScanner;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.JavaCompiler.CompilationTask;

/**
 * A subclass of TreeScanner from the Javac internal classes used to,
 * transverse the AST tree.
 *
 * This specific class is augmented with the "rabbit hole to wonderland",
 * i.e., the casting of public interfaces to the compilers internal classes
 * allows us access to almost all classes used by the compilation process.
 * These are instantiated when the constructor is invoked.
 *
 * Use 'em wisely, for with great power comes great responsibility.
 * (or brace yourself with hours debugging symbols)
 * 
 * @author metabrain / Daniel Parreira - 2013
 *
 */
public class TreeScannerStateful extends TreeScanner {

    public final SourcePositions sourcePositions;
    public final Trees trees;
    public final JavacTrees treesC;
    public final Context context ;
    public final Attr attr;
    public final Symtab syms ;
    public final Types types ;
    public final JavacTypes typesC ;
    public final TreeMaker make ;
    public final Enter enter ;
    public final Names names ;
    public final Elements elements ;
    public final JavacElements elementsC ;
    public final JavacElements javacElements ;
	public final Resolve resolve ;
	public final MemberEnter memberEnter ;
	public final TreeInfo info;
	public final JCCompilationUnit jcc ;
	public final BasicJavacTask task;

	public TreeScannerStateful(CompilationTask task, JCCompilationUnit jcc) {
    	this.jcc = jcc ;
        trees = Trees.instance(task);
        treesC = (JavacTrees)trees;
                
        //Rabbit hole to Wonderland
        context = ((BasicJavacTask)task).getContext() ;
		this.task = ((BasicJavacTask)task) ;
        
        //Initialize magical classes !
        make = TreeMaker.instance(context) ;
        names = Names.instance(context) ;
        syms = Symtab.instance(context);
        types = Types.instance(context) ;
        typesC = JavacTypes.instance(context) ;
        attr = Attr.instance(context) ;
        enter = Enter.instance(context) ;
        resolve = Resolve.instance(context) ;
        memberEnter = MemberEnter.instance(context) ;
		javacElements = JavacElements.instance(context) ;
		info = TreeInfo.instance(context) ;

        sourcePositions = trees.getSourcePositions();
        elements = ((JavacTask)task).getElements();
        elementsC = (JavacElements)elements;
 	}
}