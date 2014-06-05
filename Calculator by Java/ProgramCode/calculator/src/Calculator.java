import java.text.NumberFormat;
import java.util.Stack;

public class Calculator implements MathSymbol {
    
    /**
     * Calculate the infix expression
     * @param expression
     * @return
     */
    public double eval(String expression) {
        String str = infix2Suffix(expression);
        System.out.println(" Infix Expression: " + expression);//test
        System.out.println("Suffix Expression: " + str);//test
        if(str == null) {
            throw new IllegalArgumentException("Expression is null!");
        }
        String[] strs = str.split(" ");
        Stack<String> stack = new Stack<String>();
        
        /*Calculate the result of the postfix expression by a stack.*/  
        for(int i = 0; i < strs.length; i++) {
//        	System.out.println("strs____"+strs[i]);//test
            if(!Operator.isOperator(strs[i])) {
                stack.push(strs[i]);
            } else {
                Operator op = Operator.getInstance(strs[i]);
                double right = Double.parseDouble(stack.pop());
                double left = Double.parseDouble(stack.pop());
                double result = op.eval(left, right);
                stack.push(String.valueOf(result));
            }
        }
//        System.out.println(stack.size()+"--------"+stack.peek());//test
        return Double.parseDouble(stack.pop());
    }
    
    /**
     * Convert infix expressions to postfix expression.
     * Use the method of stack and priority.
     * @param expression
     * @return
     */
    public String infix2Suffix(String expression) {
        if(expression == null) {
            return null;
        }
        char[] chs = expression.toCharArray();
        Stack<Character> stack = new Stack<Character>();
        StringBuilder sb = new StringBuilder(chs.length);
        boolean appendSeparator = false;
        boolean sign = true;
        for(int i = 0; i < chs.length; i++) {
            char c = chs[i];
            if(c == BLANK) {
                continue;
            }
            // Next line is used output stack information.         
            if(appendSeparator) {
                sb.append(SEPARATOR);
                appendSeparator = false;
            }
            if(isSign(c) && sign) {
                sb.append(c);
                continue;
            }
            if(isNumber(c)) {
                sign = false;
                sb.append(c);
                continue;
            }            
            if(isLeftBracket(c)) {
                stack.push(c);
                continue;                
            }
            if(isRightBracket(c)) {
                sign = false;
                while(stack.peek() != LEFT_BRACKET) {
                    sb.append(SEPARATOR);
                    sb.append(stack.pop());
                }
                stack.pop();
                continue;
            }
            appendSeparator = true;
            if(Operator.isOperator(c)) {
                sign = true;
                if(stack.isEmpty() || stack.peek() == LEFT_BRACKET) {
                    stack.push(c);
                    continue;
                }
                int precedence = Operator.getPrority(c);
                while(!stack.isEmpty() && Operator.getPrority(stack.peek()) >= precedence) {
                    sb.append(SEPARATOR);
                    sb.append(stack.pop());
                }
                stack.push(c);                
            }
        }
        while(!stack.isEmpty()) {
            sb.append(SEPARATOR);
            sb.append(stack.pop());
        }
        return sb.toString();
    }
    
    /**
     * Determine whether a character is plus or minus sign
     * @param c
     * @return
     */
    private boolean isSign(char c) {
        if(c == NEGATIVE_SIGN || c == POSITIVE_SIGN) {
            return true;
        }
        return false;
    }
    
    /**
     * Determine if a character is a number or a decimal point
     * @param c
     * @return
     */
    private boolean isNumber(char c) {
        if((c >= '0' && c <= '9') || c == DECIMAL_POINT) {
            return true;
        }
        return false;
    }
    
    /**
     * Determine whether a character is left parenthesis
     * @param c
     * @return
     */
    private boolean isLeftBracket(char c) {
        return c == LEFT_BRACKET;
    }
    
    /**
     * Determine whether a character is right parenthesis
     * @param c
     * @return
     */
    private boolean isRightBracket(char c) {
        return c == RIGHT_BRACKET;
    }
}
