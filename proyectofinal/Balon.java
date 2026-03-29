import java.awt.Color;
import java.awt.Graphics;

public class Balon extends Entidad implements Movible {

    private double dx = 0;
    private double dy = 0;

    public Balon(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
    }

    @Override
    public void mover() {
        x += Math.round(dx);
        y += Math.round(dy);

        // Menor fricción para que no se sienta tan lento
        dx *= 0.985;
        dy *= 0.985;

        if (Math.abs(dx) < 0.08) dx = 0;
        if (Math.abs(dy) < 0.08) dy = 0;
    }

    public void setDireccion(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void rebotarHorizontal() {
        dx = -dx;
        dy += (Math.random() * 2 - 1);
    }

    public void rebotarVertical() {
        dy = -dy;
        dx += (Math.random() * 2 - 1);
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, ancho, alto);
    }
}