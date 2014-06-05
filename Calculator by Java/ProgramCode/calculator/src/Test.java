import java.util.Scanner;

/**
 * used to test the program.
 */
public class Test {

    public static void main(String[] args) {
        
		System.out.println("Please input the expression(+,-,*,/,^,(,) are supported):");
		Scanner in=new Scanner(System.in);
		String str = in.nextLine();
//    	str="(3*(1+2))^2";
    	Calculator cal = new Calculator();
        double d = cal.eval(str);
        System.out.println("result="+d);
    }
}