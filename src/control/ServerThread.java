package control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import visual.ChatWindow;
import java.net.SocketException;
import javax.swing.JOptionPane;

class ServerThread extends Thread {

    private TCPControl connection;
    private ServerSocket serverSocket;
    private DataOutputStream out;

    public ServerThread(ServerSocket serverSocket, TCPControl connection) {
        this.serverSocket = serverSocket;
        this.connection = connection;
    }

    // Envia "oi" e minha Chave Pública ao Cliente
    private void cumprimentar() throws IOException, SocketException {
        if (out != null) {
            String message = "Bem-Vindo Cliente!";
            out.writeBytes(message + "\n");
            ChatWindow.log("BLUE", "Servidor> " + message);

            // Gera meu par de chaves RSA e envia a chave pública
            connection.getCrypto().generateKey();
            BigInteger myPublicKey = new BigInteger(connection.getCrypto().getMyPublicKey());
            out.writeBytes("<$__publicKey__$>" + myPublicKey + "\n");
            ChatWindow.log("GRAY", "[ Criptografia ] - Minha Chave Pública Enviada");
        }
    }

    public void run() {
        try {

            // enquanto não tiver conexão
            Socket connectionSocket = null;
            do {
                if (!serverSocket.isClosed()) {
                    connectionSocket = serverSocket.accept();
                }
                sleep(1000); // take it easy!
            } while (connectionSocket == null && !connectionSocket.isConnected());

            // conectou!
            if (!connection.isConnected()){
                connection.setConnected(true);
                connection.setServer(true);
                connection.setServerSocket(connectionSocket);
                ChatWindow.serverConnected();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            out = new DataOutputStream(connectionSocket.getOutputStream());

            // aperto-de-mão: Servidor cumprimenta cliente e enviar sua chave pública.
            cumprimentar();

            // enquanto estiver conectado
            while (connectionSocket.isConnected()) {

                // recebeu mensagem ?
                if (in.ready()) {
                    String mensagem = in.readLine();
                    if (mensagem != null) {
                        
                        if (mensagem.equals("Bye Bye!")){

                            ChatWindow.log("GRAY", "=== Cliente Desconectou! ===");
                            ChatWindow.desabilitaMsgs();
                       
                        } else if (mensagem.startsWith("<$__publicKey__$>")){

                            String hisPublicKeyBigIntegerStr = mensagem.substring(17, mensagem.length());
                            BigInteger hisPublicKeyBigInteger = new BigInteger(hisPublicKeyBigIntegerStr);
                            byte[] hisPublicKey = hisPublicKeyBigInteger.toByteArray(); 
                            ChatWindow.log("GRAY", "[ Criptografia ] - Chave Pública do Interlocutor Recebida");
                            connection.getCrypto().setInterlocutorPublicKey(hisPublicKey);
                            ChatWindow.log("GREEN", "[ Status ] - Mensagens serão criptografadas e descriptografadas automáticamente.");

                        } else {
                             // Se já tenho a chave pública do interlocutor, mensagens estão criptografadas
                            if (connection.getCrypto().hasInterlocutorPublicKey())
                                mensagem = connection.getCrypto().decrypt(mensagem);

                            ChatWindow.log("RED", "Cliente> " + mensagem);
                        }
                    }
                }

                // um loop a cada meio segundo, pra não ficar frenético
                sleep(500);
            }

            // Só chega aqui se sair do while, ou seja, se não tiver mais conexão
            JOptionPane.showMessageDialog(null, "Conexão com Cliente encerrada!", "Opz!", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);

        } catch (Exception ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


