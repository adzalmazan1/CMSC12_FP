public class Boss extends Entity implements Deployable {
    protected int healthWidth;
    protected int healthHeight;

    protected int deployCounter = 0;
    protected int deployChange = 420;

    // getters and setters here
    public int getHealth() {
        return healthWidth;
    }

    public void setHealth() {
        healthWidth -= 50;
        // change to 0.5 or 1
    }

    // override this method!
    public void addSpawn() {
        // override this method
        System.out.println("Boss spawn code here");
    }

    @Override
    public void setDefaultValues() {
    }

    @Override
    public void loadImage() {
    }
}
