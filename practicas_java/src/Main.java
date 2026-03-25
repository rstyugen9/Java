import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        JFrame ventana = new JFrame("Juego - Cancha");

        PanelJuego panel = new PanelJuego();

        ventana.add(panel);
        ventana.setSize(640, 480);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }
}