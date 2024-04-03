public class Multithreading_Main {
    
    public static void main(String[] args) {
        Multithreading t1 = new Multithreading(1);
        Multithreading t2 = new Multithreading(2);
        Multithreading t3 = new Multithreading(3);

        t1.start();
        t2.start();
        t3.start();
    }
}
