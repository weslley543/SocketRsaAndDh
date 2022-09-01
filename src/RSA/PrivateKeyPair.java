package RSA;

import java.math.BigInteger;

public class PrivateKeyPair {
    private BigInteger n;
    private BigInteger d;

    public PrivateKeyPair(BigInteger n, BigInteger d) {
        this.n = n;
        this.d = d;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }
}
