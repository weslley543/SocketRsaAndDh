package RSA;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    private PrivateKeyPair privateKeyPair;
    private PublicKeyPair publicKeyPair;

    public RSA() {
        this.generateKeys();
    }
    private BigInteger totienteEuler(BigInteger n, BigInteger p, BigInteger q) {
        return  (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }

    public void generateKeys() {
        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(2048 / 2, 100, r);
        BigInteger q = new BigInteger(2048 / 2, 100, r);
        BigInteger n = p.multiply(q);

        BigInteger m = totienteEuler(n, p, q);
        BigInteger e = new BigInteger("3");

        while(m.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        BigInteger d = e.modInverse(m);

        this.privateKeyPair = new PrivateKeyPair(n, d);
        this.publicKeyPair = new PublicKeyPair(n, e);
    }

    public String encryptValue(String message, BigInteger e, BigInteger n) {
        return new BigInteger(message.getBytes()).modPow(e, n).toString();
    }

    public String decryptValue(String hashedString) {
        return new String(new BigInteger(hashedString).modPow(this.getPrivateKeyPair().getD(), this.getPrivateKeyPair().getN()).toByteArray());
    }

    public PrivateKeyPair getPrivateKeyPair() {
        return privateKeyPair;
    }

    public String getPublicKeyPair() {
        String publicKeyPair = null;
        ObjectMapper om = new ObjectMapper();
        try {
            publicKeyPair = om.writeValueAsString(this.publicKeyPair);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return publicKeyPair;
    }
}
