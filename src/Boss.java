import java.awt.image.BufferedImage;

public class Boss extends Entity implements Deployable, Runnable {
    protected int healthWidth;
    protected int healthHeight;

    protected Thread spawnThread;
    protected boolean spawnStarted = false; // def values
    protected boolean threadRunning = true;

    protected BufferedImage img;
    protected boolean isSpawning;
    protected BufferedImage spawnImg;

    // getters and setters here
    public int getHealth() {
        return healthWidth;
    }

    public void setHealth() {
        healthWidth -= 5;
        // change to 0.5 or 1
    }

    // methods for Deployable
    @Override
    public void setDefaultValues() {
    }

    @Override
    public void loadImage() {
    }

    // method for Runnable
    @Override
    public void run() {
        while (threadRunning) {
            addSpawn();
            try {
                Thread.sleep(1000);
                setIsSpawning(false);
                Thread.sleep(5000);         // Then wait before spawning again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // override this method!
    public void addSpawn() {
        // override this method
        System.out.println("Override this addSpawn method");
    }

    public void setIsSpawning(boolean isSpawning) {
        this.isSpawning = isSpawning;
    }

    public void stopSpawnThread() {
        threadRunning = false;
        if(spawnThread != null) {
            spawnThread.interrupt(); // best practices null checking
        }
        spawnStarted = false;
        spawnThread = null;
    }
}
