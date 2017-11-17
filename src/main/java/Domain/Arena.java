package Domain;

import Net.ServerWS;
import com.google.gson.Gson;
import Domain.Character;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.*;


public class Arena extends Thread {
    HashMap<Integer,PriorityQueue<Domain.Character>> charQueue;
    HashMap<Long,Battle> battleList;
    Gson gson ;


    public Arena() {
        gson = new Gson();
        this.charQueue = new HashMap<Integer, PriorityQueue<Character>>();
        this.battleList = new HashMap<Long, Battle>();
        this.gson = new Gson();
        for (int i = 1; i <= 20 ; i++) {
            this.charQueue.put(i, new PriorityQueue<Character>());
        }
        this.start();
        System.out.println("------------Арена создана------------");
    }


    public void addToArena(Character character) {

        for (HashMap.Entry<Integer, PriorityQueue<Character>>  pair: charQueue.entrySet()) {
            if(pair.getKey() == character.getLvl()){
                pair.getValue().offer(character);
            }
          //  System.out.println(pair);
        }

    }


    @Override
    public void run() {
        System.out.println("------------Запущенно создание батлов------------");
             while (true){
                 createBattle();
             }
    }


    public void createBattle() {


        for (HashMap.Entry<Integer, PriorityQueue<Character>>  pair: charQueue.entrySet()) {

           if (pair.getValue().size()>1){
               System.out.println("----------Создан БАТЛ-------------");
               long number = new Date().getTime();
               Character player1 = charQueue.get(1).poll();
               Character player2 = charQueue.get(1).poll();
               final Battle battle = new Battle(number,player1,player2);
               battleList.put(number, battle);

               Thread thread = new Thread(){
                   public void run(){
                       System.out.println("Thread Running " + battle.getNumber());
                       battle.startfight();
                   }
               };

               thread.start();

               player1.sendMessage(gson.toJson(battle));
               player2.sendMessage(gson.toJson(battle));
           }

            }

    }






    public HashMap<Integer, PriorityQueue<Character>> getCharQueue() {
        return charQueue;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public void setCharQueue(HashMap<Integer, PriorityQueue<Character>> charQueue) {
        this.charQueue = charQueue;
    }

    public HashMap<Long, Battle> getBattleList() {
        return battleList;
    }

    public void setBattleList(HashMap<Long, Battle> battleList) {
        this.battleList = battleList;
    }
}
