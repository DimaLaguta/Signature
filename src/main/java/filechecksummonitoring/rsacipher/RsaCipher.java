package filechecksummonitoring.rsacipher;

import filechecksummonitoring.util.Keys;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class RsaCipher {

    private static final String OPEN_KEYS_FILE = "open_keys.json";
    private static final String CLOSED_KEYS_FILE = "closed_keys.json";

    private final static int bitLength = 256;
    private static SecureRandom random;
    private static BigInteger one;
    private Charset charset = StandardCharsets.UTF_8;
    private ObjectMapper mapper;

    private BigInteger e;
    private BigInteger d;
    private BigInteger n;

    public RsaCipher(ObjectMapper objectMapper) {
        random = new SecureRandom();
        one = BigInteger.valueOf(1);

        this.mapper = objectMapper;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger getN() {
        return n;
    }

    public void init() {
        BigInteger p = BigInteger.probablePrime(bitLength, random);
        BigInteger q = BigInteger.probablePrime(bitLength, random);

        n = p.multiply(q);

        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));
        e = calcaulateE(phi);
        d = e.modInverse(phi);

        Keys openKeys = new Keys(e, n);
        Keys closedKeys = new Keys(d, n);

        try {
            mapper.writeValue(new File(OPEN_KEYS_FILE), openKeys);
            mapper.writeValue(new File(CLOSED_KEYS_FILE), closedKeys);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String encrypt(String message) {

        Keys openKeys = null;

        try {
            openKeys = mapper.readValue(new FileReader(OPEN_KEYS_FILE), new TypeReference<Keys>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        BigInteger e = openKeys.getFirstKey();
        BigInteger n = openKeys.getSecondKey();

        BigInteger encryptedMessage = new BigInteger(message.getBytes(charset));

        return encryptedMessage.modPow(e, n).toString();
    }

    public String decrypt(String message) {

        Keys closedKeys = null;

        try {
            closedKeys = mapper.readValue(new FileReader(CLOSED_KEYS_FILE), new TypeReference<Keys>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        BigInteger d = closedKeys.getFirstKey();
        BigInteger n = closedKeys.getSecondKey();

        BigInteger integerMessage = new BigInteger(message);
        BigInteger decryptedMessage = integerMessage.modPow(d, n);

        return new String(decryptedMessage.toByteArray(), charset);
    }

    private BigInteger calcaulateE(BigInteger phi) {
        for (BigInteger i = phi.subtract(one); i.compareTo(one) > 0; i = i.subtract(one)) {
            if (i.isProbablePrime(1) && i.gcd(phi).equals(one)) {
                return i;
            }
        }

        return BigInteger.valueOf(2);
    }
}
