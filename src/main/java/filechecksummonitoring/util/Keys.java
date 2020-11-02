package filechecksummonitoring.util;

import java.math.BigInteger;

public class Keys {

    private BigInteger firstKey;
    private BigInteger secondKey;

    public Keys() {

    }

    public Keys(BigInteger firstKey, BigInteger secondKey) {
        this.firstKey = firstKey;
        this.secondKey = secondKey;
    }

    public BigInteger getFirstKey() {
        return firstKey;
    }

    public BigInteger getSecondKey() {
        return secondKey;
    }

    public void setFirstKey(BigInteger firstKey) {
        this.firstKey = firstKey;
    }

    public void setSecondKey(BigInteger secondKey) {
        this.secondKey = secondKey;
    }
}
