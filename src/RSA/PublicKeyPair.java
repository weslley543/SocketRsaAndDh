package RSA;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigInteger;


@NoArgsConstructor
public class PublicKeyPair {
    private BigInteger n;
    private BigInteger e;

    public PublicKeyPair(BigInteger n, BigInteger e) {
        this.n = n;
        this.e = e;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }
}
