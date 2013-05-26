package visitors;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.tree.TreeScanner;
import com.sun.tools.javac.util.List;

public class DebugVisitor extends TreeScanner { 

	public static void debug(JCTree tree) {
		DebugVisitor v = new DebugVisitor() ;
		tree.accept(v) ; 		
	} 
	
	public static void debug(List<JCTree> trees) {
		DebugVisitor v = new DebugVisitor() ; 
		for(JCTree tree : trees)
			tree.accept(v) ; 		
	}

	public static void debug(Symbol sym) {
		System.out.println();
		System.out.println("sym[\""+sym.toString()+"\"] -> ("+sym.getClass()+")");
		if(sym!=null) {
			System.out.println("\t sym.name: "+sym.name);
			System.out.println("\t sym.flags_field: "+sym.flags_field+" ("+ Flags.toString(sym.flags_field)+")");
			System.out.println("\t sym.type: "+sym.type);
			System.out.println("\t sym.owner: "+sym.owner);
			System.out.println("\t sym.kind: "+sym.kind+" ("+sym.getKind()+")");
			System.out.println("\t sym.annotations: "+sym.annotations);
			System.out.println("\t sym.completer: "+sym.completer);
			System.out.println("\t sym.erasure_field: "+sym.erasure_field);
		}
		if(sym instanceof Symbol.VarSymbol) {
			Symbol.VarSymbol vsym = (Symbol.VarSymbol)sym ;
			System.out.println("\t sym.pos: "+vsym.pos);
			System.out.println("\t sym.adr: "+vsym.adr);
		}
		if(sym instanceof Symbol.ClassSymbol) {
			Symbol.ClassSymbol csym = (Symbol.ClassSymbol)sym ;
			System.out.println("\t sym.classfile: "+csym.classfile);
			System.out.println("\t sym.flatname: "+csym.flatname);
			System.out.println("\t sym.fullname: "+csym.fullname);
			System.out.println("\t sym.members_field: ->"+csym.members_field);
			System.out.println("\t sym.trans_local: "+csym.trans_local);
			System.out.println("\t sym.pool: "+csym.pool);
			for(Symbol s : csym.members_field.getElements())
				System.out.println("\t\tsym["+s+" ]");
		}
	}
	 
	/**
	 * VISITOR AND PRINTING METHODS
	 */
	
	int depth = 0 ;
	String separator = " *" ;
	String tabular = "  " ;

	void printDepth() { 
		for(int i=0 ; i<depth ; i++) System.out.print(tabular);
	}

	@Override
	public void visitAnnotatedType(JCAnnotatedType arg0) {
		printDepth();System.out.println(separator+" JCAnnotatedType\t"+arg0.toString().replace("\n", " "));
		
		depth++ ;
		super.visitAnnotatedType(arg0);
		depth-- ;
	}
 
	@Override 
	public void visitAnnotation(JCAnnotation arg0) {
		printDepth();System.out.println(separator+" JCAnnotation\t"+arg0.toString().replace("\n", " "));
		
		depth++ ;
		super.visitAnnotation(arg0);
		depth-- ;
	}

	@Override
	public void visitApply(JCMethodInvocation arg0) {
		printDepth();System.out.println(separator+" JCMethodInvocation\t"+arg0.toString().replace("\n", " "));
		
		depth++ ;
		super.visitApply(arg0);
		depth-- ;
	}

	@Override
	public void visitAssert(JCAssert arg0) {
		printDepth();System.out.println(separator+" JCAssert\t"+arg0.toString().replace("\n", " "));
		
		depth++ ;
		super.visitAssert(arg0);
		depth-- ;
	}

	@Override
	public void visitAssign(JCAssign arg0) {
		printDepth();System.out.println(separator+" JCAssign\t"+arg0.toString().replace("\n", " "));

		depth++ ;
		super.visitAssign(arg0);
		depth-- ;
	}

