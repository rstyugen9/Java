import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelJuego extends JPanel {

    private final int ANCHO = 640;
    private final int ALTO = 480;

    private Jugador jugador; // jugador principal
    private Jugador npc;     // segundo jugador (NPC)

    public PanelJuego() {

        // ✔ Tamaño y color del jugador principal
        jugador = new Jugador(100, 100, 50, 50, 5, Color.BLUE);

        // ✔ NPC en otra posición
        npc = new Jugador(400, 150, 50, 50, 0, Color.RED);

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:    jugador.mover(0, -jugador.getVelocidad()); break;
                    case KeyEvent.VK_DOWN:  jugador.mover(0,  jugador.getVelocidad()); break;
                    case KeyEvent.VK_LEFT:  jugador.mover(-jugador.getVelocidad(), 0); break;
                    case KeyEvent.VK_RIGHT: jugador.mover( jugador.getVelocidad(), 0); break;
                }

                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // =========================
        // ✔ CANCHA (gráficos vectoriales)
        // =========================
        g2.setColor(new Color(0, 120, 0));
        g2.fillRect(0, 0, ANCHO, ALTO);

        g2.setColor(Color.WHITE);

        // Bordes
        g2.drawRect(20, 20, ANCHO - 40, ALTO - 40);

        // Línea central
        g2.drawLine(ANCHO / 2, 20, ANCHO / 2, ALTO - 20);

        // Círculo central
        g2.drawOval(ANCHO / 2 - 40, ALTO / 2 - 40, 80, 80);

        // =========================
        // ✔ DIBUJAR JUGADORES
        // =========================
        jugador.dibujar(g2);
        npc.dibujar(g2);

        // =========================
        // ✔ TEXTO COORDENADAS
        // =========================
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));

        g2.drawString(
            "Coordenadas: X=" + jugador.getX() + " Y=" + jugador.getY(),
            10,
            20
        );
    }
}