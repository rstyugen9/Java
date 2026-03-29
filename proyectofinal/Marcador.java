public class Marcador {
    private int golesJugador;
    private int golesNPC;

    public void golJugador() {
        golesJugador++;
    }

    public void golNPC() {
        golesNPC++;
    }

    public int getGolesJugador() {
        return golesJugador;
    }

    public int getGolesNPC() {
        return golesNPC;
    }

    public void reiniciar() {
        golesJugador = 0;
        golesNPC = 0;
    }
}