public class Main {
    public static void main(String[] args) {
        long start= System.currentTimeMillis();

        Sys s = new Sys();
        s.repeatRun(3);
        s.close();


        System.out.println(System.currentTimeMillis()-start);
    }
}
