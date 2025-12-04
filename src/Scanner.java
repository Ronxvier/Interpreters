import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private final String source; // source code stored in string
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0; // points to the first char in the lexeme
    private int current = 0; // points to the char currently considered
    private int line = 1; // tracks what source line current is on
    Scanner(String source) { // scanner constructor
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            // This loop triggers for each lexeme
            start = current;
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, "", null, line));
        /* this is for when we reach the end of the file,
        Notating this because for some reason it wasn't obvious to me when I read it.*/
        return tokens;
    }

    private void scanToken(){
        char c = advance();
        switch (c) {
            case '(': addToken(TokenType.LEFT_PAREN); break;
            case ')': addToken(TokenType.RIGHT_PAREN); break;
            case '{': addToken(TokenType.LEFT_BRACE); break;
            case '}': addToken(TokenType.RIGHT_BRACE); break;
            case ',': addToken(TokenType.COMMA); break;
            case '.': addToken(TokenType.DOT); break;
            case '-': addToken(TokenType.MINUS); break;
            case '+': addToken(TokenType.PLUS); break;
            case ';': addToken(TokenType.SEMICOLON); break;
            case '*': addToken(TokenType.STAR); break;
            // dw that backslash '/' isn't here.
        }
    }
    private char advance(){
        return source.charAt(current++); // get char and increment current
    }

    private void addToken(TokenType type) { // this will be shorthand for the following method
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal){
        /* this will be the longhand for addToken, that uses input for type and object,
        but the scanner variables for line number, and a substring of the lexeme using start and current as
        indices for text.*/
        String text = source.substring(start,current);
        tokens.add(new Token(type, text, literal, line));
    }
    private boolean isAtEnd() {
        return current >= source.length();
    }

}
