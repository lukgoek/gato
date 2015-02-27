/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gato;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author HbTO
 */
public class gameScreen extends javax.swing.JFrame implements ActionListener {
    MiBoton botones [][];
    
    String nickname="", player1 ="", player2 ="", comando ="", ejecutar ="", client ="1";
    
    
    int filaBtn, columnaBtn, ptsGato;
    
    boolean ganador=true, turn=false, clickExterno=false;
    
    //Un socket es la combinacion de una dir ip con un puerto.
    private Socket socket;
    
    
    /*Canales de comunicacion "espacio de memoria entre usuario/servidor.
      canal entrada, canal salida
      bits*/
    private InputStream inputStream;
    private OutputStream outputStream;
    
    //variables primitivas de java
    private DataInputStream entradaDatos;
    private DataOutputStream salidaDatos;
    
    
    
    public gameScreen() {
        initComponents();
        setLocationRelativeTo(null);
        
    }

    
/**********************************************************************************/    
    public void conexion(int puerto, String host, String nickname){
        /* host=ip puerto=puerto al que se conectara  */
        try {
            socket = new Socket(host, puerto);
            this.nickname = nickname;
            //lbNick.setText(this.nickname);
            comando = "nick";
            ejecutar = nickname;
            enviaDatos();
            
            startThread();
        } catch (IOException ex) {
            Logger.getLogger(gameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
/**********************************************************************************/ 
   
    public void startThread(){
        System.out.println(inputStream);
        
        Thread hiloServer = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    recibeDatos();
                    
                    
                }
                
            }
        });
        hiloServer.start();   
    }
    
    
