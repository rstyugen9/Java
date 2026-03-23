import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class VentanaAlumno {

    public static void main(String[] args) {
        
        // Crear la ventana
        JFrame ventana = new JFrame("Datos del Alumno");
        ventana.setSize(400, 200);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear panel con layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        
        // Datos (puedes modificarlos)
        String nombre = "TuNombre";
        String apellido1 = "Apellido1";
        String apellido2 = "Apellido2";
        String grupo = "TuGrupo";
        String turno = "TuTurno";
        String anio = "2026";
        
        // Crear etiquetas
        JLabel lblNombre = new JLabel("Nombre Alumno: " + nombre + " " + apellido1 + " " + apellido2);
        JLabel lblGrupo = new JLabel("Grupo: " + grupo);
        JLabel lblTurno = new JLabel("Turno: " + turno);
        JLabel lblAnio = new JLabel("Año de ejecución: " + anio);
        
        // Agregar etiquetas al panel
        panel.add(lblNombre);
        panel.add(lblGrupo);
        panel.add(lblTurno);
        panel.add(lblAnio);
        
        // Agregar panel a la ventana
        ventana.add(panel);
        
        // Mostrar ventana
        ventana.setVisible(true);
    }
}