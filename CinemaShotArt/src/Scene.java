
public class Scene {
    String genre;
    String mood;
    String description;

    public Scene(String genre, String mood, String description) {
        this.genre = genre;
        this.mood = mood;
        this.description = description;

    }
    public String getGenre() {
        return genre;

    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


}
