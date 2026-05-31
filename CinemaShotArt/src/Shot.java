
public class Shot {
    String shotType;
    String lighting;
    String movement;
    int duration;

    public Shot(String shotType, String lighting, String movement, int duration) {
        this.shotType = shotType;
        this.lighting = lighting;
        this.movement = movement;
        this.duration = duration;

    }
    public String getShotType() {
        return shotType;

    }
    public String getLighting() {
        return lighting;

    }
    public String getMovement() {
        return movement;
    }
    public int getDuration() {
        return duration;
    }
    public void setShotType(String shotType) {
        this.shotType = shotType;

    }
    public void setLighting(String lighting) {
        this.lighting = lighting;
    }
    public void setMovement(String movement) {
        this.movement = movement;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

}
