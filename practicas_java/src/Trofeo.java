import java.awt.*;
import java.util.Random;

public class Trofeo {

    private int x, y;
    private int tamaño = 20;

    public Trofeo(int ancho, int alto) {
        generarPosicionAleatoria(ancho, alto);
    }

    public void generarPosicionAleatoria(int ancho, int alto) {
        Random r = new Random();
        x = r.nextInt(ancho - tamaño);
        y = r.nextInt(alto - tamaño);
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, tamaño, tamaño);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, tamaño, tamaño);
    }
}