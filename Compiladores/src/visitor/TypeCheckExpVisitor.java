package visitor;
import syntaxtree.*;

public class TypeCheckExpVisitor extends TypeDepthFirstVisitor {
   
  // Exp e1,e2;
  public TType visit(TExpOpAnd n) {
    if (! (n.e1.accept(this) instanceof TTypeBoolean) ) {
       System.out.println("Lado esquerdo de um AND deve ser do tipo inteiro");
       System.exit(-1);
    }
    if (! (n.e2.accept(this) instanceof TTypeBoolean) ) {
       System.out.println("Lado direito de um AND deve ser do tipo inteiro");
       System.exit(-1);
    }
    return new TTypeBoolean();
  }

  // Exp e1,e2;
  public TType visit(TExpOpLessThan n) {
    if (! (n.e1.accept(this) instanceof TTypeInteger) ) {
       System.out.println("Lado esquerdo de um LessThan deve ser do tipo inteiro");
       System.exit(-1);
    }
    if (! (n.e2.accept(this) instanceof TTypeInteger) ) {
       System.out.println("Lado direito de um LessThan deve ser do tipo inteiro");
       System.exit(-1);
    }
    return new TTypeBoolean();
  }

  // Exp e1,e2;
  public TType visit(TExpOpPlus n) {
    if (! (n.e1.accept(this) instanceof TTypeInteger) ) {
       System.out.println("Lado esquerdo de um Plus deve ser do tipo inteiro");
       System.exit(-1);
    }
    if (! (n.e2.accept(this) instanceof TTypeInteger) ) {
       System.out.println("Lado direito de um Plus deve ser do tipo inteiro");
       System.exit(-1);
    }
    return new TTypeInteger();
  }

  // Exp e1,e2;
  public TType visit(TExpOpMinus n) {
    if (! (n.e1.accept(this) instanceof TTypeInteger) ) {
       System.out.println("Lado esquerdo de um Minus deve ser do tipo inteiro");
       System.exit(-1);
    }
    if (! (n.e2.accept(this) instanceof TTypeInteger) ) {
       System.out.println("Lado direito de um Minus deve ser do tipo inteiro");
       System.exit(-1);
    }
    return new TTypeInteger();
  }

  // Exp e1,e2;
  public TType visit(TExpOpTimes n) {
    if (! (n.e1.accept(this) instanceof TTypeInteger) ) {
       System.out.println("Lado esquerdo de um Times deve ser do tipo inteiro");
       System.exit(-1);
    }
    if (! (n.e2.accept(this) instanceof TTypeInteger) ) {
       System.out.println("Lado direito de um Times deve ser do tipo inteiro");
       System.exit(-1);
    }
    return new TTypeInteger();
  }

  // Exp e1,e2;
  public TType visit(TExpArrayLookup n) {
    if (! (n.e1.accept(this) instanceof TTypeIntegerArray) ) {
       System.out.println("alvo de um ArrayLookup deve ser do tipo IntArray");
       System.exit(-1);
    }
    if (! (n.e2.accept(this) instanceof TTypeInteger) ) {
       System.out.println("indice de um ArrayLookup deve ser do tipo inteiro");
       System.exit(-1);
    }
    return new TTypeInteger();
  }

  // Exp e;
  public TType visit(TExpArrayLength n) {
    if (! (n.e.accept(this) instanceof TTypeIntegerArray) ) {
       System.out.println("alvo de um ArrayLength deve ser do tipo Array");
       System.exit(-1);
    }
    return new TTypeInteger();
  }

  // Exp e;
  // Identifier i;
  // ExpList el;
  public TType visit(TExpCall n) {

    if (! (n.e.accept(this) instanceof TTypeIdentifier)){
	System.out.println("metodo "+ n.i.toString() 
			   + " chamado em estrutura que nao é"+
			   " classe or Object.");
	System.exit(-1);
    } 

    String mname = n.i.toString();    
    String cname = ((TTypeIdentifier) n.e.accept(this)).s;

    Method calledMethod = TypeCheckVisitor.symbolTable.getMethod(mname,cname);
    
    for ( int i = 0; i < n.el.size(); i++ ) {     	
	TType t1 =null;  
	TType t2 =null;  

	if (calledMethod.getParamAt(i)!=null)
	    t1 = calledMethod.getParamAt(i).type();
	t2 = n.el.elementAt(i).accept(this);
	if (!TypeCheckVisitor.symbolTable.compareTypes(t1,t2)){
	    System.out.println("Erro de tipagem em argumento passado para " +
			       cname+"." +mname);
	    System.exit(-1);  
	}	    
    }

    return TypeCheckVisitor.symbolTable.getMethodType(mname,cname);
  }

  // int i;
  public TType visit(TExpIntegerLiteral n) {
    return new TTypeInteger();
  }

  public TType visit(TExpTrue n) {
    return new TTypeBoolean();
  }

  public TType visit(TExpFalse n) {
    return new TTypeBoolean();
  }

  // String s;
	public TType visit(TExpId n) {      
    return TypeCheckVisitor.symbolTable.getVarType(TypeCheckVisitor.currMethod,
		      TypeCheckVisitor.currClass,n.s);
  }

  public TType visit(TExpThis n) {
      return TypeCheckVisitor.currClass.type();
  }

  // Exp e;
  public TType visit(TExpNewArray n) {
    
    if (! (n.e.accept(this) instanceof TTypeInteger) ) {
       System.out.println("Expressao Array em NewArray deve ser do tipo inteiro");
       System.exit(-1);
    }
    return new TTypeIntegerArray();
  }

  // Identifier i;
  public TType visit(TExpNewObject n) {
    return new TTypeIdentifier(n.i.s);
  }

  // Exp e;
  public TType visit(TExpNot n) {
    if (! (n.e.accept(this) instanceof TTypeBoolean) ) {
       System.out.println("Expressao NOT deve ser do tipo boolean");
       System.exit(-1);
    }
    return new TTypeBoolean();
  }

}

