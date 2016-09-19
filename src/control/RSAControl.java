package control;

import java.math.BigInteger;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class 
RSAControl {

    private  Cipher cipher;
    // RSA keys are generated when Client and Server connect
    private PrivateKey myPrivateKey;
    private byte[] myPublicKey;
    private byte[] interlocutorPublicKey = null;
    // Strings are encoded / decoded in BASE64
    private BASE64Decoder b64decoder = new BASE64Decoder();
    private BASE64Encoder b64encoder = new BASE64Encoder();

    public RSAControl() {
        try {
             cipher = Cipher.getInstance("RSA");
             Security.addProvider(new BouncyCastleProvider());
            } 
catch (Exception ex) {
            Logger.getLogger(RSAControl.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    
// Generates a pair of public and private keys using 512 bytes
    public KeyPair generateKey() {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(512);
            keyPair = keyGen.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            myPublicKey = publicKey.getEncoded();
            setMyPrivateKey(privateKey);
        } 
        catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSAControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return keyPair;
    }

    // Encrypts text using public key
    public String encrypt(String text, PublicKey key) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        String cipherText = new String(cipher.doFinal(b64encoder.encode(text.getBytes()).getBytes()));
        BigInteger cipherTextBigInteger = new BigInteger(cipherText.getBytes());
        return cipherTextBigInteger.toString();
    }

    // Uses the public key to encrypt the interlocutor
    public String encrypt(String text) throws Exception {
        return encrypt(text, bytesToPublicKey(interlocutorPublicKey));
    }

    // Decrypts text using private key
    public String decrypt(String encrypted64BigIntStr, PrivateKey key) throws Exception {
        BigInteger encrypted64BigInt = new BigInteger(encrypted64BigIntStr);
        String encrypted64Str = new String(encrypted64BigInt.toByteArray());
        cipher.init(Cipher.DECRYPT_MODE, key);
        String dectyptedText = new String(cipher.doFinal(encrypted64Str.getBytes()));
        return new String(b64decoder.decodeBuffer(dectyptedText));
    }
    
    // Use my private key to decrypt
    public String decrypt(String text) throws Exception {
        return decrypt(text, myPrivateKey);
    }

    // Public key interlocutor is sent in byte [ ] and converted into a PublicKey object
    public static PublicKey bytesToPublicKey(byte[] publicKeybytes) {
        PublicKey publicKey = null;
        try {           
             KeyFactory keyFactory = KeyFactory.getInstance("RSA");
             EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeybytes);
             publicKey = keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(RSAControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSAControl.class.getName()).log(Level.SEVERE, null, ex);
        }
   return publicKey;
    }

    // Teste
    public static void main(String[] args) {
        try {
            RSAControl crypto = new RSAControl();
            KeyPair keyPair = crypto.generateKey();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            byte[] publicKeyBytes = publicKey.getEncoded();
            byte[] privateKeyBytes = privateKey.getEncoded();
            System.out.println("Public Key: " + new BigInteger(publicKeyBytes));
            System.out.println("Private Key: " + new BigInteger(privateKeyBytes));
            String testeMsg = "As a seed knows how to make a flower ?";
            System.out.println("\nText: " + testeMsg);
            String encrypted = crypto.encrypt(testeMsg, bytesToPublicKey(publicKeyBytes));
            System.out.println("Encrypted: " + encrypted);
            String decrypted = crypto.decrypt(encrypted, keyPair.getPrivate());
            System.out.println("Decrypted: " + decrypted);
        } catch (Exception ex) {
            Logger.getLogger(RSAControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public byte[] getMyPublicKey() {
        return myPublicKey;
    }

    public void setMyPublicKey(byte[] myPublicKey) {
        this.myPublicKey = myPublicKey;
    }

    public PrivateKey getMyPrivateKey() {
        return myPrivateKey;
    }

    public byte[] getInterlocutorPublicKey() {
        return interlocutorPublicKey;
    
}
    
    public boolean hasInterlocutorPublicKey() {
        return interlocutorPublicKey!=null;
    }

    public void setInterlocutorPublicKey(byte[] interlocutorPublicKey) {
        this.interlocutorPublicKey = interlocutorPublicKey;
    }
    
    public void setMyPrivateKey(PrivateKey aMyPrivateKey) {
        myPrivateKey = aMyPrivateKey;
    }
}

