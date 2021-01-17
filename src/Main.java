
public class Main implements Runnable {
    int size =60;//40 60 80

    Gui gui=new Gui(10,10,2,size);

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
