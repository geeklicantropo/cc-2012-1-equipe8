package visitor;

import syntaxtree.*;
import symboltable.*;

public class DepthFirstVisitor implements Visitor {

	private Table table;

	public Table buildSymbolTable(TProgram program) {
		table = new Table();
		visit(program);
		return table;
	}

	// TMainClass m;
	// TClassDeclList cl;
	public void visit(TProgram n) {
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).accept(this);
		}

	}

	// TIdentifier i1,i2;
	// TStatement s;
	public void visit(TMainClass n) {
		Symbol clas = Symbol.symbol(n.i1.s);
		table.put(clas, clas.getClass());
		clas = Symbol.symbol(n.i2.s);
		table.put(clas, clas.getClass());
		n.s.accept(this);
		// table.beginScope();
		// table.endScope();
	}

	// TIdentifier i;
	// TVarDeclList vl;
	// TMethodDeclList ml;
	public void visit(TClassDeclSimple n) {
		Symbol clas = Symbol.symbol(n.i.s);
		table.put(clas, clas.getClass());
		// table.beginScope();
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int j = 0; j < n.ml.size(); j++) {
			n.ml.elementAt(j).accept(this);
		}
		// table.endScope();
	}

	// TIdentifier i;
	// TIdentifier j;
	// TVarDeclList vl;
	// TMethodDeclList ml;
	public void visit(TClassDeclExtends n) {
		Symbol clas = Symbol.symbol(n.i.s);
		table.put(clas, clas.getClass());
		// table.beginScope();
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int j = 0; j < n.ml.size(); j++) {
			n.ml.elementAt(j).accept(this);
		}
		// table.endScope();
	}

	// TType t;
	// TIdentifier i;
	public void visit(TVarDecl n) {
		table.put(Symbol.symbol(n.i.s), n.t);
	}

	// TType t;
	// TIdentifier i;
	// TFormalList fl;
	// TVarDeclList vl;
	// Statements sl;
	// TExp e;
	public void visit(TMethodDecl n) {
		Symbol name = Symbol.symbol(n.i.s);
		table.put(name, name.getClass());
		// table.beginScope();
		for (int i = 0; i < n.fl.size(); i++) {
			n.fl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		// table.endScope();
	}

	// TType t;
	// TIdentifier i;
	public void visit(TFormal n) {
		n.t.accept(this);
		n.i.accept(this);
	}

	public void visit(TTypeIntegerArray n) {
	}

	public void visit(TTypeBoolean n) {
	}

	public void visit(TTypeInteger n) {
	}

	// String s;
	public void visit(TTypeIdentifier n) {
	}

	// Statements sl;
	public void visit(TStatementBlock n) {
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
	}

	// TExp e;
	// TStatement s1,s2;
	public void visit(TStatementIf n) {
		n.e.accept(this);
		n.s1.accept(this);
		n.s2.accept(this);
	}

	// TExp e;
	// TStatement s;
	public void visit(TStatementWhile n) {
		n.e.accept(this);
		n.s.accept(this);
	}

	// TExp e;
	public void visit(TStatementPrint n) {
		n.e.accept(this);
	}

	// TIdentifier i;
	// TExp e;
	public void visit(TStatementAssign n) {
		n.i.accept(this);
		n.e.accept(this);
	}

	// TIdentifier i;
	// TExp e1,e2;
	public void visit(TStatementArrayAssign n) {
		n.i.accept(this);
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// TExp e1,e2;
	public void visit(TExpOpAnd n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// TExp e1,e2;
	public void visit(TExpOpLessThan n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// TExp e1,e2;
	public void visit(TExpOpPlus n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// TExp e1,e2;
	public void visit(TExpOpMinus n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// TExp e1,e2;
	public void visit(TExpOpTimes n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// TExp e1,e2;
	public void visit(TExpArrayLookup n) {
		n.e1.accept(this);
		n.e2.accept(this);
	}

	// TExp e;
	public void visit(TExpArrayLength n) {
		n.e.accept(this);
	}

	// TExp e;
	// TIdentifier i;
	// TExpList el;
	public void visit(TExpCall n) {
		n.e.accept(this);
		n.i.accept(this);
		for (int i = 0; i < n.el.size(); i++) {
			n.el.elementAt(i).accept(this);
		}
	}

	// int i;
	public void visit(TExpIntegerLiteral n) {
	}

	public void visit(TExpTrue n) {
	}

	public void visit(TExpFalse n) {
	}

	// String s;
	public void visit(TExpId n) {
	}

	public void visit(TExpThis n) {
	}

	// TExp e;
	public void visit(TExpNewArray n) {
		n.e.accept(this);
	}

	// TIdentifier i;
	public void visit(TExpNewObject n) {
	}

	// TExp e;
	public void visit(TExpNot n) {
		n.e.accept(this);
	}

	// String s;
	public void visit(TIdentifier n) {
	}
}
