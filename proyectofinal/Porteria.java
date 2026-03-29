import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Porteria {

    private int x, y, ancho, alto;
    private Color color;

    public Porteria(int x, int y, int ancho, int alto, Color color) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.color = color;
    }

    public void dibujar(Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, ancho, alto);
    }

    public Rectangle getArea() {
        return new Rectangle(x, y, ancho, alto);
    }
}