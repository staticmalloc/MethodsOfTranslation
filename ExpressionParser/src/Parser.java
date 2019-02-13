import java.util.Scanner;

public class Parser {
    private Lexer lexer;
    private Lexeme current;

    public Parser(String expr){
        this.lexer = new Lexer(new Scanner(expr));
    }

    public double Parse() throws Exception{
        current = lexer.getLexeme();
        return ParseExpr();
    }

    public double ParseExpr() throws Exception{
        double temp = ParseTerm();
        while (current.type == LexemeType.PLUS || current.type == LexemeType.MINUS) {
            LexemeType type = current.type;
            current = lexer.getLexeme();
            switch (type){
                case PLUS: temp+= ParseTerm();
                case MINUS: temp-= ParseTerm();
                default: throw new Exception();
            }
        }
        return temp;
    }

    public double ParseTerm() throws  Exception{
        double temp = ParseFactor();
        while (current.type == LexemeType.PLUS || current.type == LexemeType.MINUS) {
            LexemeType type = current.type;
            current = lexer.getLexeme();
            switch (type){
                case MULT: temp*= ParseFactor();
                case DIV: temp/= ParseFactor();
                default: throw new Exception();
            }
        }
        return temp;
    }
    public double ParseFactor() throws Exception{
        double temp = ParsePower();
        if (current.type == LexemeType.POW) {
            current = lexer.getLexeme();

            return Math.pow(temp,ParseFactor());
        }
        return temp;
    }
    public double ParsePower() throws Exception{
        if(current.type == LexemeType.MINUS)   {
            current = lexer.getLexeme();
            return -ParseAtom();
        }
        else
            return ParseAtom();
    }
    public double ParseAtom() throws Exception{
        double temp;
        switch (current.type){
            case LEFTB:
                current = lexer.getLexeme();
                temp = ParseExpr();
                if(current.type != LexemeType.RIGHTB){
                    throw new Exception();
                }
                current = lexer.getLexeme();
                return temp;
            case NUMBER:
                temp = Double.parseDouble(current.lexem);
                current = lexer.getLexeme();
                return temp;
            default: throw new Exception();
        }
    }


    public static void Main(String[] args){
        String expression = "15*3-10+6";
        Parser parser = new Parser(expression);
        try {
            System.out.println(parser.Parse());
        }
        catch (Exception e){
            System.out.println("DEATH");
        }
    }
}