	@Override
	public void visitAssignop(JCAssignOp arg0) {
		printDepth();System.out.println(separator+" JCAssignOp\t"+arg0.toString().replace("\n", " "));
		depth++;
		super.visitAssignop(arg0);
		depth-- ;
	}

	@Override
	public void visitBinary(JCBinary arg0) {
		printDepth();System.out.println(separator+" JCBinary\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitBinary(arg0);
		depth-- ;
	}

	@Override
	public void visitBlock(JCBlock arg0) {
		printDepth();System.out.println(separator+" JCBlock\t"+arg0.toString());
		System.out.println();
		
		depth++;
		super.visitBlock(arg0);
		depth-- ;
	}

	@Override
	public void visitBreak(JCBreak arg0) {
		printDepth();System.out.println(separator+" JCBreak\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitBreak(arg0);
		depth-- ;
	}

	@Override
	public void visitCase(JCCase arg0) {
		printDepth();System.out.println(separator+" JCCase\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitCase(arg0);
		depth-- ;
	}

	@Override
	public void visitCatch(JCCatch arg0) {
		printDepth();System.out.println(separator+" JCCatch\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitCatch(arg0);
		depth-- ;
	}

	@Override
	public void visitClassDef(JCClassDecl arg0) {
		printDepth();System.out.println(separator+" JCClassDecl\t"+arg0.toString().replace("\n", " "));
		
		depth++ ;
		super.visitClassDef(arg0);
		depth-- ;
	}

	@Override
	public void visitConditional(JCConditional arg0) {
		printDepth();System.out.println(separator+" JCConditional\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitConditional(arg0);
		depth-- ;
	}

	@Override
	public void visitContinue(JCContinue arg0) {
		printDepth();System.out.println(separator+" JCContinue\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitContinue(arg0);
		depth-- ;
	}

	@Override
	public void visitDoLoop(JCDoWhileLoop arg0) {
		printDepth();System.out.println(separator+" JCDoWhileLoop\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitDoLoop(arg0);
		depth-- ;
	}

	@Override
	public void visitErroneous(JCErroneous arg0) {
		printDepth();System.out.println(separator+" JCErroneous\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitErroneous(arg0);
		depth-- ;
	}

	@Override
	public void visitExec(JCExpressionStatement arg0) {
		printDepth();System.out.println(separator+" JCExpressionStatement\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitExec(arg0);
		depth-- ;
	}

	@Override
	public void visitForLoop(JCForLoop arg0) {
		printDepth();System.out.println(separator+" JCForLoop\t"+arg0.toString().replace("\n", " "));

		depth++ ;
		super.visitForLoop(arg0);
		depth-- ;
	}

	@Override
	public void visitForeachLoop(JCEnhancedForLoop arg0) {
		printDepth();System.out.println(separator+" JCEnhancedForLoop\t"+arg0.toString().replace("\n", " "));

		depth++ ;
		super.visitForeachLoop(arg0);
		depth-- ;
	}

	@Override
	public void visitIdent(JCIdent arg0) {
		printDepth();System.out.println(separator+" JCIdent\t"+arg0
				+"\t"+arg0.getKind()
				+"\t"+arg0.getName()
				+"\t"+arg0.getTag()
				+"\t"+arg0.getTree()
				+"\t"+arg0.sym
				+"\t"+arg0.type);
		
		depth++;
		super.visitIdent(arg0); 
		depth-- ;
	}

	@Override
	public void visitIf(JCIf arg0) {
		printDepth();System.out.println(separator+" JCIf\t"+arg0.toString().replace("\n", " "));

		depth++ ;
		super.visitIf(arg0);
		depth-- ;
	}

	@Override
	public void visitImport(JCImport arg0) {
		printDepth();System.out.println(separator+" JCImport\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitImport(arg0);
		depth-- ;
	}

	@Override
	public void visitIndexed(JCArrayAccess arg0) {
		printDepth();System.out.println(separator+" JCArrayAccess\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitIndexed(arg0);
		depth-- ;
	}

	@Override
	public void visitLabelled(JCLabeledStatement arg0) {
		printDepth();System.out.println(separator+" JCLabeledStatement\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitLabelled(arg0);
		depth-- ;
	}

	@Override
	public void visitLambda(JCLambda arg0) {
		printDepth();System.out.println(separator+" JCLambda\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitLambda(arg0);
		depth-- ;
	}

	@Override
	public void visitLetExpr(LetExpr arg0) {
		printDepth();System.out.println(separator+" LetExpr\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitLetExpr(arg0);
		depth-- ;
	}

	@Override
	public void visitLiteral(JCLiteral arg0) {
		printDepth();System.out.println(separator+" JCLiteral\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitLiteral(arg0);
		depth-- ;
	}

	@Override
	public void visitMethodDef(JCMethodDecl arg0) {
		printDepth();System.out.println(separator+" JCMethodDecl\t"+arg0.name.toString()+" restype:"+arg0.restype);

		depth++ ;
		super.visitMethodDef(arg0);
		depth-- ;
	}

	@Override
	public void visitModifiers(JCModifiers arg0) {
		printDepth();System.out.println(separator+" JCModifiers\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitModifiers(arg0);
		depth-- ;
	}

	@Override
	public void visitNewArray(JCNewArray arg0) {
		printDepth();System.out.println(separator+" JCNewArray\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitNewArray(arg0);
		depth-- ;
	}

	@Override
	public void visitNewClass(JCNewClass arg0) {
		printDepth();System.out.println(separator+" JCNewClass\t"+arg0.toString().replace("\n", " "));

		depth++ ;
		super.visitNewClass(arg0);
		depth-- ;
	}

	@Override
	public void visitParens(JCParens arg0) {
		printDepth();System.out.println(separator+" JCParens\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitParens(arg0);
		depth-- ;
	}

	@Override
	public void visitReference(JCMemberReference arg0) {
		printDepth();System.out.println(separator+" JCMemberReference\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitReference(arg0);
		depth-- ;
	}

	@Override
	public void visitReturn(JCReturn arg0) {
		printDepth();System.out.println(separator+" JCReturn\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitReturn(arg0);
		depth-- ;
	}

	@Override
	public void visitSelect(JCFieldAccess arg0) {
		printDepth();System.out.println(separator+" JCFieldAccess\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitSelect(arg0);
		depth-- ;
	}

	@Override
	public void visitSkip(JCSkip arg0) {
		printDepth();System.out.println(separator+" JCSkip\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitSkip(arg0);
		depth-- ;
	}

	@Override
	public void visitSwitch(JCSwitch arg0) {
		printDepth();System.out.println(separator+" JCSwitch\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitSwitch(arg0);
		depth-- ;
	}

	@Override
	public void visitSynchronized(JCSynchronized arg0) {
		printDepth();System.out.println(separator+" JCSynchronized\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitSynchronized(arg0);
		depth-- ;
	}

	@Override
	public void visitThrow(JCThrow arg0) {
		printDepth();System.out.println(separator+" JCThrow\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitThrow(arg0);
		depth-- ;
	}

	@Override
	public void visitTopLevel(JCCompilationUnit arg0) {
		printDepth();System.out.println(separator+" JCCompilationUnit\t"+arg0.toString().replace("\n", " "));

		depth++ ;
		super.visitTopLevel(arg0);
		depth-- ;
	}

	@Override
	public void visitTree(JCTree arg0) {
		printDepth();System.out.println(separator+" JCTree\t"+arg0.toString().replace("\n", " "));
		
		depth++ ;
		super.visitTree(arg0);
		depth-- ;
	}

	@Override
	public void visitTry(JCTry arg0) {
		printDepth();System.out.println(separator+" JCTry\t"+arg0.toString().replace("\n", " "));
		
		depth++ ;
		super.visitTry(arg0);
		depth-- ;
	}

	@Override
	public void visitTypeApply(JCTypeApply arg0) {
		printDepth();System.out.println(separator+" JCTypeApply\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitTypeApply(arg0);
		depth-- ;
	}

	@Override
	public void visitTypeArray(JCArrayTypeTree arg0) {
		printDepth();System.out.println(separator+" JCArrayTypeTree\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitTypeArray(arg0);
		depth-- ;
	}

	@Override
	public void visitTypeBoundKind(TypeBoundKind arg0) {
		printDepth();System.out.println(separator+" TypeBoundKind\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitTypeBoundKind(arg0);
		depth-- ;
	}

	@Override
	public void visitTypeCast(JCTypeCast arg0) {
		printDepth();System.out.println(separator+" JCTypeCast\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitTypeCast(arg0);
		depth-- ;
	}

	@Override
	public void visitTypeIdent(JCPrimitiveTypeTree arg0) {
		printDepth();System.out.println(separator+" JCPrimitiveTypeTree\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitTypeIdent(arg0);
		depth-- ;
	}

	@Override
	public void visitTypeIntersection(JCTypeIntersection arg0) {
		printDepth();System.out.println(separator+" JCTypeIntersection\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitTypeIntersection(arg0);
		depth-- ;
	}

	@Override
	public void visitTypeParameter(JCTypeParameter arg0) {
		printDepth();System.out.println(separator+" JCTypeParameter\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitTypeParameter(arg0);
		depth-- ;
	}

	@Override
	public void visitTypeTest(JCInstanceOf arg0) {
		printDepth();System.out.println(separator+" JCInstanceOf\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitTypeTest(arg0);
		depth-- ;
	}

	@Override
	public void visitTypeUnion(JCTypeUnion arg0) {
		printDepth();System.out.println(separator+" JCTypeUnion\t"+arg0.toString().replace("\n", " "));
		
		depth++;
		super.visitTypeUnion(arg0);
		depth-- ;
	}

	@Override
	public void visitUnary(JCUnary arg0) {
		printDepth();System.out.println(separator+" JCUnary\t"+arg0.toString().replace("\n", " "));
		
		depth++ ;
		super.visitUnary(arg0);
		depth-- ;		
	}

	@Override
	public void visitVarDef(JCVariableDecl arg0) {
		String maybeflags = "";
		if(arg0.sym!=null)
			maybeflags = ",flags_field:"+arg0.sym.flags_field ;
		printDepth();
		System.out.println(separator+" JCVariableDecl(type:"+arg0.type+",vartype:"+arg0.vartype+maybeflags+")\t"
				+arg0.toString().replace("\r", " "));

		if(arg0.sym!=null) {
			Symbol.VarSymbol sym = arg0.sym;
			System.out.println(separator+" sym:\n"+separator+separator
				+"sym.flags_field:"+sym.flags_field+"\n"+separator+separator
					+"sym.adr:"+sym.adr+"\n"+separator+separator
					+"sym.pos:"+sym.pos+"\n"+separator+separator
					+"sym.annotations:"+sym.annotations+"\n"+separator+separator
					+"sym.completer:"+sym.completer+"\n"+separator+separator
					+"sym.erasure_field:"+sym.erasure_field+"\n"+separator+separator
					+"sym.kind:"+sym.kind+"\n"+separator+separator
					+"sym.name:"+sym.name+"\n"+separator+separator
					+"sym.owner:"+sym.owner+"\n"+separator+separator
					+"sym.type:"+sym.type+"\n"+separator+separator);
		}
		
		depth++ ;
		super.visitVarDef(arg0);
		depth-- ;
	}

	@Override
	public void visitWhileLoop(JCWhileLoop arg0) {
		printDepth();System.out.println(separator+" JCWhileLoop\t"+arg0.toString().replace("\n", " "));
		
		depth++ ;
		super.visitWhileLoop(arg0);
		depth-- ;
	}

	@Override
	public void visitWildcard(JCWildcard arg0) {
		printDepth();System.out.println(separator+" JCWildcard\t"+arg0.toString().replace("\n", " "));
		
		depth++ ;
		super.visitWildcard(arg0);
		depth-- ;
	}

}
