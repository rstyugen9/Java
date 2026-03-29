import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelJuego extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    private Jugador jugador;
    private Balon balon;
    private NPC[] npcs;
    private Porteria porteriaIzquierda;
    private Porteria porteriaDerecha;
    private Marcador marcador;
    private EstadoJuego estado;

    private final int MARGEN_CANCHA = 50;

    private int cooldownJugador = 0;
    private int[] cooldownNPC;

    public PanelJuego() {
        setFocusable(true);
        addKeyListener(this);

        inicializarJuego();

        timer = new Timer(16, this);
        timer.start();
    }

private void inicializarJuego() {

    estado = EstadoJuego.INICIO;

    jugador = new Jugador(100, 250, 40, 40, 5, Color.BLUE);
    balon = new Balon(430, 280, 20, 20);

    if (marcador == null) {
        marcador = new Marcador();
    }

    porteriaIzquierda = new Porteria(50, 220, 40, 120, Color.WHITE);
    porteriaDerecha = new Porteria(790, 220, 40, 120, Color.WHITE);

    npcs = new NPC[4];

    npcs[0] = new NPC(650, 100, 40, 40, 3, Color.RED, balon, 0);
    npcs[1] = new NPC(700, 200, 40, 40, 3, Color.RED, balon, 1);
    npcs[2] = new NPC(650, 320, 40, 40, 4, Color.RED, balon, 2);
    npcs[3] = new NPC(550, 420, 40, 40, 4, Color.RED, balon, 2);

    for (NPC npc : npcs) {
        npc.setEquipo(npcs);
    }

    cooldownNPC = new int[npcs.length];
    cooldownJugador = 0;
}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 130, 0));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.drawRect(50, 50, 780, 480);
        g.drawLine(440, 50, 440, 530);
        g.drawOval(390, 240, 100, 100);

        porteriaIzquierda.dibujar(g);
        porteriaDerecha.dibujar(g);

        jugador.dibujar(g);
        balon.dibujar(g);

        for (NPC npc : npcs) {
            npc.dibujar(g);
        }

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.RED);

        g.drawString("Jugador: " + marcador.getGolesJugador(), 80, 30);
        g.drawString("NPC: " + marcador.getGolesNPC(), 700, 30);

        if (estado == EstadoJuego.INICIO) {
            g.drawString("Presiona ENTER para iniciar", 300, 280);
        } else if (estado == EstadoJuego.VICTORIA) {
            g.drawString("¡GANASTE!", 250, 280);
        } else if (estado == EstadoJuego.DERROTA) {
            g.drawString("PERDISTE", 250, 280);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (estado == EstadoJuego.JUGANDO) {

            actualizarCooldowns();

            jugador.mover();
            limitarJugador();

            for (NPC npc : npcs) {
                npc.mover();
            }

            detectarColisiones();
            balon.mover();

            limitarBalonDentroCancha();
            detectarGoles();
        }

        repaint();
    }

    private void actualizarCooldowns() {
        if (cooldownJugador > 0) cooldownJugador--;

        for (int i = 0; i < cooldownNPC.length; i++) {
            if (cooldownNPC[i] > 0) cooldownNPC[i]--;
        }
    }

    private void detectarColisiones() {

        if (cooldownJugador == 0 && jugador.getBounds().intersects(balon.getBounds())) {
            golpear(jugador.getX(), jugador.getY(), jugador.getAncho(), jugador.getAlto(), 9);
            separar(jugador.getX(), jugador.getY(), jugador.getAncho(), jugador.getAlto(), 10);
            cooldownJugador = 8;
        }

        for (int i = 0; i < npcs.length; i++) {

            NPC npc = npcs[i];

            if (cooldownNPC[i] == 0 && npc.getBounds().intersects(balon.getBounds())) {
                golpear(npc.getX(), npc.getY(), npc.getAncho(), npc.getAlto(), 7);
                separar(npc.getX(), npc.getY(), npc.getAncho(), npc.getAlto(), 12);
                cooldownNPC[i] = 12;
            }
        }
    }

    private void golpear(int ex, int ey, int ancho, int alto, double fuerza) {

        int cxBalon = balon.getX() + balon.getAncho() / 2;
        int cyBalon = balon.getY() + balon.getAlto() / 2;

        int cxEntidad = ex + ancho / 2;
        int cyEntidad = ey + alto / 2;

        int dx = cxBalon - cxEntidad;
        int dy = cyBalon - cyEntidad;

        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist == 0) {
            dx = 1;
            dy = 0;
            dist = 1;
        }

        balon.setDireccion((dx / dist) * fuerza, (dy / dist) * fuerza);
    }

    private void separar(int ex, int ey, int ancho, int alto, int fuerza) {

        int cxBalon = balon.getX() + balon.getAncho() / 2;
        int cyBalon = balon.getY() + balon.getAlto() / 2;

        int cxEntidad = ex + ancho / 2;
        int cyEntidad = ey + alto / 2;

        int dx = cxBalon - cxEntidad;
        int dy = cyBalon - cyEntidad;

        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist == 0) {
            dx = 1;
            dy = 0;
            dist = 1;
        }

        balon.setX((int)(balon.getX() + (dx / dist) * fuerza));
        balon.setY((int)(balon.getY() + (dy / dist) * fuerza));
    }

    private void limitarJugador() {

        if (jugador.getX() < MARGEN_CANCHA) jugador.x = MARGEN_CANCHA;
        if (jugador.getX() + jugador.getAncho() > getWidth() - MARGEN_CANCHA)
            jugador.x = getWidth() - MARGEN_CANCHA - jugador.getAncho();

        if (jugador.getY() < MARGEN_CANCHA) jugador.y = MARGEN_CANCHA;
        if (jugador.getY() + jugador.getAlto() > getHeight() - MARGEN_CANCHA)
            jugador.y = getHeight() - MARGEN_CANCHA - jugador.getAlto();
    }

    private void limitarBalonDentroCancha() {

        int margen = 50;

        boolean enIzq = balon.getBounds().intersects(porteriaIzquierda.getArea());
        boolean enDer = balon.getBounds().intersects(porteriaDerecha.getArea());

        if (!enIzq && !enDer) {

            if (balon.getX() <= margen) {
                balon.setX(margen);
                balon.rebotarHorizontal();
            }

            if (balon.getX() + balon.getAncho() >= getWidth() - margen) {
                balon.setX(getWidth() - margen - balon.getAncho());
                balon.rebotarHorizontal();
            }
        }

        if (balon.getY() <= margen) {
            balon.setY(margen);
            balon.rebotarVertical();
        }

        if (balon.getY() + balon.getAlto() >= getHeight() - margen) {
            balon.setY(getHeight() - margen - balon.getAlto());
            balon.rebotarVertical();
        }
    }

    private void detectarGoles() {

        if (balon.getBounds().intersects(porteriaIzquierda.getArea())) {
            marcador.golNPC();
            reiniciarPosiciones();
            return;
        }

        if (balon.getBounds().intersects(porteriaDerecha.getArea())) {
            marcador.golJugador();
            reiniciarPosiciones();
            return;
        }

        if (marcador.getGolesJugador() >= 3) estado = EstadoJuego.VICTORIA;
        if (marcador.getGolesNPC() >= 3) estado = EstadoJuego.DERROTA;
    }

    private void reiniciarPosiciones() {
        inicializarJuego();
        estado = EstadoJuego.JUGANDO;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (estado == EstadoJuego.INICIO && e.getKeyCode() == KeyEvent.VK_ENTER) {
            estado = EstadoJuego.JUGANDO;
        }

        if ((estado == EstadoJuego.VICTORIA || estado == EstadoJuego.DERROTA)
                && e.getKeyCode() == KeyEvent.VK_R) {
            reiniciarPosiciones();
        }

        if (estado == EstadoJuego.JUGANDO) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> jugador.setDireccion(0, -1);
                case KeyEvent.VK_DOWN -> jugador.setDireccion(0, 1);
                case KeyEvent.VK_LEFT -> jugador.setDireccion(-1, 0);
                case KeyEvent.VK_RIGHT -> jugador.setDireccion(1, 0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        jugador.setDireccion(0, 0);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}