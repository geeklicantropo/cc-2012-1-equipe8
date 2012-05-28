package syntaxtree;
import translate.Exp;
import visitor.ExpVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class TExpNot extends TExp {
  public TExp e;
  
  public TExpNot(TExp ae) {
    e=ae; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public TType accept(TypeVisitor v) {
    return v.visit(this);
  }

//@Override
//public translate.Exp accept(ExpVisitor v) {
//	// TODO Auto-generated method stub
//	return v.visit(this);
//}
}
