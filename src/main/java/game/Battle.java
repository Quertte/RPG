package game;

public class Battle {
    // Метод, который вызывается при начале боя, сюда мы передаем ссылки на нашего героя и монстра.
    public void fight(Character hero, Character monster, Realm.FightCallback fightCallback) {
        //Ходы будут идти в отдельном потоке
        //Сюда запишем какой ход по счету
        //Когда бой будет закончен мы
        //Воины бьют по очереди, описываем логику смены сторон
        //Чтобы бой не проходил за секунду, сделаем имитацию работы, как если бы
        //у нас была анимация
        Runnable runnable = () -> {
            //Сюда запишем какой ход по счету
            int turn = 1;
            //Когда бой будет закончен мы
            boolean isFightEnded = false;
            while (!isFightEnded) {
                System.out.println("Ход + " + turn);
                //Воины бьют по очереди, описываем логику смены сторон
                if (turn++ % 2 != 0) {
                    isFightEnded = makeHit(monster, hero, fightCallback);
                } else {
                    isFightEnded = makeHit(hero, monster, fightCallback);
                }
                try {
                    //Чтобы бой не проходил за секунду, сделаем имитацию работы, как если бы
                    //у нас была анимация
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread fight = new Thread(runnable);
        fight.start();
    }

    //Метод для совершения удара
    private Boolean makeHit(Character def, Character attack, Realm.FightCallback fightCallback) {
        //Получаем силу удара
        int hit = attack.attack();
        //Отнимаем количество урона из здоровья защищающегося
        int defHealth = def.getHp() - hit;
        //Если атака прошла, выводим в консоль сообщение об этом
        if (hit != 0) {
            System.out.printf("%s Нанес удар в %d единиц", attack.getName(), hit);
            System.out.printf("У %s осталось %d единиц здоровья...", def.getName(), defHealth);
        } else {
            //Если атакующий промахнулся (то есть урон не 0), выводим это сообщение
            System.out.printf("%s промахнулся!%n", attack.getName());
        }
        if (defHealth <= 0 && def instanceof Hero) {
            //Если здоровье меньше 0 и если защищающейся был героем, то игра заканчивается
            System.out.println("Вы пали в бою!");
            //Вызываем коллбэк, что мы проиграли
            fightCallback.fightLost();
            return true;
        } else if (defHealth <= 0) {
            //Если здоровья больше нет и защищающийся – это монстр, то мы забираем от монстра его опыт и золото
            System.out.printf("Враг повержен! Вы получаете %d опыта и %d золота", def.getXp(), def.getGold());
            attack.setXp(attack.getXp() + def.getXp());
            attack.levelUp(attack.getXp());
            attack.setGold(attack.getGold() + def.getGold());
            //вызываем коллбэк, что мы победили
            fightCallback.fightWin();
            return true;
        } else {
            //если защищающийся не повержен, то мы устанавливаем ему новый уровень здоровья
            def.setHp(defHealth);
            return false;
        }
    }
}
