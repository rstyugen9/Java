import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelJuego extends JPanel {

    private final int ANCHO = 640;
    private final int ALTO = 480;

    private Jugador jugador;
    private Jugador npc;

    private Trofeo[] trofeos;
    private int contador = 0;
    private final int TOTAL_TROFEOS = 5;

    private boolean victoria = false;

    public PanelJuego() {

        jugador = new Jugador(100, 100, 50, 50, 5, Color.BLUE);
        npc = new Jugador(400, 150, 50, 50, 0, Color.RED);

        // Crear trofeos
        trofeos = new Trofeo[TOTAL_TROFEOS];
        for (int i = 0; i < TOTAL_TROFEOS; i++) {
            trofeos[i] = new Trofeo(ANCHO, ALTO);
        }

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (victoria) return; // no mover si ya ganó

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: jugador.mover(0, -jugador.getVelocidad()); break;
                    case KeyEvent.VK_DOWN: jugador.mover(0, jugador.getVelocidad()); break;
                    case KeyEvent.VK_LEFT: jugador.mover(-jugador.getVelocidad(), 0); break;
                    case KeyEvent.VK_RIGHT: jugador.mover(jugador.getVelocidad(), 0); break;
                }

                verificarColisiones();
                repaint();
            }
        });
    }

    private void verificarColisiones() {
        Rectangle jugadorRect = new Rectangle(
                jugador.getX(),
                jugador.getY(),
                50,
                50
        );

        for (int i = 0; i < trofeos.length; i++) {
            if (trofeos[i] != null) {
                if (jugadorRect.intersects(trofeos[i].getBounds())) {
                    trofeos[i] = null; // eliminar trofeo
                    contador++;
                }
            }
        }

        // Condición de victoria
        if (contador == TOTAL_TROFEOS) {
            victoria = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // CANCHA
        g2.setColor(new Color(0, 120, 0));
        g2.fillRect(0, 0, ANCHO, ALTO);

        g2.setColor(Color.WHITE);
        g2.drawRect(20, 20, ANCHO - 40, ALTO - 40);
        g2.drawLine(ANCHO / 2, 20, ANCHO / 2, ALTO - 20);
        g2.drawOval(ANCHO / 2 - 40, ALTO / 2 - 40, 80, 80);

        // Jugadores
        jugador.dibujar(g2);
        npc.dibujar(g2);

        // Dibujar trofeos
        for (Trofeo t : trofeos) {
            if (t != null) {
                t.dibujar(g2);
            }
        }

        // Contador
        g2.setColor(Color.WHITE);
        g2.drawString("Trofeos: " + contador + "/" + TOTAL_TROFEOS, 10, 20);

        // Coordenadas
        g2.drawString("X: " + jugador.getX() + " Y: " + jugador.getY(), 10, 40);

        // Pantalla de victoria
        if (victoria) {
            g2.setFont(new Font("Arial", Font.BOLD, 30));
            g2.setColor(Color.YELLOW);
            g2.drawString("¡GANASTE!", ANCHO / 2 - 100, ALTO / 2);
        }
    }
}