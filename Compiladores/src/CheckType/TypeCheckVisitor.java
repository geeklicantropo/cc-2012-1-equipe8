package CheckType;

import symboltable.Symbol;
import symboltable.Table;
import syntaxtree.TClassDeclExtends;
import syntaxtree.TClassDeclSimple;
import syntaxtree.TExpArrayLength;
import syntaxtree.TExpArrayLookup;
import syntaxtree.TExpCall;
import syntaxtree.TExpFalse;
import syntaxtree.TExpIntegerLiteral;
import syntaxtree.TExpNewArray;
import syntaxtree.TExpNewObject;
import syntaxtree.TExpNot;
import syntaxtree.TExpOpAnd;
import syntaxtree.TExpOpLessThan;
import syntaxtree.TExpOpMinus;
import syntaxtree.TExpOpPlus;
import syntaxtree.TExpOpTimes;
import syntaxtree.TExpThis;
import syntaxtree.TExpTrue;
import syntaxtree.TFormal;
import syntaxtree.TIdentifier;
import syntaxtree.TMainClass;
import syntaxtree.TMethodDecl;
import syntaxtree.TProgram;
import syntaxtree.TStatement;
import syntaxtree.TStatementArrayAssign;
import syntaxtree.TStatementAssign;
import syntaxtree.TStatementBlock;
import syntaxtree.TStatementIf;
import syntaxtree.TStatementPrint;
import syntaxtree.TStatementWhile;
import syntaxtree.TTypeBoolean;
import syntaxtree.TTypeIdentifier;
import syntaxtree.TTypeInteger;
import syntaxtree.TTypeIntegerArray;
import symboltable.*;

public class TypeCheckVisitor implements CheckVisitorInterface {

	private Table table;
	
	public TypeCheckVisitor(Table t) {
		table = t;
	}
	
	@Override
	public Table visit(TProgram n) {

		n.m.accept(this);
		for(int i=0;i<n.cl.size();i++)
		{
			//n.cl.elementAt(i).accept(this);
		}
		return null;
	}

	@Override
	public void visit(TMainClass n) {
		table.beginScope();
		table.endScope();
		
		
	}

	@Override
	public void visit(TClassDeclSimple n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TClassDeclExtends n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TMethodDecl n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Symbol visit(TFormal n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TTypeIntegerArray n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TTypeBoolean n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TTypeInteger n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TStatement n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visit(TStatementBlock n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TStatementIf n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TStatementWhile n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TStatementPrint n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TStatementAssign n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TStatementArrayAssign n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Symbol visit(TExpOpAnd n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpOpLessThan n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpOpPlus n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpOpMinus n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpOpTimes n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpArrayLookup n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpArrayLength n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpCall n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpIntegerLiteral n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpTrue n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpFalse n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TTypeIdentifier n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpThis n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpNewArray n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpNewObject n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TExpNot n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Symbol visit(TIdentifier n) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
