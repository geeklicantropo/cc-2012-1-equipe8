package visitor;



import symboltable.Symbol;
import symboltable.Table;
import syntaxtree.TClassDeclExtends;
import syntaxtree.TClassDeclSimple;
import syntaxtree.TFormal;
import syntaxtree.TMainClass;
import syntaxtree.TMethodDecl;
import syntaxtree.TProgram;
import syntaxtree.TStatementArrayAssign;
import syntaxtree.TStatementAssign;
import syntaxtree.TStatementIf;
import syntaxtree.TStatementPrint;
import syntaxtree.TStatementWhile;
import syntaxtree.TType;
import syntaxtree.TTypeBoolean;
import syntaxtree.TTypeInteger;
import syntaxtree.TTypeIntegerArray;
import syntaxtree.TVarDecl;

public class TypeCheckVisitor extends DepthFirstVisitor {

	static Class currClass;
    static Method currMethod;
    static SymbolTable symbolTable;
   
    public TypeCheckVisitor(SymbolTable s){
	symbolTable = s;

    }

  // MainClass m;
  // ClassDeclList cl;
  public void visit(TProgram n) {
    n.m.accept(this);
    for ( int i = 0; i < n.cl.size(); i++ ) {
        n.cl.elementAt(i).accept(this);
    }
  }
  
  // Identifier i1,i2;
  // Statement s;
  public void visit(TMainClass n) {
      String i1 = n.i1.toString();
      currClass = symbolTable.getClass(i1);
      
      n.i2.accept(this);
      n.s.accept(this);
  }
  
  // Identifier i;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(TClassDeclSimple n) {
    String id = n.i.toString();
    currClass = symbolTable.getClass(id);
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.elementAt(i).accept(this);
    }
    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.elementAt(i).accept(this);
    }
  }
 
  // Identifier i;
  // Identifier j;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(TClassDeclExtends n) {
    String id = n.i.toString();
    currClass = symbolTable.getClass(id);
    n.j.accept(this);
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.elementAt(i).accept(this);
    }
    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.elementAt(i).accept(this);
    }
  }

  
 
public void visit(TVarDecl n) {
    n.t.accept(this);
    n.i.accept(this);
  }

 
 
public void visit(TMethodDecl n) {
    n.t.accept(this);
    String id = n.i.toString();
    currMethod = currClass.getMethod(id);
    TType retType = currMethod.type();
    for ( int i = 0; i < n.fl.size(); i++ ) {
        n.fl.elementAt(i).accept(this);
    }
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.elementAt(i).accept(this);
    }
    for ( int i = 0; i < n.sl.size(); i++ ) {
        n.sl.elementAt(i).accept(this);
    }
    if (symbolTable.compareTypes(retType, n.e.accept(new TypeCheckExpVisitor()))==false){
	System.out.println("Erro no tipo de retorno para o metodo "+ id);
	System.exit(0);
    }
  }

  // Type t;
  // Identifier i;
  public void visit(TFormal n) {
      n.t.accept(this);
      n.i.accept(this);
  }

  // Exp e;
  // Statement s1,s2;
  public void visit(TStatementIf n) {
    if (! (n.e.accept(new TypeCheckExpVisitor()) instanceof TTypeBoolean) ) {
       System.out.println("A condicao de um IF deve ser"+
                          " do tipo boolean");
       System.exit(-1);
    }
    n.s1.accept(this);
    n.s2.accept(this);
  }

  // Exp e;
  // Statement s;
  public void visit(TStatementWhile n) {
    if (! (n.e.accept(new TypeCheckExpVisitor()) instanceof TTypeBoolean) ) {
       System.out.println("A condicao de um WHILE deve ser"+
                          " do tipo boolean");
       System.exit(-1);
    }
    n.s.accept(this);
  }

  public void visit(TStatementPrint n) {
    if (! (n.e.accept(new TypeCheckExpVisitor()) instanceof TTypeInteger) ) {
       System.out.println("O argumento para System.out.println deve ser"+
                          " do tipo int");
       System.exit(-1);
    }
  }
  
  public void visit(TStatementAssign n) {
    TType t1 = symbolTable.getVarType(currMethod,currClass,n.i.toString());
    TType t2 = n.e.accept(new TypeCheckExpVisitor() );
    if (symbolTable.compareTypes(t1,t2)==false){
	System.out.println("Erro de tipagem na atribuicao para "+n.i.toString());	
	System.exit(0);
    }
  }

  public void visit(TStatementArrayAssign n) {
      TType typeI = symbolTable.getVarType(currMethod,currClass,n.i.toString());
      
      if (! (typeI instanceof TTypeIntegerArray) ) {
	  System.out.println("O identificador em uma atribuicao de vetor"+
			     " deve ser do tipo int[]");
	  System.exit(-1);
      }
      
    if (! (n.e1.accept(new TypeCheckExpVisitor()) instanceof TTypeInteger) ) {
       System.out.println("A primeira expressao em uma atribuicao de vetor"+
                          " deve ser do tipo int");
       System.exit(-1);
    }
    if (! (n.e2.accept(new TypeCheckExpVisitor()) instanceof TTypeInteger) ) {
       System.out.println("A segunda expressao em uma atribuicao de vetor"+
                          " deve ser do tipo int");
       System.exit(-1);
    }
  }
}