package instrumentation;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.ListBuffer;

import javax.lang.model.element.Element;

import static plugin.Persistent_Plugin.out;

/**
 * Utility class used to manipulate AST elements and symbols.
 *
 * @autor metabrain (https://github.com/metabrain) - Daniel Parreira 2013
 */
public class AST_Query {

	public static Element convert2Element(JCTree tree, TreeScannerStateful state) {
		Element eC = state.treesC.getElement(state.trees.getPath(state.jcc, tree)) ;
		Element e = state.trees.getElement(state.trees.getPath(state.jcc, tree)) ;
		if(e!=eC || (e!=null && eC!=null && !eC.toString().equalsIgnoreCase(e.toString())))
			throw new RuntimeException("TreeC and Tree didn't return same element... e:"+e.toString()+" eC:"+eC.toString()) ;
		return eC ;
	}

	public static JCTree convert2JCTree(Element e, TreeScannerStateful state) {
		return state.elementsC.getTree(e);
	}
 
	public static void removeAnnotation(JCVariableDecl var, String toRemove) {
		ListBuffer<JCAnnotation> lb = ListBuffer.lb();

		for (JCAnnotation note : var.mods.annotations) {
			if (!note.annotationType.toString().equals(toRemove)) {
				lb.add(note);
			}
		}

		var.mods.annotations = lb.toList();
	}

}
