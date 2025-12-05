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
            case '!': addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG); break;
            case '=': addToken(match('=') ? TokenType.EQUAL_EQUAL: TokenType.EQUAL); break;
            case '<': addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS); break;
            case '>': addToken(match('=') ? TokenType.GREATER_EQUAL: TokenType.GREATER); break;
            case '/':
                      if (match('/')) {
                          while (peek() != '\n' && !isAtEnd()) advance();
                      } else {
                          addToken(TokenType.SLASH);
                      }
            case ' ':
            case '\r': // Ignore whitespace.
            case '\t': break;

            case '"': string(); break;

            case '\n':
                line++; // increment the line when we hit a newline
                break;

            default:
                if (isDigit(c))
                    number();
                else {
                    Lox.error(line, "Unexpected character.");
                    break;
                }
        }
    }

    private void number() {
        while (isDigit(peek())) advance();

        // look for fractional part
        if(peek() == '.' && isDigit(peekNext())){
            advance(); // consume the '.'
            while(isDigit(peek())) advance(); // get the nums after
        }
        addToken(TokenType.NUMBER,
                Double.parseDouble(source.substring(start,current))); // doubles are like floats
    }

    private char peekNext() {
        if (current+1>= source.length()) return '\0';
        return source.charAt(current+1); // remember current is always at the "next" character
    }


    private void string() {
        /* while we don't hit the end of the
        file nor the end of the string, keep advancing.
        also, if we hit the end of a line, we have to increment the line count.*/

        /* Oh, also, because there's no breaking the string when the line ends,
        we support multi line strings. */

        while (peek()!= '"' && !isAtEnd()){
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated string.");
            return;
        }
        // grab the closing '"'.
        advance();
        // Trim the quotes that surround the string
        String value = source.substring(start+1, current-1);
        addToken(TokenType.STRING,value);
    }
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;
        current++; // this is basically doing the same thing as the advance method
        return true;
        // this was described in the book as a 'conditional advance'
    }

    private char peek() {
        if (isAtEnd()) return '\0'; // empty if at end
        return source.charAt(current); // check next char without advancing
    }

    private boolean isDigit(char c) {
        return c>= '0' && c<='9'; // uses some string value system idek
    }

    private char advance(){
        return source.charAt(current++); // get char and increment current
    }

    private void addToken(TokenType type) {
        // this will be shorthand for the following method
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
