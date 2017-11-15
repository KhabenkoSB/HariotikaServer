package Domain;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Arena {
    HashMap<Integer,PriorityQueue<Character>> charQueue;
    HashMap<Integer,Battle> battleList;


    public Arena() {
        this.charQueue = new HashMap<Integer, PriorityQueue<Character>>();
        this.battleList = new HashMap<Integer, Battle>();

        for (int i = 1; i <= 20 ; i++) {
            this.charQueue.put(i, new PriorityQueue<Character>());
        }
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

    public void createBattle() {


    }


}
