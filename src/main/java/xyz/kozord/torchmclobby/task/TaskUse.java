package xyz.kozord.torchmclobby.task;

public abstract class TaskUse implements Runnable {

    public void run() {
        this.runTask();
    }

    public abstract void runTask();
}
