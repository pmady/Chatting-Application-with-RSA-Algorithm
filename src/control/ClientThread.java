package control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import visual.ChatWindow;
import java.net.SocketException;
import javax.swing.JOptionPane;

class ClientThread extends Thread {

    private TCPControl connection;
    private Socket clientSocket;
    private DataOutputStream out;

    public ClientThread(Socket clientSocket, TCPControl connection) {
        this.clientSocket = clientSocket;
        this.connection = connection;
    }

    // Send " hi " and my Public Key to Server
    private void cumprimentar() throws IOException, SocketException {
        if (out != null) {
            String message = "Server hi!";
            out.writeBytes(message + "\n");
            ChatWindow.log("RED", "Client> " + message);

            // My generates RSA key pair and sends the public key
            connection.getCrypto().generateKey();
            BigInteger myPublicKey = new BigInteger(connection.getCrypto().getMyPublicKey());
            out.writeBytes("<$__publicKey__$>" + myPublicKey + "\n");
            ChatWindow.log("GRAY", "[ Encryption ] - My Public Key Asked");
        }
    }

    public void run() {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new DataOutputStream(clientSocket.getOutputStream());

            // while connected
            while (clientSocket.isConnected()) {

                // He received message?
                if (in.ready()) {
                    String message = in.readLine();
                    if (message != null) {
                        
                        if (message.equals("Welcome Customer !")) {
                            ChatWindow.log("BLUE", "Server> " + message);
                            cumprimentar(); // handshake
                       
                        } else if (message.equals("Bye Bye!")) {

                            ChatWindow.log("GRAY", "=== Disconnected server! ===");
                            ChatWindow.desabilitaMsgs();
                       
                        } else if (message.startsWith("<$__publicKey__$>")){

                            String hisPublicKeyBigIntegerStr = message.substring(17, message.length());
                            BigInteger hisPublicKeyBigInteger = new BigInteger(hisPublicKeyBigIntegerStr);
                            byte[] hisPublicKey = hisPublicKeyBigInteger.toByteArray(); 
                            ChatWindow.log("GRAY", "[ Criptografia ] - Chave P�blica do Interlocutor Recebida");
                            connection.getCrypto().setInterlocutorPublicKey(hisPublicKey);
                            ChatWindow.log("GREEN", "[ Status ] - Mensagens ser�o criptografadas e descriptografadas autom�ticamente.");

                        } else {
                            // Se j� tenho a chave p�blica do interlocutor, mensagens est�o criptografadas
                            if (connection.getCrypto().hasInterlocutorPublicKey())
                                message = connection.getCrypto().decrypt(message);

                            ChatWindow.log("BLUE", "Servidor> " + message);
                        }
                        
                        message = null;
                    }
                }

                // calma, um loop a cada meio segundo
                sleep(500);
            }

            JOptionPane.showMessageDialog(null, "Conex�o com Servidor encerrada!", "Opz!", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);

        } catch (Exception ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

