import com.sun.jdi.PathSearchingVirtualMachine;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Random;

import javax.swing.*;
public class PacMan extends JPanel implements ActionListener,KeyListener {
    class Block{
        int x;
        int y;
        int genislik;
        int boy;
        Image image;

        int baslangicX;
        int baslangicY;
        char yon ='u';
        int hizX=0;
        int hizY=0;


        Block(Image image, int x,int y,int genislik,int boy){
            this.image=image;
            this.x=x;
            this.y=y;
            this.genislik=genislik;
            this.boy=boy;
            this.baslangicX=x;
            this.baslangicY=y;
            

        }
        void yonuGuncelle(char yon){
            char oncekiYon=this.yon;
            this.yon=yon;
            hiziGuncelle();
            this.x+=this.hizX;
            this.y+=this.hizY;
            for(Block duvar:duvarlar){
                if(carpisma(this, duvar)){
                    this.x-=this.hizX;
                    this.y-=this.hizY;
                    this.yon=oncekiYon;
                    hiziGuncelle();


                }
            }

        } 
        void hiziGuncelle(){
            if(this.yon=='U'){
                this.hizX=0;
                this.hizY= -pikselSize/4;
            }
            else if(this.yon=='D'){
                this.hizX=0;
                this.hizY=pikselSize/4;
            }
            else if(this.yon=='L'){
                this.hizX=-pikselSize/4;
                this.hizY=0;
            }
            else if(this.yon=='R'){
                this.hizX=pikselSize/4;  
                this.hizY=0;
            }
        }
        void reset(){
            this.x=this.baslangicX;
            this.y=this.baslangicY;

        }
        


    }
    private int sütunSayac= 21;
    private int satirSayac=19;
    private int pikselSize=32;
    private int oyun_Genislik= satirSayac*pikselSize;
    private int oyun_Uzunlugu= sütunSayac*pikselSize;

    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image pinkGhostImage;
    private Image redGhostImage;

    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

