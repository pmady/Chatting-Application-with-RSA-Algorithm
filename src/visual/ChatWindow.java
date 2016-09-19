package visual;


import control.TCPControl;

import java.awt.Font;
import java.awt.event.KeyEvent;

import java.io.IOException;

import java.net.UnknownHostException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.html.HTMLDocument;


public class ChatWindow extends javax.swing.JFrame {

    private TCPControl connection = new TCPControl();
    private static StringBuilder chatText = new StringBuilder();
    private boolean isServing = false;

    public ChatWindow() {
        try {
            //Sets the GUI according to the Operating System 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            initComponents();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        jButton6.setEnabled(false);
        jButton1.setEnabled(false);
        jTextField1.setEnabled(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aaaa");
        log("BLACK", "=== ChatRSA @ " + dateFormat.format(new Date()) + " ===");
    }

    @SuppressWarnings("unchecked")
    // IDE Auto-Generated Dialog
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jSeparator1 = new javax.swing.JSeparator();
        jToolBar1 = new javax.swing.JToolBar();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ChatRSA");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Message"));

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jTextField1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 99, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton1))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Chat"));

        jEditorPane1.setContentType("text/html");
        jEditorPane1.setEditable(false);
        Font font = UIManager.getFont("Label.font");
        String bodyRule = "body { padding: 5px; font-family: " + font.getFamily() + "; " + "font-size: " + font.getSize() + "pt; }";
        ((HTMLDocument)jEditorPane1.getDocument()).getStyleSheet().addRule(bodyRule);
        jScrollPane1.setViewportView(jEditorPane1);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(58, 58, 58)
                        .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jToolBar1.setFloatable(false);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/images/gear_refresh.png"))); // NOI18N
        jButton4.setText("connect server");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/images/gear_run.png"))); // NOI18N
        jButton5.setText("Connect to Server");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);
        jToolBar1.add(jSeparator3);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/images/gear_error.png"))); // NOI18N
        jButton6.setText("Disconnect and Exit");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visual/images/gear_information.png"))); // NOI18N
        jButton3.setText("About");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(9, 9, 9)
                .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Send message 
    private void sendMessage(String message) {
        if (!message.equals("")) {
            connection.sendMessage(message);
            jTextField1.setText(""); // clean text field to send
        }
    }

    // Send message when you press the button
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        sendMessage(jTextField1.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

    // Send message priate press ENTER
    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            sendMessage(jTextField1.getText());
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    // Toolbar button: Disconnect & Exit
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        connection.sair();
    }//GEN-LAST:event_jButton6ActionPerformed

    // Toolbar button: About
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        AboutWindow about = new AboutWindow();
        about.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    // Toolbar button: Connect to Server
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        log("GRAY", "[Client ] - Connecting to server ...");

        try {
            String host = JOptionPane.showInputDialog("Address:", "137.30.209.204");

            if (host != null && connection.connect(host)) {
                // Client connected to the Server
                setTitle("ChatRSA - Client");
                jButton5.setEnabled(false);
                jButton4.setEnabled(false);
                jButton6.setEnabled(true);
                jButton1.setEnabled(true);
                jTextField1.setEnabled(true);
                log("GREEN", "[Status ] - Connected !");

            } else {
                ChatWindow.log("GRAY", "[Client ] - Cancelled");
            }

        } catch (UnknownHostException ex) {
            ChatWindow.log("GRAY", "[ Error] - connection can not be established in this host.");
        } catch (IOException ex) {
            ChatWindow.log("GRAY", "[ Error] - connection can not be established in this host.");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    // Toolbar button: Enable / Disable Server
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {

            if (isServing) {
                isServing = false;
                connection.listen(false);
                jButton5.setEnabled(true);
                jButton4.setText("Enable Server");
                setTitle("ChatRSA ");
                ChatWindow.log("GRAY", "[ Server] - Closed");
            } else {
                isServing = true;
                connection.listen(true);
                jButton5.setEnabled(false);
                jButton4.setText("Disable Server");
                setTitle("ChatRSA - Server");
                ChatWindow.log("GRAY", "[ Server] - Waiting for Connection ...");
            }

        } catch (IOException ex) {
            ChatWindow.log("GRAY", "[ Error] - Maybe another server is already connected ");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    // Server established connection with client
    public static void serverConnected() {
        jButton5.setEnabled(false);
        jButton4.setEnabled(false);
        jButton6.setEnabled(true);
        jButton1.setEnabled(true);
        jTextField1.setEnabled(true);
        log("GREEN", "[Status] - Connected !");
    }
    
    // Disables sending messages.
    public static void desabilitaMsgs() {
        jButton1.setEnabled(false);
        jTextField1.setEnabled(false);
    }

    // Plays pro chat panel text
    public static void log(String color, String message) {
        message = "<font color=\"" + color + "\">" + message + "</font><br>";
        chatText.append(message);
        jEditorPane1.setText(chatText.toString());
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ChatWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private static javax.swing.JButton jButton4;
    private static javax.swing.JButton jButton5;
    private static javax.swing.JButton jButton6;
    private static javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private static javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}

