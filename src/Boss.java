public class Boss extends Entity implements Deployable, Runnable {
    protected int healthWidth;
    protected int healthHeight;

    protected int deployCounter = 0;
    protected int deployChange = 300;

    protected Thread spawnThread;
    protected boolean spawnStarted = false; // def values
    protected boolean threadRunning = true;

    // getters and setters here
    public int getHealth() {
        return healthWidth;
    }

    public void setHealth() {
        healthWidth -= 50;
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
        while(threadRunning) {
            addSpawn();
            try {
                Thread.sleep(2000);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // override this method!
    public void addSpawn() {
        // override this method
        System.out.println("Override this addSpawn method");
    }

    public void stopSpawnThread() {
        threadRunning = false;
        if(spawnThread != null) {
            spawnThread.interrupt(); // best practices null checking
        }
        spawnStarted = false;
    }
}