   //X = wall, O = skip, P = pac man, ' ' = food
    //Ghosts: b = blue, o = orange, p = pink, r = red
    private String[] engeller = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
    };



    HashSet<Block> duvarlar;
    HashSet<Block> yemekler;
    HashSet<Block> hayaletler;
    Block pacman;
    Timer gameLoop;
    char [] yonler= {'U','D','L','R'};
    Random random= new Random();
    int skor=0;
    int hak = 3;
    boolean oyunBitti=false;






        public PacMan() {
        setPreferredSize(new Dimension(oyun_Genislik,oyun_Uzunlugu));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        //resimleri yükle
        wallImage= new ImageIcon(getClass().getResource("./wall.png")).getImage();
        redGhostImage= new ImageIcon(getClass().getResource("./redGhost.png")).getImage();
        blueGhostImage= new ImageIcon(getClass().getResource("./blueGhost.png")).getImage();
        orangeGhostImage= new ImageIcon(getClass().getResource("./orangeGhost.png")).getImage();
        pinkGhostImage= new ImageIcon(getClass().getResource("./pinkGhost.png")).getImage();
        
        pacmanUpImage= new ImageIcon(getClass().getResource("./pacmanUp.png")).getImage();
        pacmanDownImage= new ImageIcon(getClass().getResource("./pacmanDown.png")).getImage();
        pacmanLeftImage= new ImageIcon(getClass().getResource("./pacmanLeft.png")).getImage();
        pacmanRightImage= new ImageIcon(getClass().getResource("./pacmanRight.png")).getImage();
        mapiYukle();
        for(Block hayalet:hayaletler){
            char yeniyon=yonler[random.nextInt(4)];
            hayalet.yonuGuncelle(yeniyon);
        }
        gameLoop=new Timer(50,this);
        gameLoop.start();
        
    }
    public void mapiYukle(){
        duvarlar= new HashSet<Block>();
        yemekler= new HashSet<Block>();
        hayaletler= new HashSet<Block>();
        for (int j=0;j<sütunSayac;j++){
            for (int i=0; i<satirSayac;i++){
                String sütun=engeller[j];
                char engellerChar=sütun.charAt(i);

                int x= i*pikselSize;
                int y= j*pikselSize;
                if(engellerChar=='X'){
                    Block duvar=new Block(wallImage,x,y,pikselSize,pikselSize);
                    duvarlar.add(duvar);
                }
                else if(engellerChar=='b'){
                    Block hayalet= new Block(blueGhostImage,x,y,pikselSize,pikselSize);
                    hayaletler.add(hayalet);

                }
                else if(engellerChar=='o'){
                    Block hayalet= new Block(orangeGhostImage,x,y,pikselSize,pikselSize);
                    hayaletler.add(hayalet);

                }
                else if(engellerChar=='p'){
                    Block hayalet= new Block(pinkGhostImage,x,y,pikselSize,pikselSize);
                    hayaletler.add(hayalet);

                }
                else if(engellerChar=='r'){
                    Block hayalet= new Block(redGhostImage,x,y,pikselSize,pikselSize);
                    hayaletler.add(hayalet);

                }
                else if(engellerChar=='P'){
                    pacman=new Block(pacmanRightImage, x, y, pikselSize, pikselSize);

                }
                else if(engellerChar==' ') {
                    Block yemek= new Block(null,x+14,y+14,4,4);
                    yemekler.add(yemek);
                }
            }
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);


    }
    public void draw (Graphics g){
        for(Block duvar:duvarlar){
            g.drawImage(duvar.image,duvar.x,duvar.y,duvar.genislik,duvar.boy,null);
        }
        for (Block yemek: yemekler){
            g.setColor(Color.WHITE);
            g.fillRect(yemek.x, yemek.y, yemek.genislik, yemek.boy);
        }
        for (Block hayalet:hayaletler){
            g.drawImage(hayalet.image, hayalet.x, hayalet.y, hayalet.genislik,hayalet.boy,null);
        }
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.genislik,pacman.boy,null);
    
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        if(oyunBitti){
            g.drawString("Oyun Bitti Berkay kazandi" +String.valueOf(skor),pikselSize/2,pikselSize/2);
        }
        else {
             g.drawString("x"+ String.valueOf(hak)+ "skor: "+ String.valueOf(skor),pikselSize/2,pikselSize/2);
        }   
        

    }
    public void move(){
    

        pacman.x+= pacman.hizX;
        pacman.y+=pacman.hizY;

        for (Block duvar:duvarlar) {
            if(carpisma(pacman, duvar)){
                pacman.x-=pacman.hizX;
                pacman.y-=pacman.hizY;
                break;
            }
            
        }
        //hayalet carpismasi kontrolü
        for(Block hayalet:hayaletler){
            if(carpisma(hayalet, pacman)){
                hak-=1;
                if(hak ==0){
                    oyunBitti=true;
                    return;
                }
                yeriSifirla();

            }
        }
        for (Block hayalet:hayaletler){
            if(hayalet.y==pikselSize*9&&hayalet.yon!='U'&&hayalet.yon!='D'){
                hayalet.yonuGuncelle('U');
            }

            hayalet.x+=hayalet.hizX;
            hayalet.y+=hayalet.hizY;
            for(Block duvar:duvarlar){
                if (carpisma(duvar, hayalet)||hayalet.x<=0||hayalet.x+hayalet.genislik>=oyun_Genislik){
                     hayalet.x-=hayalet.hizX;
                     hayalet.y-=hayalet.hizY;
                     char yeniyon=yonler[random.nextInt(4)];
                     hayalet.yonuGuncelle(yeniyon);
                     System.out.println("Pac-Man: x=" + pacman.x + ", y=" + pacman.y);


                }

            }
        }
//yemek carpismasi kontrolü
  Block yemekYeme=null; 
  for (Block yemek:yemekler){
    if(carpisma(pacman, yemek)){
        yemekYeme=yemek;
        skor+=10;
        
    }

  }
    yemekler.remove(yemekYeme);
    if(yemekler.isEmpty()){
        mapiYukle();
        yeriSifirla();

    }

    }
        public boolean carpisma(Block a, Block b){
            return a.x<b.x+b.genislik&&
                   a.x+ a.genislik>b.x &&
                   a.y <b.y +b.boy&&
                   a.y + a.boy>b.y;

        }
        public void yeriSifirla(){
            pacman.reset();
            pacman.hizX=0;
            pacman.hizY=0;
            for(Block hayalet:hayaletler){
                hayalet.reset();
                char yeniyon=yonler[random.nextInt(4)];
                hayalet.yonuGuncelle(yeniyon);

            }


        }
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); 
        repaint();
        if(oyunBitti){
            gameLoop.stop();
        }


 
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) { //hangi tusa basildigini anlamamizi saglar.
        if(oyunBitti){
            mapiYukle();
            yeriSifirla();
            hak = 3;
            skor =0;
            oyunBitti=false;
            gameLoop.start();

        }
        if(e.getKeyCode()==KeyEvent.VK_UP){
            pacman.yonuGuncelle('U');
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN){
            pacman.yonuGuncelle('D');
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT){
            pacman.yonuGuncelle('L');
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            pacman.yonuGuncelle('R');
        }
        if(pacman.yon== 'U'){
            pacman.image=pacmanUpImage;
        }
        else if(pacman.yon =='D'){
            pacman.image=pacmanDownImage;
        }
        else if(pacman.yon =='L'){
            pacman.image=pacmanLeftImage;
        }
        else if(pacman.yon =='R'){
            pacman.image=pacmanRightImage;
        }

    }
}




