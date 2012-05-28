package syntaxtree;
//import translate.Exp;
import visitor.ExpVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class TExpId extends TExp {
  public String s;
  public TExpId(String as) { 
    s=as;
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