/**********************************************************************************/   
    
    public void recibeDatos(){
        
        try {
            inputStream = socket.getInputStream();
            entradaDatos = new DataInputStream(inputStream);
            //System.out.println("RECIBEDATOS INTERFACE"+entradaDatos.readUTF());
            
            
            
            String comandos [] = entradaDatos.readUTF().split("/");
            
            
            //Coloca los nombres de los jugadores
            if(comandos[0].equals("player1")){
                
                player1=comandos[1];
                lTurn.setText(player1);
                System.out.println("palyer1 ="+player1);
                
            }
            
            if(comandos.length == 2){
            if(comandos[0].equals("player2")){
                
                player2=comandos[1];
                System.out.println("player2 ="+player2);
            }
            }
            
            
            if(comandos[0].equals("click")){
                System.out.println("Estoy recibiendo un click!");
                actionClick(comandos[1]);
            }
            
            if(comandos[0].equals("reset")){
                resetThisGame();
            }
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(gameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
/**********************************************************************************/  
    
    public void enviaDatos(){
        
        try {
            outputStream = socket.getOutputStream();
            salidaDatos = new DataOutputStream(outputStream);
            
            salidaDatos.writeUTF(this.nickname+"/"+comando+"/"+ejecutar);
            salidaDatos.flush();
             System.out.println("Se envio esto: "+this.nickname+"/"+comando+"/"+ejecutar);
            
        } catch (IOException ex) {
            Logger.getLogger(gameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
/**********************************************************************************/
    
    
    public void getTurn(boolean turn){
        this.turn=turn;
        
        
    }
    
    
 /**********************************************************************************/
    
    public void llenarPanel(){
        botones = new MiBoton[3][3];
        
        for(int i=0; i<botones.length; i++){
            for(int j=0; j<botones.length; j++){
                botones[i][j]= new MiBoton();
               botones[i][j].setBounds(j*(400/3), i*(400/3), 400/3, 400/3);
              // botones[i][j].setContentAreaFilled(false);
               botones[i][j].setBorder(javax.swing.BorderFactory.createEtchedBorder());
                botones[i][j].addActionListener(this);
                pnlBotones.add(botones[i][j]);
            }
        }
        pnlBotones.repaint();
        
        
    }
    
/**********************************************************************************/
    
    public void actionClick(String posicion){
        System.out.println("Hagamos click en : "+posicion);
        
        if(posicion.equals("1")){
            clickExterno = true;
            this.botones[0][0].doClick();
        }
        
        if(posicion.equals("2")){
            clickExterno = true;
            this.botones[0][1].doClick();
        }
        if(posicion.equals("3")){
            clickExterno = true;
            this.botones[0][2].doClick();
        }
        
        if(posicion.equals("4")){
            clickExterno = true;
            this.botones[1][0].doClick();
        }
        
        if(posicion.equals("5")){
            clickExterno = true;
            this.botones[1][1].doClick();
        }
        
        if(posicion.equals("6")){
            clickExterno = true;
            this.botones[1][2].doClick();
        }
        
        if(posicion.equals("7")){
            clickExterno = true;
            this.botones[2][0].doClick();
        }
        
        if(posicion.equals("8")){
            clickExterno = true;
            this.botones[2][1].doClick();
        }
        
        if(posicion.equals("9")){
            clickExterno = true;
            this.botones[2][2].doClick();
        }
        
    }
    
    public void resetThisGame(){
        JOptionPane.showMessageDialog(null,"Reset game... The progress will be lost.");
  
            lWin.setText("");
            lWin.repaint();
            pnlBotones.removeAll();
            llenarPanel();
            ganador=true;
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBotones = new javax.swing.JPanel();
        lTurno = new javax.swing.JLabel();
        lTurn = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        lWin = new javax.swing.JLabel();
        lFondo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        backMenu = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlBotones.setLayout(new java.awt.GridLayout(3, 3));

        lTurno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lTurno.setText("Turn:");

        lTurn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        lWin.setFont(new java.awt.Font("Comic Sans MS", 1, 36)); // NOI18N
        lWin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lFondo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fondo.png"))); // NOI18N

        backMenu.setText("Back <-");
        backMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(backMenu);

        jMenu2.setText("About");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lTurno)
                .addGap(18, 18, 18)
                .addComponent(lTurn, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnReset))
            .addGroup(layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lWin, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(lFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReset)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lTurno)
                            .addComponent(lTurn, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(522, 522, 522)
                .addComponent(lWin, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(lFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
      
        comando ="reset";
        ejecutar ="reset";
        enviaDatos();
        
           
        
    }//GEN-LAST:event_btnResetActionPerformed

    private void backMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMenuMouseClicked
        int salir = JOptionPane.showConfirmDialog(null,"Are you sure?.\nThe progress will be lost.","¿Do you want to leave?", JOptionPane.YES_NO_OPTION);
    
            if(salir==0){
             // Upps se quiere ir...
                
                mainMenu obj = new mainMenu();
                obj.setVisible(true);
                setVisible(false);
            }else{
               
                //No hacemos nada continuamos :D
            } 
    }//GEN-LAST:event_backMenuMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gameScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu backMenu;
    private javax.swing.JButton btnReset;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lFondo;
    public static javax.swing.JLabel lTurn;
    private javax.swing.JLabel lTurno;
    private javax.swing.JLabel lWin;
    private javax.swing.JPanel pnlBotones;
    // End of variables declaration//GEN-END:variables

    
    public void verificar() {
        
        if(ganador==true){
      //examina si un jugador a ganado
            String lineaV1, lineaV2, lineaV3, lineaH1, lineaH2, lineaH3, lineaD1, lineaD2;
            ptsGato=0;
            //recuperar valores
            String btn00 = botones[0][0].tipo;
            String btn01 = botones[0][1].tipo;
            String btn02 = botones[0][2].tipo;
            
            String btn10 = botones[1][0].tipo;
            String btn11 = botones[1][1].tipo;
            String btn12 = botones[1][2].tipo;
            
            String btn20 = botones[2][0].tipo;
            String btn21 = botones[2][1].tipo;
            String btn22 = botones[2][2].tipo;
            
           
            lineaV1=btn00+btn01+btn02;
            lineaV2=btn10+btn11+btn12;
            lineaV3=btn20+btn21+btn22;
            
            lineaH1=btn00+btn10+btn20;
            lineaH2=btn01+btn11+btn21;
            lineaH3=btn02+btn12+btn22;
            
            lineaD1=btn00+btn11+btn22;
            lineaD2=btn02+btn11+btn20;
            
            /*System.out.println("*"+lineaH1+"*");
            System.out.println("*"+lineaH2+"*");
            System.out.println("*"+lineaH3+"*");
            
            System.out.println("*"+lineaV1+"*");
            System.out.println("*"+lineaV2+"*");
            System.out.println("*"+lineaV3+"*");*/
            
            
        //Horizontales!   
        if(lineaH1.equals("111")){
            lWin.setText(""+player1+" WIN!");
            pnlBotones.setEnabled(false);
            ganador=false;
        }
        
        if(lineaH1.equals("222")){
            lWin.setText(""+player2+" WIN!");
            ganador=false;
        }
        
        if(lineaH2.equals("111")){
            lWin.setText(""+player1+" WIN!");
            ganador=false;
        }
        
        if(lineaH2.equals("222")){
            lWin.setText(""+player2+" WIN!");
            ganador=false;
        }
        if(lineaH3.equals("111")){
            lWin.setText(""+player1+" WIN!");
            ganador=false;
        }
        
        if(lineaH3.equals("222")){
            lWin.setText(""+player2+" WIN!");
            ganador=false;
        }
        
        
        //Verticales!   
        if(lineaV1.equals("111")){
            lWin.setText(""+player1+" WIN!");
            pnlBotones.setEnabled(false);
            ganador=false;
        }
        
        if(lineaV1.equals("222")){
            lWin.setText(""+player2+" WIN!");
            ganador=false;
        }
        
        if(lineaV2.equals("111")){
            lWin.setText(""+player1+" WIN!");
            ganador=false;
        }
        
        if(lineaV2.equals("222")){
            lWin.setText(""+player2+" WIN!");
            ganador=false;
        }
        if(lineaV3.equals("111")){
            lWin.setText(""+player1+" WIN!");
            ganador=false;
        }
        
        if(lineaV3.equals("222")){
            lWin.setText(""+player2+" WIN!");
            ganador=false;
        }
        
        
        //Diagonales..
        if(lineaD1.equals("111")){
            lWin.setText(""+player1+" WIN!");
            ganador=false;
        }
        
        if(lineaD1.equals("222")){
            lWin.setText(""+player2+" WIN!");
            ganador=false;
        }
        
        if(lineaD2.equals("111")){
            lWin.setText(""+player1+" WIN!");
            ganador=false;
        }
        
        if(lineaD2.equals("222")){
            lWin.setText(""+player2+" WIN!");
            ganador=false;
        }
        
        }
        
        for(int i=0; i<botones.length; i++){
            for(int j=0; j<botones.length; j++){
                if(!botones[i][j].tipo.equals("")){
                  ptsGato++; 
                }
            }
        }
        
        if(ptsGato==9){
            lWin.setText("Sorry, the CAT WIN!");
        }
        
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        comando = "click";
        
        if(ganador==true){
        String contenido, playerTurn = lTurn.getText();
        
        if(playerTurn.equals(this.nickname)  || clickExterno == true){
        
        MiBoton botones = (MiBoton)e.getSource();
        contenido = botones.tipo;
        
        for(int i =0; i<this.botones.length; i++){
            for(int j=0; j<this.botones.length; j++){
                if(botones == this.botones[i][j]){
                    filaBtn=i;
                    columnaBtn=j;
                    //                    //System.out.println(i+"=="+j);
                    System.out.println(i+"=="+j);
                }
            }
        }
        
        if(filaBtn == 0 && columnaBtn == 0){
            ejecutar = "1";
        }
        if(filaBtn == 0 && columnaBtn == 1){
            ejecutar = "2";
        }
        if(filaBtn == 0 && columnaBtn == 2){
            ejecutar = "3";
        }
        
        
        if(filaBtn == 1 && columnaBtn == 0){
            ejecutar = "4";
        }
        if(filaBtn == 1 && columnaBtn == 1){
            ejecutar = "5";
        }
        if(filaBtn == 1 && columnaBtn == 2){
            ejecutar = "6";
        }
        
        if(filaBtn == 2 && columnaBtn == 0){
            ejecutar = "7";
        }
        if(filaBtn == 2 && columnaBtn == 1){
            ejecutar = "8";
        }
        if(filaBtn == 2 && columnaBtn == 2){
            ejecutar = "9";
        }
        
        //enviaDatos();
 //       if(this.nickname.equals(playerTurn)){
        //verifica si es player1
        if(playerTurn.equals(player1)){
            //System.out.println("aqui"+player1);
            if(contenido.equals("")){
               botones.setIcon(new ImageIcon(getClass().getResource("../images/player1.png")));
               botones.tipo="1";
               enviaDatos();
               verificar();
               lTurn.setText(player2);
               clickExterno=false;
            }
        }
        
        
        //Verifica si es palyer2
        if(playerTurn.equals(player2)){
            //System.out.println("aqui"+player2);
            if(contenido.equals("")){
                botones.setIcon(new ImageIcon(getClass().getResource("../images/player2.png")));
                botones.tipo="2";
                enviaDatos();
                verificar();
                lTurn.setText(player1);
                clickExterno=false;
            }
        }
        
      }
    }
   }
    
}
