


 
import java.util.HashMap;
import java.util.Map;

public abstract class Operator {
    
    /**
     * operator
     */
    private char operator;
    
    /**
     * The operator priority.the bigger number£¬the higher priority.
     */
    private int priority;
    
    private static Map<Character, Operator> operators = new HashMap<Character, Operator>();
    
    private Operator(char operator, int priority) {
        setOperator(operator);
        setPriority(priority);
        register(this);
    }
    
    private void register(Operator operator) {
        operators.put(operator.getOperator(), operator);
    }
    
    /**
     * additive operation
     */
    public final static Operator ADITION = new Operator('+', 100) {
            public double eval(double left, double right) {
                return left + right;
            }            
        };
    
    /**
     * subtraction operation

     */
    public final static Operator SUBTRATION = new Operator('-', 100) {
            public double eval(double left, double right) {
                return left - right;
            }            
        };
    
    /**
     * multiplication
     */ 
    public final static Operator MULTIPLICATION = new Operator('*', 200) {
            public double eval(double left, double right) {
                return left * right;
            }            
        };
    
    /**
     * division operation
     */
    public final static Operator DIVITION = new Operator('/', 200) {
            public double eval(double left, double right) {
                return left / right;
            }            
        };
    
    /**
     * pow operation
     */
    public final static Operator EXPONENT = new Operator('^', 300) {
            public double eval(double left, double right) {
                return Math.pow(left, right);
            }            
        };
        
    
    
    public char getOperator() {
        return operator;
    }
    private void setOperator(char operator) {
        this.operator = operator;
    }
    public int getPriority() {
        return priority;
    }
    private void setPriority(int priority) {
        this.priority = priority;
    }
    
    /**
     *get the priority according to the operator.
     * @param c
     * @return   the priority of the operators
     */
    public static int getPrority(char c) {
        Operator op = operators.get(c);
        if(op != null) {
            return op.getPriority();
        }
        return 0;
    }
    
    /**
     * Determine whether a character is the operator
     * @param c
     * @return  ¡°yes¡± return true£¬¡°no¡± return false
     */    
    public static boolean isOperator(char c) {
        return getInstance(c) != null;
    }
    public static boolean isOperator(String str) {
        if(str.length() > 1) {
            return false;
        }
        return isOperator(str.charAt(0));
    }

    /**
     * get the instance according to the operators
     * @param c
     * @return     return the instance from the register; if not exist return null.
     */
    public static Operator getInstance(char c) {       
        return operators.get(c);
    }    
    public static Operator getInstance(String str) {
        if(str.length() > 1) {
            return null;
        }
        return getInstance(str.charAt(0));
    }

    /**
     * calculate according to the operands
     * @param left    The left operand
     * @param right   The right operand
     * @return        the result
     */
    public abstract double eval(double left, double right); 
}
