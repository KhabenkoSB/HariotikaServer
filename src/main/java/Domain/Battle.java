package Domain;


import com.google.gson.Gson;

import java.io.Serializable;

public class Battle {

    long number;
    boolean finished = false;
    String log;
    private Character player1;
    private Character player2;

    private boolean player1IsReady;
    private boolean player2IsReady;

    private PartOfBody player1Def;
    private PartOfBody player2Def;

    private PartOfBody player1Hit;
    private PartOfBody player2Hit;


    public Battle(long number,Character player1, Character player2) {
        this.number = number;
        this.player1 = player1;
        this.player2 = player2;
        this.player1IsReady = false;
        this.player2IsReady =false;
    }


    public void fight(){
        Gson gson = new Gson();
        System.out.println("Файт");
        if (!getPlayer1Hit().equals(getPlayer2Def()) && getPlayer1Hit()!= null)
        {
            player1.hit(player2);
            log+=player1.getName()+" hitting "+player2.getName()+" to "+ player1Hit;
            System.out.println("HP Игрока 1 "+player1.getHP());
        }
        if (!getPlayer2Hit().equals(getPlayer1Def())&& getPlayer2Hit()!= null)
        {
            player2.hit(player1);
            log+=player2.getName()+" hitting "+player1.getName()+" to "+ player2Hit;
            System.out.println("HP Игрока 2 "+player2.getHP());
        }

        Battle battle =this;
        if (isFinish())
            finished =true;

        if (player1.getName()!="Bot")
        player1.sendMessage("Battle#"+gson.toJson(battle));
        if (player2.getName()!="Bot")
        player2.sendMessage("Battle#"+gson.toJson(battle));

        player1IsReady =false;
        player2IsReady =false;

    }



    public boolean isRedy(){
          return this.player1IsReady && this.player2IsReady;
    }

    public boolean isFinish(){
        if (player1.getHP()<=0 || player2.getHP()<=0)
            return true;
        else return false;
    }


    public void startfight() {
        System.out.println("Бой начался");
        while (!isFinish())
        {
            fightWithBot();
            try {
                Thread.sleep(1000);
                System.out.print("");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isRedy()){
                fight();

               if (isFinish())
                    break;
            }
         //   setPlayer1IsReady(true);
        //    setPlayer2IsReady(true);



        }

        player1.setInBattle(false);
        player2.setInBattle(false);
        System.out.println(player1.isInBattle());
        System.out.println(player2.isInBattle());
        System.out.println("Бой закончен");
        finished =true;



    }

    public boolean isPlayer1IsReady() {
        return player1IsReady;
    }

    public void setPlayer1IsReady(boolean player1IsReady) {
        this.player1IsReady = player1IsReady;
    }

    public boolean isPlayer2IsReady() {
        return player2IsReady;
    }

    public void setPlayer2IsReady(boolean player2IsReady) {
        this.player2IsReady = player2IsReady;
    }

    public PartOfBody getPlayer1Def() {
        return player1Def;
    }

    public void setPlayer1Def(PartOfBody player1Def) {
        this.player1Def = player1Def;
    }

    public PartOfBody getPlayer2Def() {
        return player2Def;
    }

    public void setPlayer2Def(PartOfBody player2Def) {
        this.player2Def = player2Def;
    }

    public Character getPlayer1() {
        return player1;
    }

    public void setPlayer1(Character player1) {
        this.player1 = player1;
    }

    public Character getPlayer2() {
        return player2;
    }

    public void setPlayer2(Character player2) {
        this.player2 = player2;
    }

    public PartOfBody getPlayer1Hit() {
        return player1Hit;
    }

    public void setPlayer1Hit(PartOfBody player1Hit) {
        this.player1Hit = player1Hit;
    }

    public PartOfBody getPlayer2Hit() {
        return player2Hit;
    }

    public void setPlayer2Hit(PartOfBody player2Hit) {
        this.player2Hit = player2Hit;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void fightWithBot(){
         if (player1.getName().equals("Bot")) {
             player1IsReady = true;
             player1Hit = PartOfBody.HEAD;
             player1Def = PartOfBody.BODY;
         } else if (player2.getName().equals("Bot")){
             player2IsReady = true;
             player2Hit = PartOfBody.HEAD;
             player2Def =PartOfBody.BODY;

         }



    }
}
