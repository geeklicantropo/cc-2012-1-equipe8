package Main;

import gramatica.*;
import syntaxtree.TProgram;
import visitor.PrettyPrintVisitor;

public class TesteAnaliseLexica {
	public static void main(String[] args) throws ParseException {
		System.out.println("Informe o código do programa e digite '$' no fim.");
		MiniJava miniJava = new MiniJava(System.in);
		TProgram p = MiniJava.Program();
		PrettyPrintVisitor prettyPrintVisitor = new PrettyPrintVisitor();
		p.accept(prettyPrintVisitor);
		System.out.println("Analise sintática ok.");
	}
	
/*//Exemplo:
class Factorial {
public static void main(String[] a) {
System.out.println(new Fac().ComputeFac(10));
}
}
class Fac {
public int ComputeFac(int num) {
int num_aux;
if (num < 1)
num_aux = 1;
else
num_aux = num * (this.ComputeFac(num-1));
return num_aux;
}}
$
	 */
}
