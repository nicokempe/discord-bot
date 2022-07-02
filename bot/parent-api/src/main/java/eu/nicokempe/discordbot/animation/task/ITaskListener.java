package eu.nicokempe.discordbot.animation.task;

public interface ITaskListener<T> {

    ITaskListener FIRE_EXCEPTION_ON_FAILURE = new ITaskListener() {
        @Override
        public void onFailure(ITask task, Throwable th) {
            th.printStackTrace();
        }
    };

    default void onComplete(ITask<T> task, T t) {
    }

    default void onCancelled(ITask<T> task) {
    }

    default void onFailure(ITask<T> task, Throwable th) {
    }
}