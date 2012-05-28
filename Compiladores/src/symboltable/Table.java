package symboltable;

import java.util.*;

import syntaxtree.TMethodDecl;

class Binder {
  Object valor;
  Symbol symbolTopo;
  Binder prox;
  Binder(Object v, Symbol p, Binder t) {
	valor=v; symbolTopo=p; prox=t;
  }
}

public class Table {

	
  private Class currentClass = null;
  private TMethodDecl currentMethod = null;
  
  private Dictionary dict = new Hashtable();
  private Symbol top;
  private Binder binder;

  public Table(Symbol s){
	  this.top = s;
  }
  
  public Table()
  {
	  
  }

  public Object get(Symbol key) {
	Binder e = (Binder)dict.get(key);
	if (e==null) return null;
	else return e.valor;
  }	

  public void put(Symbol key, Object valor) {
	dict.put(key, new Binder(valor, top, (Binder)dict.get(key)));
	top = key;
  }

  public void beginScope() {binder = new Binder(null,top,binder); top=null;}

  public void endScope() {
	while (top!=null) {
	   Binder e = (Binder)dict.get(top);
	   if (e.prox!=null) dict.put(top,e.prox);
	   else dict.remove(top);
	   top = e.symbolTopo;
	}
	top=binder.symbolTopo;
	binder=binder.prox;
  }
  
  public Enumeration keys() {return dict.keys();}
}

