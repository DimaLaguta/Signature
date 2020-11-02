package filechecksummonitoring.util;

public class Container {

    private String filename;
    private String digitalSignature;

    public Container() {

    }

    public Container(String filename, String digitalSignature) {
        this.filename = filename;
        this.digitalSignature = digitalSignature;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }
}
