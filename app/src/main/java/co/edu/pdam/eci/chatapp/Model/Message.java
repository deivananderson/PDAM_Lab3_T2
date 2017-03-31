package co.edu.pdam.eci.chatapp.Model;

/**
 * Created by estudiante on 3/30/17.
 */

public class Message {

    private String message;
    private String user;
    private String imageUrl;

    public Message(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public  Message(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (!message.equals(message1.message)) return false;
        return user.equals(message1.user);

    }

    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    public String getImageUrl() {

        return imageUrl;
    }
}
