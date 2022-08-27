package RSA;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    private BigInteger n;
    private BigInteger p;
    private BigInteger q;
    private BigInteger e;
    private BigInteger d;
    public RSA() {
        SecureRandom r = new SecureRandom();
        this.p = new BigInteger(2048 / 2, 100, r);
        this.q = new BigInteger(2048 / 2, 100, r);
        this.n = this.p.multiply(this.q);
    }

    private BigInteger totienteEuler(BigInteger n) {
        return  (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }

    public void generateKey() {
        BigInteger m = totienteEuler(this.n);
        this.e = new BigInteger("3");

        while(m.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        this.d = e.modInverse(m);
    }

    public String encryptValue(String message) {
        return new BigInteger(message.getBytes()).modPow(this.e, this.n).toString();
    }

    public String decryptValue(String hashedString) {
        return new String(new BigInteger(hashedString).modPow(d, n).toByteArray());
    }
}
