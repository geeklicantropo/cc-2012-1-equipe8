package syntaxtree;
import visitor.ExpVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class TExpFalse extends TExp {
  public void accept(Visitor v) {
    v.visit(this);
  }

  public TType accept(TypeVisitor v) {
    return v.visit(this);
  }
  
//  public translate.Exp accept(ExpVisitor v) {
//	    return v.visit(this);
//	  }
}
