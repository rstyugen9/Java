import javax.swing.JFrame;

public class VentanaJuego extends JFrame {

    public VentanaJuego() {
        setTitle("Juego de Futbol");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new PanelJuego());
        setVisible(true);
    }
}