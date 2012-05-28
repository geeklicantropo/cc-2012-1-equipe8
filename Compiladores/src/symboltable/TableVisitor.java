package symboltable;

import syntaxtree.TClassDeclExtends;
import syntaxtree.TClassDeclSimple;
import syntaxtree.TFormal;
import syntaxtree.TIdentifier;
import syntaxtree.TMethodDecl;
import syntaxtree.TProgram;
import syntaxtree.TTypeBoolean;
import syntaxtree.TTypeIdentifier;
import syntaxtree.TTypeInteger;
import syntaxtree.TTypeIntegerArray;
import syntaxtree.TVarDecl;

public class TableVisitor implements TableVisitorInterface {

	@Override
	public Table visit(TProgram n) {
		Table tab;
		tab = n.m.accept(this,Symbol.symbol(n.m.i1.toString()));
		//tab.put(key, valor)
		return null;
	}

	@Override
	public Table visit(TClassDeclSimple n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table visit(TClassDeclExtends n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VarInfo visit(TVarDecl n) {
		
		return null;
	}

	@Override
	public Table visit(TMethodDecl n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VarInfo visit(TFormal n) {
		
		return new VarInfo(n.t,Symbol.symbol(n.i.s));
	}

	@Override
	public Symbol visit(TTypeIntegerArray n) {
		
		return Symbol.symbol("int[]");
	}

	@Override
	public Symbol visit(TTypeBoolean n) {
		return Symbol.symbol("boolean");
	}

	@Override
	public Symbol visit(TTypeInteger n) {
		return Symbol.symbol("int");
	}

	@Override
	public Symbol visit(TTypeIdentifier n) {
		return Symbol.symbol(n.s);
	}

	@Override
	public Symbol visit(TIdentifier n) {
		return Symbol.symbol(n.s);
	}
	
	

}
