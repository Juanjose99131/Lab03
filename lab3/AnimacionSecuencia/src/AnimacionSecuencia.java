import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimacionSecuencia extends JPanel implements ActionListener {

    private Image[] imagenes; // Arreglo que contiene la secuencia de imágenes
    private Timer temporizador;
    private int indiceImagen = 0;
    private JSlider controlVelocidad;

    public AnimacionSecuencia() {
        // Número de imágenes en la animación
        int numFrames = 10; // Cambia este número dependiendo de cuántos cuadros tienes
        imagenes = new Image[numFrames];

        // Cargar cada imagen en el arreglo
        for (int i = 0; i < numFrames; i++) {
            // Suponiendo que las imágenes están nombradas como frame1.png, frame2.png, etc.
            imagenes[i] = new ImageIcon("C:/MisProyectos/Animacion/frames/frame" + (i + 1) + ".png").getImage();
        }

        // Crear un temporizador con un retraso inicial de 500 ms
        temporizador = new Timer(500, this);
        temporizador.start();

        // Crear un control deslizante para cambiar la velocidad de la animación
        controlVelocidad = new JSlider(JSlider.HORIZONTAL, 100, 2000, 500);
        controlVelocidad.setMajorTickSpacing(500);
        controlVelocidad.setPaintTicks(true);
        controlVelocidad.setPaintLabels(true);
        controlVelocidad.addChangeListener(e -> {
            int delay = controlVelocidad.getValue();
            temporizador.setDelay(delay); // Cambiar el retraso del temporizador
        });

        // Añadir el control deslizante al panel
        setLayout(new BorderLayout());
        add(controlVelocidad, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenes[indiceImagen] != null) {
            g.drawImage(imagenes[indiceImagen], 50, 50, this); // Dibuja la imagen actual
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        indiceImagen = (indiceImagen + 1) % imagenes.length; // Cambia de imagen
        repaint(); // Vuelve a dibujar el panel
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Animación de Secuencia");
        AnimacionSecuencia panelAnimacion = new AnimacionSecuencia();

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(400, 400);
        ventana.add(panelAnimacion);
        ventana.setVisible(true);
    }
}
