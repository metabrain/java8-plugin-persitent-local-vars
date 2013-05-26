package plugin.phases;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;
import instrumentation.AST_Query;
import instrumentation.TreeScannerStateful;
import visitors.DebugVisitor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.tools.JavaCompiler;
import java.lang.reflect.Modifier;
import static com.sun.tools.javac.code.Flags.*;

import static plugin.Persistent_Plugin.out;

/**
 * Where the actually transformation happens. Each method is evaluated for
 * annotations @Persistent and extracts the variables declarations to the
 * class (static or not, depending on the method) scope with adequate
 * renaming to avoid collisions from same named variables from different
 * methods.
 *
 * @autor metabrain (https://github.com/metabrain) - Daniel Parreira 2013
 */
public class PersistentifyPhase extends TreeScannerStateful {

	public PersistentifyPhase(JavaCompiler.CompilationTask task, JCTree.JCCompilationUnit jcc) {
		super(task, jcc);
	}

	public static void translate(JCTree tree, JavaCompiler.CompilationTask task, JCTree.JCCompilationUnit jcc) {
//		out.println("PHASE translating: "+jcc.getSourceFile().getName());
		tree.accept(new PersistentifyPhase(task,jcc));
	}

	static String persistent_name = annotations.Persistent.class.getSimpleName() ;

	@Override
	public void visitMethodDef(JCTree.JCMethodDecl tree) {
//		tree.body = translate(tree.body);
//		result = tree;
		super.visitMethodDef(tree);
	}

	@Override
	public void visitClassDef(JCTree.JCClassDecl tree) {
//		if(tree.name.toString().equalsIgnoreCase("Main"))
//			tree.
//		out.println("$$$$$$$$ BEFORE #########\n\n"+tree.toString());
//
//		   JCTree.JCLiteral stringLiteral = make.Literal("HALLO");
//		Type type = syms.stringType ;
//		Symbol.VarSymbol stringSymbol = new Symbol.VarSymbol(
//				0,
//				names.fromString("LOLFIELD"),
//				type,//syms.stringType,
//				tree.sym) ;
//
//		JCTree.JCVariableDecl new_field = make.VarDef(stringSymbol, stringLiteral) ;


//		JCTree.JCLiteral stringLiteral = make.Literal(2);
//		Type type = syms.intType ;
//		Symbol.VarSymbol stringSymbol = new Symbol.VarSymbol(
//				PUBLIC | HASINIT | STATIC,
//				names.fromString("LOLFIELD"),
//				type,//syms.stringType,
//				tree.sym) ;
//		JCTree.JCVariableDecl new_field = make.VarDef(stringSymbol, stringLiteral) ;
//		tree.sym.members_field.enter(stringSymbol);
//		DebugVisitor.debug(tree.sym);
//
//		ListBuffer<JCTree> buf = ListBuffer.lb();
//		buf.prepend(new_field);
//		buf.addAll(tree.defs);
//		tree.defs = buf.toList();

		super.visitClassDef(tree);
//		result = tree ;
//		out.println("&&&&&&&&& AFTER #########\n\n"+tree.toString());
	}



