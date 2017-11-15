package Domain;

import Net.ServerWS;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Arena extends Thread {
    HashMap<Integer,PriorityQueue<Character>> charQueue;
    HashMap<Long,Battle> battleList;
    Gson gson;


    public Arena() {
        this.charQueue = new HashMap<Integer, PriorityQueue<Character>>();
        this.battleList = new HashMap<Long, Battle>();
        this.gson = new Gson();
        for (int i = 1; i <= 20 ; i++) {
            this.charQueue.put(i, new PriorityQueue<Character>());
        }
        this.start();
        System.out.println("Создана арена");
    }


    public void addToArena(Character character) {

        for (HashMap.Entry<Integer, PriorityQueue<Character>>  pair: charQueue.entrySet()) {
            System.out.println(pair);
            System.out.println(pair.getKey() == character.getLvl());
            if(pair.getKey() == character.getLvl()){
                pair.getValue().offer(character);
            }
            System.out.println(pair);
        }
        System.out.println(charQueue.get(3).poll().getName());
        System.out.println(charQueue.get(3).size());
    }


    @Override
    public void run() {
             while (true){
                 createBattle();
             }
    }


    public void createBattle() {
           if (charQueue.get(1).size()>1){
               long number = new Date().getTime();
               Character player1 = charQueue.get(1).poll();
               Character player2 = charQueue.get(1).poll();
               Battle battle = new Battle(number,player1,player2);
               battleList.put(number, battle);
               battle.start();
               player1.sendMessage(gson.toJson(battle));
               player2.sendMessage(gson.toJson(battle));
           }
    }



}
