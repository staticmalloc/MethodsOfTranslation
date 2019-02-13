
import java.util.Scanner;

public class Lexer {
    private char current;
    private boolean isLastNumber;
    private Scanner scanner;

    public Lexer(Scanner scanner){
        this.scanner = scanner;
        current = '0';
        isLastNumber = false;
    }

    public Lexeme getLexeme() throws Exception{
        if(!isLastNumber){
            current = scanner.next().charAt(0);
        }
        isLastNumber = false;
        if(!scanner.hasNext()) {
            return new Lexeme("EOF", LexemeType.EOF);
        }
        switch (current){
            case '+': return new Lexeme("+",LexemeType.PLUS);
            case '-': return new Lexeme("+",LexemeType.MINUS);
            case '*': return new Lexeme("+",LexemeType.MULT);
            case '/': return new Lexeme("+",LexemeType.DIV);
            case '^': return new Lexeme("+",LexemeType.POW);
            case '(': return new Lexeme("+",LexemeType.LEFTB);
            case ')': return new Lexeme("+",LexemeType.RIGHTB);
            case '0': return new Lexeme(this.readNumber(),LexemeType.NUMBER);
            case '1': return new Lexeme(this.readNumber(),LexemeType.NUMBER);
            case '2': return new Lexeme(this.readNumber(),LexemeType.NUMBER);
            case '3': return new Lexeme(this.readNumber(),LexemeType.NUMBER);
            case '4': return new Lexeme(this.readNumber(),LexemeType.NUMBER);
            case '5': return new Lexeme(this.readNumber(),LexemeType.NUMBER);
            case '6': return new Lexeme(this.readNumber(),LexemeType.NUMBER);
            case '7': return new Lexeme(this.readNumber(),LexemeType.NUMBER);
            case '8': return new Lexeme(this.readNumber(),LexemeType.NUMBER);
            case '9': return new Lexeme(this.readNumber(),LexemeType.NUMBER);

            default: throw new Exception();
        }
    }
    private String readNumber(){
        StringBuilder builder = new StringBuilder();
        while (current>='0' && current<='9'){
            builder.append(current);
            if(!isLastNumber){
                current = scanner.next().charAt(0);
            }
        }
        isLastNumber = true;
        return  builder.toString();
    }
}
