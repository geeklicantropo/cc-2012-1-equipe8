package Error;

public class PrintError {
	static boolean anyErrors;
    
    public static void complain(String msg){
            anyErrors = true;
            System.out.println(msg);
    }

}
