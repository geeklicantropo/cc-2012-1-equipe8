package Main;

import gramatica.MiniJava;
import gramatica.ParseException;
import syntaxtree.TProgram;
import visitor.BuildSymbolTableVisitor;
import visitor.PrettyPrintVisitor;
import visitor.TypeCheckVisitor;

public class TesteChecagemTipo {
	public static void main(String[] args) throws ParseException {
		System.out.println("Informe o código do programa e digite '$' no fim.");
		MiniJava miniJava = new MiniJava(System.in);
		TProgram tProgram = MiniJava.Program();
		PrettyPrintVisitor prettyPrintVisitor = new PrettyPrintVisitor();
		tProgram.accept(prettyPrintVisitor);
		System.out.println("Analise sintática ok.");
		//Checagem de Tipo
		BuildSymbolTableVisitor bTableVisitor = new BuildSymbolTableVisitor();
		tProgram.accept(bTableVisitor);
		tProgram.accept(new TypeCheckVisitor(bTableVisitor.getSymTab()));
		System.out.println("Checagem de Tipo Terminada com Sucesso!");
		
		
	}
	
/*//Exemplo:
class Factorial {
public static void main(String[] a) {
System.out.println(new Fac().ComputeFac(10));
}
}
class Fac {
public int ComputeFac(int num) {
boolean num_aux;
if (num < 1)
num_aux = 1;
else
num_aux = num * (this.ComputeFac(num-1));
return num_aux;
}}
$
	 */
}
