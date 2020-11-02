package filechecksummonitoring.digitalsignature;

import filechecksummonitoring.hasing.MD5Checksum;
import filechecksummonitoring.rsacipher.RsaCipher;
import filechecksummonitoring.util.Container;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DigitalSignature {

    private String DIGITAL_SIGNATURE_FILE = "digital_signature.json";

    private RsaCipher rsaCipher;
    private ObjectMapper mapper;

    public DigitalSignature(RsaCipher rsaCipher, ObjectMapper objectMapper) {
        this.rsaCipher = rsaCipher;
        this.mapper = objectMapper;
    }

    public Container create(String filename) {

        String hash = MD5Checksum.getMD5Checksum(filename);
        rsaCipher.init();
        String digitalSignature = rsaCipher.encrypt(hash);

        Container container = new Container(filename, digitalSignature);

        try {
            mapper.writeValue(new File(DIGITAL_SIGNATURE_FILE), container);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return container;
    }

    public String check() {

        Container container = null;

        try {
            container = mapper.readValue(new FileReader(DIGITAL_SIGNATURE_FILE), new TypeReference<Container>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        String hash = rsaCipher.decrypt(container.getDigitalSignature());
        String newHash = MD5Checksum.getMD5Checksum(container.getFilename());

        if (hash.equals(newHash)) {
            return "Digital signatures are equal";
        } else {
            return "Digital signatures are NOT!!! equal";
        }
    }
}