	@Override
	public void visitVarDef(JCTree.JCVariableDecl var) {
//		out.println("var:"+var.toString());
//		long flgs = -1 ;
//		if(var.sym!=null)           {
//			flgs = var.sym.flags_field;
//			DebugVisitor.debug(var.sym);
//		}
//		if(true)
//			return ;
//		out.println("VAR FLAGS BEFORE: "+flgs+ " ("+Flags.toString(flgs)+")");
		for(JCTree.JCAnnotation annot : var.getModifiers().getAnnotations())      {
				if(annot.getAnnotationType().toString().equals(PersistentifyPhase.persistent_name)) { {
					String old_name = var.name.toString() ;
					//remove annotation
					AST_Query.removeAnnotation(var,persistent_name);

					//find outer method
					Element e = AST_Query.convert2Element(var,this);
					Element meth = e.getEnclosingElement() ;
//					out.println(meth.getKind());
					while(!meth.getKind().equals(ElementKind.METHOD)) {
						meth = meth.getEnclosingElement() ;
//						out.println(meth.getKind());
					}
//					out.println("outer meth is:"+meth.getSimpleName().toString());

					//check if method is static or not
					Symbol.MethodSymbol methsym = (Symbol.MethodSymbol)meth;
					boolean isStatic = methsym.getModifiers().toString().contains("static");
//					out.println("is the method with modifiers "+methsym.getModifiers().toString()+" static? "+isStatic);

					//make new variable private so it doesnt polute our classes unnecessarilly
					var.mods = make.Modifiers(Modifier.PUBLIC);
					var.sym.flags_field = PUBLIC | HASINIT ;

					//..and if the method is static, change variable to static as well!
					if(isStatic) {
						var.mods = make.Modifiers(var.mods.flags | Modifier.STATIC);
						var.sym.flags_field |= STATIC ;
					}

					//find outer class
					Element clazz = meth.getEnclosingElement() ;
					while(!clazz.getKind().isClass()) {
						clazz = clazz.getEnclosingElement() ;
//						out.println(clazz.getKind());
					}
//					out.println("outer class is:"+clazz.getSimpleName().toString());

					//add var decl to parent class (static or not)
					JCTree.JCClassDecl clazzDecl = (JCTree.JCClassDecl)AST_Query.convert2JCTree(clazz,this);
					ListBuffer<JCTree> buf = ListBuffer.lb();
					buf.prepend(var);
					buf.addAll(clazzDecl.defs);
					clazzDecl.defs = buf.toList();

					//...and remove the method declaration from the the method
					JCTree.JCMethodDecl methodDecl = (JCTree.JCMethodDecl)AST_Query.convert2JCTree(meth,this);
					List<JCTree.JCStatement> stats = methodDecl.body.stats ;
					ListBuffer<JCTree.JCStatement> buf_stats = ListBuffer.lb();
					for(int i=0 ; i<stats.size() ; i++) {
//						out.println((stats.get(i).toString())+" different to "+var.toString()+"?"+(stats.get(i)!=var));
						if(stats.get(i)!=var) //I think this op is O(N) and not O(1) in ListBuffers, changed in future
							buf_stats.append(stats.get(i));
					}
					methodDecl.body.stats = buf_stats.toList();


//					out.println("var in class, but symbol owner is..."+var.sym.owner);
//					var = make.VarDef(var.mods,var.name,var.vartype,var.init) ;
//					out.println("NEW var in class, but symbol owner is..."+var.sym==null);
//					long nflgs = -1 ;
//					if(var.sym!=null)
//						nflgs = var.sym.flags_field;
//					out.println("VAR FLAGS AFTER: "+nflgs);
//					DebugVisitor.debug(var.sym);



					//pick a new name for our variable to avoid class scope collisions
					//TODO get better name for the persistent variable
					Name new_name = names.fromString("_persistent_"+var.name.toString()+"_from_"+methodDecl.name.toString());
					//...rename the variable and it's symbols
					var.name = new_name ;
					var.sym.name = new_name ;
					var.sym.owner = clazzDecl.sym ;
					clazzDecl.sym.members_field.enter(var.sym);

					//and finally rename all the idents in this method mentioning the old variable name
					methodDecl.accept(new TranslateIdent(old_name, new_name, var));


//					flgs = -1 ;
//					if(var.sym!=null)
//						flgs = var.sym.flags_field;
//					out.println("VAR FLAGS AFTER: "+flgs);

//					out.println(clazzDecl.toString());
	//				out.println("owner:"+var.sym.owner);
				}
			}
		}

		super.visitVarDef(var);
	}

	private class TranslateIdent extends TreeTranslator {
		final String old_name;
		final Name new_name ;
		final JCTree.JCVariableDecl var ;
		public TranslateIdent(String old_name, Name new_name, JCTree.JCVariableDecl var) {
			this.var = var ;
			this.old_name = old_name ;
			this.new_name = new_name ;
		}

		@Override
		public void visitIdent(JCTree.JCIdent tree) {
			//if old variable name, convert to new name
			if(tree.name.contentEquals(old_name))           {
//				tree = make.Ident(var.sym);
//				String sym = "null";
//				if(tree.sym!=null)
//					sym = tree.sym.getKind().toString();
//				out.println("new JCIDENT["+tree.name.toString()+"].sym.kind="+sym);
//				if(tree.sym!=null)
//					out.println("new JCIDENT["+tree.name.toString()+"].sym.flags_field="+Flags.toString(tree.sym.flags()));

				tree.name = new_name ;

//				out.println("SAME OBJ?=!=!=!"+(var.sym==tree.sym));
//				DebugVisitor.debug(tree);
//				DebugVisitor.debug(tree.sym);
			}
			result = tree ;
//			super.visitIdent(tree);
		}
	}
}
