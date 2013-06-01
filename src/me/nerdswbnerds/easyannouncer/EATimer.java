package me.nerdswbnerds.easyannouncer;

import java.util.ArrayList;
import java.util.Random;

public class EATimer implements Runnable{
    int previous = -1;
    ArrayList<Integer> used = new ArrayList<Integer>();
    int ticks = 0;

    public int id = -1;

    public void run(){
        ticks++;

        if(used.size() >= EAManager.messages.size()){
            used.clear();
        }

        if(ticks >= EAManager.interval){
            int nextID = previous + 1;

            if(nextID >= EAManager.messages.size()){
                nextID = 0;
            }

            if(EAManager.mode == EAMode.RANDOM){
                nextID = new Random().nextInt(EAManager.messages.size());
            }else if(EAManager.mode == EAMode.ORGANIZED){
                while(true){
                    nextID = new Random().nextInt(EAManager.messages.size());

                    if(!used.contains(nextID)){
                        used.add(nextID);
                        break;
                    }
                }
            }

            EAManager.announce(nextID);

            ticks = 0;

            previous = nextID;
        }
    }
}
