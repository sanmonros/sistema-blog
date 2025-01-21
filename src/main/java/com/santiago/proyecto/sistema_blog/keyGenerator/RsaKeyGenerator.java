package com.santiago.proyecto.sistema_blog.keyGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

public class RsaKeyGenerator {

    public static void main(String[] args) {
        try {
            // Generar el par de claves
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            // Guardar las claves en archivos en formato PEM
            saveKeyInPEMFormat("C:\\Users\\santiago.montaño\\Desktop\\security\\src\\main\\resources\\jwtKeys\\private_key.pem", "RSA PRIVATE KEY", privateKey.getEncoded());
            saveKeyInPEMFormat("C:\\Users\\santiago.montaño\\Desktop\\security\\src\\main\\resources\\jwtKeys\\public_key.pem", "RSA PUBLIC KEY", publicKey.getEncoded());

            System.out.println("Claves RSA generadas y guardadas en archivos correctamente en formato PEM.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveKeyInPEMFormat(String path, String description, byte[] key) throws IOException {
        PemObject pemObject = new PemObject(description, key);
        try (PemWriter pemWriter = new PemWriter(new FileWriter(path))) {
            pemWriter.writeObject(pemObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
