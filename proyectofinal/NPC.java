import java.awt.Color;
import java.awt.Graphics;

public class NPC extends Entidad implements Movible {

    private int velocidad;
    private Color color;
    private Balon balon;
    private int tipo;

    private NPC[] equipo;

    public NPC(int x, int y, int ancho, int alto, int velocidad, Color color, Balon balon, int tipo) {
        super(x, y, ancho, alto);
        this.velocidad = velocidad;
        this.color = color;
        this.balon = balon;
        this.tipo = tipo;
    }

    public void setEquipo(NPC[] equipo) {
        this.equipo = equipo;
    }

    @Override
    public void mover() {

        int cx = x + ancho / 2;
        int cy = y + alto / 2;

        int bx = balon.getX() + balon.getAncho() / 2;
        int by = balon.getY() + balon.getAlto() / 2;

        // 🔴 DISTANCIA AL BALÓN
        double distBalon = distancia(cx, cy, bx, by);

        // 🔵 SOLO EL MÁS CERCANO VA AL BALÓN
        boolean soyElMasCercano = true;

        for (NPC otro : equipo) {
            if (otro != this) {
                int ox = otro.getX() + otro.getAncho() / 2;
                int oy = otro.getY() + otro.getAlto() / 2;

                if (distancia(ox, oy, bx, by) < distBalon) {
                    soyElMasCercano = false;
                    break;
                }
            }
        }

        double dx, dy;

        if (soyElMasCercano) {
            dx = bx - cx;
            dy = by - cy;
        } else {
            // 🔵 POSICIONES DINÁMICAS (NO FIJAS)
            int offsetY = (tipo - 1) * 120;

            int objetivoX = 550 + tipo * 50;
            int objetivoY = 250 + offsetY;

            dx = objetivoX - cx;
            dy = objetivoY - cy;
        }

        double dist = Math.sqrt(dx * dx + dy * dy);

        // 🔴 ANTI TEMBLOR
        if (dist < 2) return;

        dx /= dist;
        dy /= dist;

        // 🔴 SEPARACIÓN REAL
        double sepX = 0;
        double sepY = 0;

        for (NPC otro : equipo) {
            if (otro != this) {

                int ox = otro.getX();
                int oy = otro.getY();

                int dx2 = x - ox;
                int dy2 = y - oy;

                double d = Math.sqrt(dx2 * dx2 + dy2 * dy2);

                if (d < 60 && d > 0) {
                    sepX += dx2 / d;
                    sepY += dy2 / d;
                }
            }
        }

        // 🔥 MOVIMIENTO FINAL
        x += (int)((dx + sepX * 1.5) * velocidad);
        y += (int)((dy + sepY * 1.5) * velocidad);

        // 🔴 DISPARO REAL (NO ZIGZAG)
        if (distBalon < 35 && bx < cx) { // solo si balón está hacia la portería

            int porteriaX = 40;
            int porteriaY = 250;

            int dirX = porteriaX - bx;
            int dirY = porteriaY - by;

            double d = Math.sqrt(dirX * dirX + dirY * dirY);

            if (d > 0) {
                dirX /= d;
                dirY /= d;

                balon.setDireccion(dirX * 9, dirY * 9);
            }
        }
    }

    private double distancia(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, ancho, alto);
    }

    public void setBalon(Balon balon) {
        this.balon = balon;
    }
}