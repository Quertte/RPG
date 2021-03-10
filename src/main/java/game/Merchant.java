package game;

public class Merchant implements Seller {


    @Override
    public int sell(Goods goods) {
        int result = 0;
        if (goods == Goods.POTION) {
            result = 20;
        }
        return result;
    }

    public enum Goods {
        POTION
    }
}
