import java.awt.Color;
import java.awt.Graphics;

public class Jugador extends Entidad implements Movible {

    private int velocidad;
    private int dx;
    private int dy;
    private Color color;

    public Jugador(int x, int y, int ancho, int alto, int velocidad, Color color) {
        super(x, y, ancho, alto);
        this.velocidad = velocidad;
        this.color = color;
        this.dx = 0;
        this.dy = 0;
    }

    public void setDireccion(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void mover() {
        x += dx * velocidad;
        y += dy * velocidad;
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, ancho, alto);
    }

    public int getVelocidad() {
        return velocidad;
    }
}