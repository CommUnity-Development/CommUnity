package Notification;

/**
 * A class to store Notification data, not in use a lot right now since not much of the sending notification is working
 * will be fully implemented later
 */
public class Data {
    private String user;
    private int icon;
    private String body;
    private String title;
    private String sented;

    /**
     * Constructor with the necessary data
     * @param user the user id sending the notification
     * @param icon the icon of the notification
     * @param body the text of the notification
     * @param title the title of the notification
     * @param sented the user id of the user being sent the notification
     */
    public Data(String user, int icon, String body, String title, String sented) {
        this.user = user;
        this.icon = icon;
        this.body = body;
        this.title = title;
        this.sented = sented;
    }

    /**
     * default constructor
     */
    public Data() {
        user = "";
        icon = 0;
        body = "";
        title = "";
        sented = "";

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSented() {
        return sented;
    }

    public void setSented(String sented) {
        this.sented = sented;
    }
}
