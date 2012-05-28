package visitor;
import syntaxtree.*;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;

class SymbolTable {
   private Hashtable hashtable;
   
   public SymbolTable() {
	    hashtable = new Hashtable();
    }
	
    public boolean addClass(String id, String parent) {
	    if(containsClass(id)) 
                    return false;	    
	    else 
		hashtable.put(id, new Class(id, parent));
	    return true;	    
    }
	
    public Class getClass(String id) {
	    if(containsClass(id)) 
		return (Class)hashtable.get(id);	    
	    else 
		return null;
    }

    public boolean containsClass(String id) {
	    return hashtable.containsKey(id);
    }

    
	
    public TType getVarType(Method m, Class c, String id) {
      if(m != null) {
	  if(m.getVar(id) != null) {
	      return m.getVar(id).type();
	  }
	  if(m.getParam(id) != null){
	     return m.getParam(id).type();
	  }
      }
      
      while(c != null) {
	  if(c.getVar(id) != null) {
	      return c.getVar(id).type();
	  }
	  else {
	      if(c.parent() == null) {
		  c = null;
	      }
	      else {
		  c = getClass(c.parent());
	      }
	  }
      }
            
      System.out.println("Variavel " + id 
			 + " nao definida no escopo atual");
      System.exit(0);
      return null;
  }

  public Method getMethod(String id, String classScope) {
	if(getClass(classScope)==null) {
	    System.out.println("Classe " + classScope 
			       + " nao definida");  
	    System.exit(0);
	}

	Class c = getClass(classScope);
	while(c != null) {
	    if(c.getMethod(id) != null) {
		return c.getMethod(id);
	    }
	    else {
		if(c.parent() == null) {
		    c = null;
		}
		else {
		    c = getClass(c.parent());
		}
	    }
	}

	
	System.out.println("Metodo " + id + " nao definido na classe " + classScope);
	
	System.exit(0);
	return null;
    }

    public TType getMethodType(String id, String classScope) {
	if(getClass(classScope)==null) {
	    System.out.println("Classe " + classScope 
			       + " nao definida");  
	    System.exit(0);
	}

	Class c = getClass(classScope);
	while(c != null) {
	    if(c.getMethod(id) != null) {
		return c.getMethod(id).type();
	    }
	    else {
		if(c.parent() == null) {
		    c = null;
		}
		else {
		    c = getClass(c.parent());
		}
	    }
	}
	
	System.out.println("Metodo " + id + " nao definido na classe " + classScope);	
	System.exit(0);
	return null;
    }

    public boolean compareTypes(TType t1, TType t2){
	
	if (t1 == null || t2 == null) return false;
	
	if (t1 instanceof TTypeInteger && t2 instanceof  TTypeInteger)
	    return true;
	if (t1 instanceof TTypeBoolean && t2 instanceof  TTypeBoolean)
	    return true;
	if (t1 instanceof TTypeIntegerArray && t2 instanceof TTypeIntegerArray)
	    return true;
	if (t1 instanceof TTypeIdentifier && t2 instanceof TTypeIdentifier){
		TTypeIdentifier i1 = (TTypeIdentifier)t1;
		TTypeIdentifier i2 = (TTypeIdentifier)t2;
	    
	    Class c = getClass(i2.s);
	    while(c != null) {
		if (i1.s.equals(c.getId())) return true;
		else {
		    if(c.parent() == null) return false;
		    c = getClass(c.parent());
		}
	    }
	}
	return false;	
    }

}//SymbolTable

class Class {

    String id;
    Hashtable methods;
    Hashtable globals;
    String parent;
    TType type;
    
    public Class(String id, String p) {
	this.id = id;
	parent = p;
	type = new TTypeIdentifier(id);
	methods = new Hashtable();
	globals = new Hashtable();
    }
    
    public Class() {}
    
    public String getId(){ return id; }
    
    public TType type(){ return type; }
    
    public boolean addMethod(String id, TType type) {
	if(containsMethod(id)) 
	    return false;       
	else {
	    methods.put(id, new Method(id, type));
	    return true;
	}
    }
	
    public Enumeration getMethods(){
	return methods.keys();
    }
    
    public Method getMethod(String id) {
	if(containsMethod(id)) 
	    return (Method)methods.get(id);
	else 
	    return null;
    }
	
    public boolean addVar(String id, TType type) {
	if(globals.containsKey(id)) 
	    return false;
	else{
	    globals.put(id, new Variable(id, type));
	    return true;
	}
    }
    
    public Variable getVar(String id) {
	if(containsVar(id)) 
	    return (Variable)globals.get(id);
	else 
	    return null;
    }
    
    public boolean containsVar(String id) {
	return globals.containsKey(id);
    }
    
    public boolean containsMethod(String id) {
	return methods.containsKey(id);
    }
    
    public String parent() {
	return parent;
    }	    
} // Class

class Variable {
	
    String id;
    TType type;
    
    public Variable(String id, TType type) {
	this.id = id;
	this.type = type;
    }
    
    public String id() { return id; }
    
    public TType type() { return type; }
	
} // Variable

class Method {
	
    String id;
    TType type;
    Vector params;
    Hashtable vars;
    
    public Method(String id, TType type) {
	this.id = id;
	this.type = type;
	vars = new Hashtable();
	params = new Vector();
    }
    
    public String getId() { return id; }
    
    public TType type() { return type; }
    
    
    public boolean addParam(String id, TType type) {
	if(containsParam(id)) 
	    return false;       
	else {
	    params.addElement(new Variable(id, type));
	    return true;
	}
    }
    
    public Enumeration getParams(){
	return params.elements();
    }

    public Variable getParamAt(int i){
	if (i<params.size())
	    return (Variable)params.elementAt(i);
	else
	    return null;
    }

    public boolean addVar(String id, TType type) {
	if(vars.containsKey(id)) 
	    return false;
	else{
	    vars.put(id, new Variable(id, type));
	    return true;
	}
    }
    
    public boolean containsVar(String id) {
	return vars.containsKey(id);
    }
    
    public boolean containsParam(String id) {
	for (int i = 0; i< params.size(); i++)
	    if (((Variable)params.elementAt(i)).id.equals(id))
		return true;
	return false;
    }
    
    public Variable getVar(String id) {
	if(containsVar(id)) 
	    return (Variable)vars.get(id);
	else 
	    return null;
    }

    public Variable getParam(String id) {
	
	for (int i = 0; i< params.size(); i++)
	    if (((Variable)params.elementAt(i)).id.equals(id))
		return (Variable)(params.elementAt(i));
	
	return null;
    }
    
} // Method