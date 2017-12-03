package Domain;

import Net.ServerWS;
import com.google.gson.Gson;
import java.util.HashMap;


public class GlobalUpdate extends Thread {
    Character character;
    Gson gson;
    public GlobalUpdate() {
        this.start();
        character = new Character();
        gson = new Gson();
    }

    @Override
    public void run() {
     while (true) {
         try {
             Thread.sleep(1000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

         for (HashMap.Entry<String, Character> pair : ServerWS.getCharacterMap().entrySet()) {
             character = ServerWS.getCharacterMap().get(pair.getKey() );
             if (character.getHP()< character.getMaxHP() && !character.isInBattle())
             character.setHP(character.getHP() + 1);
             ServerWS.getCharacterMap().get(character.getName()).setHP(character.getHP());
             if (character.getName()!= "Bot")
             if (ServerWS.getSessionMap().get(pair.getKey()).isOpen() ) {
                 // Проверяем, конекшен
                 ServerWS.getSessionMap().get(character.getName()).getAsyncRemote().sendText("login#1#" + gson.toJson(ServerWS.getCharacterMap().get(character.getName())));

             } else {
                 //Удаляем сесию, если она закрыта

             }
         }

     }
    }
}
