package Domain;

import db.Users;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "character")
public class Character {
    @Transient
    private Users user;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "strength")
    private int strength;
    @Column(name = "agility")
    private int agility;
    @Column(name = "intuition")
    private int intuition;
    @Column(name = "vitality")
    private int vitality;
    @Column(name = "intelligence")
    private int intelligence;
    @Column(name = "wisdom")
    private int wisdom;
    @Column(name = "armor")
    private int armor;
    @Column(name = "maxHP")
    private int maxHP;
    @Transient
    private int HP;
    @Column(name = "login")
    private String login;
    @Column(name = "lvl")
    private int lvl;
    @Column(name = "experience")
    private int experience;

    public Character(String name, String login) {
        this.name = name;
        this.login = login;
    }

    public Character() {
    }

    public void hit(Character enemy){
        int perReductionPhyDamage = (100-(enemy.getArmor()/enemy.getLvl()))/100;
        enemy.setHP(enemy.getHP()-(this.strength*perReductionPhyDamage));
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getIntuition() {
        return intuition;
    }

    public void setIntuition(int intuition) {
        this.intuition = intuition;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
