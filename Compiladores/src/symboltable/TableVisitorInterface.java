package symboltable;
import syntaxtree.*;

public interface TableVisitorInterface 
{

	public Table visit(TProgram n);
    public Table visit(TClassDeclSimple n);
    public Table visit(TClassDeclExtends n);
    public VarInfo visit(TVarDecl n);
    public Table visit(TMethodDecl n);
    public VarInfo visit(TFormal n);
    public Symbol visit(TTypeIntegerArray n);
    public Symbol visit(TTypeBoolean n);
    public Symbol visit(TTypeInteger n);
    public Symbol visit(TTypeIdentifier n);
    public Symbol visit(TIdentifier n);
}
