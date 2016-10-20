package haricots;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 20/10/13
 * Time: 10:25
 * To change this template use File | Settings | File Templates.
 */
public enum Variete {
    HARRY_CORIACE(new int[]{2,3}, new int[]{2,3}),
    HARRY_COCHET(new int[]{4,7,9,11}, new int[]{1,2,3,4}),
    HARRY_COLT(new int[]{4,6,8,10}, new int[]{1,2,3,4}),
    HARRY_CAUCHEMAR(new int[]{3,6,8,9}, new int[]{1,2,3,4}),
    HARRY_COCHON(new int[]{3,5,7,8}, new int[]{1,2,3,4}),
    HARRY_COLIQUE(new int[]{3,5,6,7}, new int[]{1,2,3,4}),
    HARRY_COPAIN(new int[]{2,4,6,7}, new int[]{1,2,3,4}),
    HARRY_CHAOS(new int[]{2,4,5,6}, new int[]{1,2,3,4}),
    HARRY_CHOCO(new int[]{2,3,4}, new int[]{2,3,4}),
    HARRY_COGNE(new int[]{4,7,10,12}, new int[]{1,2,3,4}),
    HARRY_COMIQUE(new int[]{2,3,4,5}, new int[]{1,2,3,4});

    private int[] haricometre;
    private int[] thunes;

    private Variete(int[] haricometre, int[] thunes) {
        this.haricometre = haricometre;
        this.thunes = thunes;
    }

    public int[] getHaricometre() {
        return haricometre;
    }

    public int getThunes(int i) {
        return thunes[i];
    }
}
