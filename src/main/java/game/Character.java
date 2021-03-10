package game;

import java.util.Random;

public abstract class Character implements Fighter {
    //ник-нейм
    private String name;
    //Характеристики персонажа
    private int level;

    private int hp;

    private int strength;
    private int agility;
    // Золото и опыт

    private int xp;
    private int gold;

    private static final int FULL_HP = 100;

    public Character(String name, int hp, int strength, int agility, int xp, int gold) {
        this.name = name;
        this.hp = hp;
        this.strength = strength;
        this.xp = xp;
        this.gold = gold;
        this.level = 1;
    }

    @Override
    public int attack() {
        if (agility * 3 > random()) {
            //Шанс критического удара 20%
            if ((new Random().nextInt(4) + 1) == 1) {
                System.out.println("Критический удар!!!");
                return strength * 2;
            }
            return strength;
        }
        return 0;
    }

    void levelUp(int xp) {
        if (xp >= 200) {
            ++level;
            this.hp = FULL_HP + 20;
            this.agility += 20;
            System.out.println("Ваш уровень поднят! Ваш уровень: " + level + "ваше здоровь: " + hp + "ваша ловкость:" + agility);
        }
    }

    public int getLevel() {
        return level;
    }

    private int random() {
        return (int) (Math.random() * 100);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String toString() {
        return String.format("%s здоровье: %d, опыт: %d", name, hp, xp);
    }
}
