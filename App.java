import javax.swing.JFrame;
public class App {
    public static void main(String[] args) throws Exception {
        int sütunSayac= 21;
        int satirSayac=19;
        int pikselSize=32;
        int oyun_Genislik= satirSayac*pikselSize;
        int oyun_Uzunlugu= sütunSayac*pikselSize;

        JFrame frame= new JFrame("Berkay'in pack man oyunu");
        frame.setSize(oyun_Genislik,oyun_Uzunlugu);
        frame.setVisible(true); //jframe varsayilan olarak gizli olusur bu pencerenin ekranda gorunmesini saglar. bu satiri yazmazsan pencere oluşturulur ama ekranda gorunmez
        frame.setLocationRelativeTo(null);// jframe varsayılan olarak ekranın sol üst köşesinde açılır bu komut pencerenin ekranda ortalanmasını sağlar
        frame.setResizable(false); //pencerenin boyutunun değiştirilmemesini sağlar.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //pencereyi kapattığında java uygulamasının sonlanmasını sağlar.
        PacMan pacmanGame=new PacMan();
        frame.add(pacmanGame);
        frame.pack();
        pacmanGame.requestFocus();
        frame.setVisible(true);









     
    }
}
