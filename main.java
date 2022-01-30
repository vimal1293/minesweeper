public class main implements Runnable{
    gui moi = new gui();
    public static void main(String[] args){
        new Thread(new main()).start();
    }
    @Override
    public void run(){
        while(true){
            moi.repaint();
            if (moi.resetter == false){
                moi.checkVictoryStatus();
               // System.out.println("VICTORY: " + moi.victory + " DEFEAT: " +moi.defeat );
            }
        }

    }
}
