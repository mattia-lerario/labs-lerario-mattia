public class Main
{
    public static void main(String[] args)
    {
        int n = 8; // Number of threads
        for (int i=0; i<n; i++)
        {
            DemoThread object = new DemoThread();
            object.start();
        }
    }
}