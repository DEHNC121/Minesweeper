
public class Main implements Runnable {

    Gui gui=new Gui(10,7,3,35);

    public static void main(String[] args){
        new Thread(new Main()).start();

    }

    @Override
    public void run() {
        while (true){
            gui.repaint();

        }

    }
}
