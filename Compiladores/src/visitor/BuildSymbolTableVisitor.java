package visitor;
import syntaxtree.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class BuildSymbolTableVisitor extends TypeDepthFirstVisitor {

    SymbolTable symbolTable;
    public boolean stop(){
    	return false;
    }
    private String erros="";
    public void addErro(String erro){
    	erros+=erro;
    }
    public String getErros(){
    	return erros;
    }

    public BuildSymbolTableVisitor(){
	symbolTable = new SymbolTable();
    }

    public SymbolTable getSymTab(){
	return symbolTable;
    }

    private Class currClass;
    private Method currMethod;
  

  // MainClass m;
  // ClassDeclList cl;
  public TType visit(TProgram n) {
    n.m.accept(this);
    for ( int i = 0; i < n.cl.size(); i++ ) {
        n.cl.elementAt(i).accept(this);
    }
    return null;
  }
  
  // Identifier i1,i2;
  // Statement s;
  public TType visit(TMainClass n) {
     symbolTable.addClass( n.i1.toString(), null); 
     currClass = symbolTable.getClass(n.i1.toString());
      
    //this is an ugly hack.. but its not worth having a Void and
    //String[] type just for one occourance
    currMethod = new Method ("main", new TTypeIdentifier("void"));
    currMethod.addVar(n.i2.toString(),
		      new TTypeIdentifier("String[]"));
    n.s.accept(this);

    currMethod = null;
          
    return null;
  }
  
  // Identifier i;
  // VarDeclList vl;
  // MethodDeclList ml;
  public TType visit(TClassDeclSimple n) {
    if(!symbolTable.addClass( n.i.toString(), null)){
	addErro("Classe " +  n.i.toString()
			   + " ja esta definida" ); 
	if(stop()) return null;
    }
    currClass =  symbolTable.getClass(n.i.toString());
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.elementAt(i).accept(this);
    }
    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.elementAt(i).accept(this);
    }
    return null;
  }
 
  // Identifier i;
  // Identifier j;
  // VarDeclList vl;
  // MethodDeclList ml;
  public TType visit(TClassDeclExtends n) {
    if(!symbolTable.addClass( n.i.toString(),  n.j.toString())){
	addErro("Classe " +  n.i.toString()
			   + " ja esta definida" ); 
	if(stop()) return null;
    }
    currClass = symbolTable.getClass(n.i.toString());
  
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.elementAt(i).accept(this);
    }
    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.elementAt(i).accept(this);
    }
    return null;
  }

  // Type t;
  // Identifier i;
  public TType visit(TVarDecl n) {
    
     TType t =  n.t.accept(this);
     String id =  n.i.toString();

      if (currMethod == null){
	  if (!currClass.addVar(id,t)){
	      addErro("Identificador " + id + "  ja esta definido na " 
				 + currClass.getId()); 
	      if(stop()) return null;
	  }
      } else {
	  
	  if (!currMethod.addVar(id,t)){
	      addErro("Identificador " + id + "  ja esta definido na " 
				 + currClass.getId() + "." +
				 currMethod.getId());
	      if(stop()) return null;
	  }
      }
    return null;
  }

  // Type t;
  // Identifier i;
  // FormalList fl;
  // VarDeclList vl;
  // StatementList sl;
  // Exp e;
  public TType visit(TMethodDecl n) {
    TType t = n.t.accept(this);
    String id = n.i.toString();
    
    if (!currClass.addMethod(id,t)){
	addErro("Metodo " + id 
			   + " ja esta definido na " 
			   + currClass.getId()); 
	if(stop()) return null;
    }

    currMethod = currClass.getMethod(id);

    for ( int i = 0; i < n.fl.size(); i++ ) {
        n.fl.elementAt(i).accept(this);
    }
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.elementAt(i).accept(this);
    }
    for ( int i = 0; i < n.sl.size(); i++ ) {
        n.sl.elementAt(i).accept(this);
    }

    n.e.accept(this);
    currMethod = null;
    return null;
  }

  // Type t;
  // Identifier i;
  public TType visit(TFormal n) {
      
    TType t = n.t.accept(this);
    String id = n.i.toString();
    
    if (!currMethod.addParam(id,t)){
	addErro("Formal" + id + " ja esta definido na " 
			   + currClass.getId() + "." +
			   currMethod.getId());
	if(stop()) return null;
    }
    return null;
  }

  public TType visit(TTypeIntegerArray n) {
    return n; 
  }

  public TType visit(TTypeBoolean n) {
    return n;
  }

  public TType visit(TTypeInteger n) {
    return n;
  }

  // String s;
  public TType visit(TTypeIdentifier n) {
    return n;
  }

  // StatementList sl;
  public TType visit(TStatementBlock n) {
    for ( int i = 0; i < n.sl.size(); i++ ) {
        n.sl.elementAt(i).accept(this);
    }
    return null;
  }
}
