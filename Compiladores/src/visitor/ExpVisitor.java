package visitor;

import syntaxtree.*;
import translate.Exp;

public interface ExpVisitor
{
  public Exp visit(TProgram n);
  public Exp visit(TMainClass n);
  public Exp visit(TClassDeclSimple n);
  public Exp visit(TClassDeclExtends n);
  public Exp visit(TVarDecl n);
  public Exp visit(TMethodDecl n);
  public Exp visit(TFormal n);
  public Exp visit(TTypeIntegerArray n);
  public Exp visit(TTypeBoolean n);
  public Exp visit(TTypeInteger n);
  public Exp visit(TTypeIdentifier n);
  public Exp visit(TStatementBlock n);
  public Exp visit(TStatementIf n);
  public Exp visit(TStatementWhile n);
  public Exp visit(TStatementPrint n);
  public Exp visit(TStatementAssign n);
  public Exp visit(TStatementArrayAssign n);
  public Exp visit(TExpOpAnd n);
  public Exp visit(TExpOpLessThan n);
  public Exp visit(TExpOpPlus n);
  public Exp visit(TExpOpMinus n);
  public Exp visit(TExpOpTimes n);
  public Exp visit(TExpArrayLookup n);
  public Exp visit(TExpArrayLength n);
  public Exp visit(TExpCall n);
  public Exp visit(TExpIntegerLiteral n);
  public Exp visit(TExpTrue n);
  public Exp visit(TExpFalse n);
  public Exp visit(TExpId n);
  public Exp visit(TExpThis n);
  public Exp visit(TExpNewArray n);
  public Exp visit(TExpNewObject n);
  public Exp visit(TExpNot n);
  public Exp visit(TIdentifier n);
}
