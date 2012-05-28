package syntaxtree;
import CheckType.TypeCheckVisitor;
import visitor.*;

public abstract class TClassDecl {
  public abstract void accept(Visitor v);
  public abstract TType accept(TypeVisitor v);
//  public abstract translate.Exp accept(ExpVisitor v);
}
