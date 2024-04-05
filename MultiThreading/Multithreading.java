public class Multithreading extends Thread {
    
    private int threadNumber;
    public Multithreading(int x){
            this.threadNumber = x;
    }
    @Override
    public void run() {

        for(int i=0;i<=5;i++){
            System.out.println(i + " from thread " + threadNumber);

            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                System.out.println(e);
            }
        }
    }
}
