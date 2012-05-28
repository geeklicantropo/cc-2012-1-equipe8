package syntaxtree;
//import translate.Exp;
import visitor.ExpVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class TExpOpLessThan extends TExp {
  public TExp e1,e2;
  
  public TExpOpLessThan(TExp ae1, TExp ae2) {
    e1=ae1; e2=ae2;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public TType accept(TypeVisitor v) {
    return v.visit(this);
  }
/*
@Override
public void TStatement(Visitor v) {
	// TODO Auto-generated method stub
	
}*/

//@Override
//public Exp accept(ExpVisitor v) {
//	// TODO Auto-generated method stub
//	return null;
//}


}
