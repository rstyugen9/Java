import java.awt.*;

public class Jugador {

    private int x, y;
    private int ancho, alto;
    private int velocidad;
    private Color color;

    public Jugador(int x, int y, int ancho, int alto, int velocidad, Color color) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
        this.color = color;
    }

    public void mover(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void dibujar(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, ancho, alto);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getVelocidad() { return velocidad; }
}