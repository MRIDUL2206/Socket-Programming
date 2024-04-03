public class Multithreading_Main {
    
    public static void main(String[] args) {
        Multithreading t1 = new Multithreading(1);
        Multithreading t2 = new Multithreading(2);
        Multithreading t3 = new Multithreading(3);

        t1.start();
        t2.start();
        t3.start();

        // here even if one of the threads or the main file gives a runtime error , th other threads still continue to execute untill completion
    }
}
