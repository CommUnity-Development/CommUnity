package Notification;

/**
 * a class that helps deal with Tokens, stores them when necessary so they are easy to access
 */
public class Token {
    private String token;

    /**
     * Constructor with appropriate parameters
     * @param token the token to be stored
     */
    public Token(String token) {
        this.token = token;
    }

    /**
     * default constructor
     */
    public Token() {
        token = "";
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
