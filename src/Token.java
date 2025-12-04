public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;
    Token(TokenType type, String lexeme, Object literal, int line){
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        return type + " " + lexeme + " " + literal;
        /* I clearly don't know enough about java because I just found out you can provide instructions on how
        to convert objects into readable strings by giving the class a toString() method.*/
    }
}
