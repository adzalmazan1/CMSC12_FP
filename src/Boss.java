public class Boss extends Entity {
    protected int healthWidth;
    protected int healthHeight;

    // getters and setters here
    public int getHealth() {
        return healthWidth;
    }

    public void setHealth() {
        healthWidth -= 50;
    }
}
