package RSA;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    // Declara a chave privada
    private PrivateKeyPair privateKeyPair;
    // Declara a chave publica
    private PublicKeyPair publicKeyPair;

    public RSA() {
        this.generateKeys(); // Chama função para geração de chaves
    }
    private BigInteger totienteEuler(BigInteger n, BigInteger p, BigInteger q) {
        return  (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }

    public void generateKeys() {
        // Esta classe fornece um gerador de números aleatórios criptograficamente forte
        SecureRandom r = new SecureRandom();
        /*
        * A classe BigInteger é usada para a operação matemática que envolve cálculos de inteiros muito grandes que
        * estão fora do limite de todos os tipos de dados primitivos disponíveis.
        */
        BigInteger p = new BigInteger(2048 / 2, 100, r);
        BigInteger q = new BigInteger(2048 / 2, 100, r);
        BigInteger n = p.multiply(q);

        // Função totiente de Euler
        BigInteger m = totienteEuler(n, p, q);
        BigInteger e = new BigInteger("3");

        while(m.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        BigInteger d = e.modInverse(m);

        this.privateKeyPair = new PrivateKeyPair(n, d);
        this.publicKeyPair = new PublicKeyPair(n, e);
    }

    // Função que encripta o valor
    public String encryptValue(String message, BigInteger e, BigInteger n) {
        return new BigInteger(message.getBytes()).modPow(e, n).toString();
    }

    // Função que desencripta o valor
    public String decryptValue(String hashedString) {
        return new String(new BigInteger(hashedString).modPow(this.getPrivateKeyPair().getD(), this.getPrivateKeyPair().getN()).toByteArray());
    }

    // Getter para a chave privada
    private PrivateKeyPair getPrivateKeyPair() {
        return privateKeyPair;
    }

    /*
    * O Getter da chave publica transforma o a chave em um json
    * para facilitar compreensão
    */
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
