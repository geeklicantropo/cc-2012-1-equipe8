package syntaxtree;
import translate.Exp;
import visitor.ExpVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class TExpNewArray extends TExp {
  public TExp e;
  
  public TExpNewArray(TExp ae) {
    e=ae; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public TType accept(TypeVisitor v) {
    return v.visit(this);
  }

//@Override
//public Exp accept(ExpVisitor v) {
//	// TODO Auto-generated method stub
//	return v.visit(this);
//}
}
