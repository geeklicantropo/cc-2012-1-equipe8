package CheckType;

import symboltable.*;
import syntaxtree.*;

public interface CheckVisitorInterface 
{

	public Table visit(TProgram n);
    public void visit(TMainClass n);
    public void visit(TClassDeclSimple n);
    public void visit(TClassDeclExtends n);
    public void visit(TMethodDecl n);
    public Symbol visit(TFormal n);
    public Symbol visit(TTypeIntegerArray n);
    public Symbol visit(TTypeBoolean n);
    public Symbol visit(TTypeInteger n);
    public Symbol visit(TStatement n);
    public void visit(TStatementBlock n);
    public void visit(TStatementIf n);
    public void visit(TStatementWhile n);
    public void visit(TStatementPrint n);
    public void visit(TStatementAssign n);
    public void visit(TStatementArrayAssign n);
    public Symbol visit(TExpOpAnd n);
    public Symbol visit(TExpOpLessThan n);
    public Symbol visit(TExpOpPlus n);
    public Symbol visit(TExpOpMinus n);
    public Symbol visit(TExpOpTimes n);
    public Symbol visit(TExpArrayLookup n);
    public Symbol visit(TExpArrayLength n);
    public Symbol visit(TExpCall n);
    public Symbol visit(TExpIntegerLiteral n);
    public Symbol visit(TExpTrue n);
    public Symbol visit(TExpFalse n);
    public Symbol visit(TTypeIdentifier n);
    public Symbol visit(TExpThis n);
    public Symbol visit(TExpNewArray n);
    public Symbol visit(TExpNewObject n);
    public Symbol visit(TExpNot n);
    public Symbol visit(TIdentifier n);
}
