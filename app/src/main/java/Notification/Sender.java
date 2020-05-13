package Notification;

/**
 * A class to send data, temporarily stores necessary data
 */
public class Sender {
    public Data data;
    public String to;

    /**
     * a constructor for the data
     * @param data
     * @param to
     */
    public Sender(Data data, String to) {
        this.data = data;
        this.to = to;
    }
}
