package syntaxtree;
import visitor.ExpVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class TProgram extends TExp{
  public TMainClass m;
  public TClassDeclList cl;

  public TProgram(TMainClass am, TClassDeclList acl) {
    m=am; cl=acl; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public TType accept(TypeVisitor v) {
    return v.visit(this);
  }
  
  public translate.Exp accept(ExpVisitor v) {
	    return v.visit(this);
	  }
}
