package DH;

import java.math.BigInteger;

public class DH {
    private BigInteger p;
    private BigInteger alpha;
    private BigInteger secret;
    private BigInteger x ;
    private BigInteger incommingX;
    private BigInteger key;
    public DH(int p, int alpha, int secret) {
        this.p = new BigInteger(String.valueOf(p));
        this.alpha = new BigInteger(String.valueOf(alpha));
        this.secret = new BigInteger(String.valueOf(secret));
        this.x  = new BigInteger("0") ;
        this.incommingX = new BigInteger("0");
        this.key = new BigInteger("0");
    }

    private void calcX() {
        this.x = this.alpha.modPow(this.secret, this.p);
    }
    private void generatePsk() {
        this.key = this.incommingX.modPow(this.secret, this.p);

    }
}
