import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        System.out.println("Hello, " + args[0]);

        thread(args);
        //there are exeption handlers for threads
//        exeptionHandler(args);
        createThreadExtends();
        createThreadRunnable();

        //public void run in Thread cntrl
        //we can implemetn Runnable to some other class and override run method


        //thread interseptions
        //yeeld() - notify sheduler that thread can have less time to run
        //sleep() - Ask Thread Sheduler of os to sleep some thread
        sleepThread();

        //interupt() - notify Thread that he needs to stop working
        interuptThread();

        //isInterrupted flag - way to implement loop in run method
        isInterruptedFlagThingie();

        //Join - waiting for other thread to finish its work
        waitingForOtherThread();

        /**
         * Monitors - for communication between threads
         * Each object have monitor that thread can lock or unlock
         */

        //simpliets way to lock object is by synchronized keyword
        Object object = new Object();
        synchronized(object) {
            System.out.println("Hello World");
        }

        //as example
        syncLockThread();

        //another synchroniaed usage -sync whole block
        //then lock will be THIS object
        // if method static - lock will be objects of class by   .class

        //wait() - usesd on object, on monitor of which we want to complete wait

        waitNotifyByMonitor();

        //isAlive - true for every thread state except TERMINATED

        //@Volatile
        /**
         * 1) Variable does not stored in cache so no thread will work with
         * cached value of variable
         * 2) Variable read|write operations will be atomic
         */
        //@Transient
        /**
         * varable not serializible
         */

        while (true) {
            //
        }
    }

    // Thread - API for low-level JVM threads
    // There are two types of threads
    /**
     * Daemons and non-daemon
     * Daemons threads - are service threads for backgroung work
     * JVM runs until
     * Runtime.exit or all non-daemons thread end their work
     */

    //each thread enters some Thread group

    public static void thread(String []args){
        Thread currentThread = Thread.currentThread();
        ThreadGroup threadGroup = currentThread.getThreadGroup();
        System.out.println("Thread: " + currentThread.getName());
        System.out.println("Thread Group: " + threadGroup.getName());
        System.out.println("Parent Group: " + threadGroup.getParent().getName());
    }

    public static void exeptionHandler(String []args) {
        Thread th = Thread.currentThread();
        th.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Возникла ошибка: " + e.getMessage());
            }
        });
        System.out.println(2/0);
    }

    /**
     * There are Two ways to create threads
     * Extends from Thread class and override run method
     *
     */


    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello, World!");
        }
    }

    public static void createThreadExtends() {
        Thread thread = new MyThread();
        thread.start();
    }

    public static void createThreadRunnable() {
        Runnable task = new Runnable() {
            public void run() {
                System.out.println("Hello, Runnable");
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public static void sleepThread() {
        Runnable task = () -> {
            try {
                int secToWait = 1000 * 5;
                Thread.currentThread().sleep(secToWait);
                System.out.println("Waked up, Samurai");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public static void interuptThread() {
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        thread.interrupt();
    }

    public static void isInterruptedFlagThingie() {
        Runnable task = () -> {
            while(!Thread.currentThread().isInterrupted()) {
                //Do some work

            }
            System.out.println("Finished");
        };
        Thread thread = new Thread(task);
        thread.start();
        thread.interrupt();
    }

    public static void waitingForOtherThread() throws InterruptedException {
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Finished Sleeping");
            } catch (InterruptedException e) {
                System.out.println("Interrupted Join");
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        thread.join();
        System.out.println("Finished Join");
    }

    public static void syncLockThread() throws InterruptedException {
        Object lock = new Object();

        Runnable task = new Runnable() {
            public void run() {
                try {
                    Thread.currentThread().sleep(1000);
                }
                catch (InterruptedException ie) {

                }
                synchronized (lock) {
                    System.out.println("thread");
                }
            }
        };


        Thread th1 = new Thread(task);
        System.out.println(th1.getState());
        th1.start();
        System.out.println(th1.getState());
        synchronized (lock) {
            for (int i = 0; i < 8; i++) {
                Thread.currentThread().sleep(1000);
                System.out.print("  " + i);
            }
            System.out.println(th1.getState());
            System.out.println(" ...");
        }
    }

    public static void waitNotifyByMonitor() throws InterruptedException {
        Object lock = new Object();
        // task будет ждать, пока его не оповестят через lock
        Runnable task = () -> {
            synchronized(lock) {
                try {
                    lock.wait();
                } catch(InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
            // После оповещения нас мы будем ждать, пока сможем взять лок
            System.out.println("thread");
        };
        Thread taskThread = new Thread(task);
        taskThread.start();
        // Ждём и после этого забираем себе лок, оповещаем и отдаём лок
        Thread.currentThread().sleep(3000);
        System.out.println("main");
        synchronized(lock) {
            lock.notify();
        }
    }

}
