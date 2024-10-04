import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Animacion extends JPanel implements ActionListener {

    private BufferedImage[] imagenesDeSecuencia;
    private int indiceImagen = 0;
    private Timer temporizador;
    private JSlider sliderVelocidad;
    private JButton botonPausar;
    private boolean animacionPausada = false;

    public Animacion() {
        setLayout(new BorderLayout());

        // Cargar imágenes desde la carpeta correcta
        String[] nombresImagenes = {
            "contacto.png", "paso.png", "recorrido.png", "punto_alto.png",
            "contacto_2.png", "paso_2.png", "recorrido_2.png", "punto_alto_2.png", "contacto.png"
        };

        imagenesDeSecuencia = new BufferedImage[nombresImagenes.length];

        for (int i = 0; i < nombresImagenes.length; i++) {
            try {
                imagenesDeSecuencia[i] = ImageIO.read(new File("nombresImagenes/" + nombresImagenes[i]));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + nombresImagenes[i] + "\n" + e.toString());
                return;
            }
        }

        // Verificar que todas las imágenes se cargaron
        for (BufferedImage imagen : imagenesDeSecuencia) {
            if (imagen == null) {
                JOptionPane.showMessageDialog(this, "Error al cargar las imágenes");
                return;
            }
        }

        setPreferredSize(new Dimension(imagenesDeSecuencia[0].getWidth(), imagenesDeSecuencia[0].getHeight()));

        // Iniciar el temporizador con un delay de 100ms
        temporizador = new Timer(100, this);
        temporizador.start();

        // Configuración del slider para ajustar la velocidad
        sliderVelocidad = new JSlider(1, 5, 3); // 5 velocidades, con un valor inicial de 3 (velocidad media)
        sliderVelocidad.setMajorTickSpacing(1);
        sliderVelocidad.setPaintTicks(true);
        sliderVelocidad.setPaintLabels(true);

        sliderVelocidad.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int valorSlider = sliderVelocidad.getValue();
                int nuevoDelay;

                // Ajustar el delay basado en la selección del slider (de lento a rápido)
                switch (valorSlider) {
                    case 1: // Muy lento
                        nuevoDelay = 500;
                        break;
                    case 2: // Lento
                        nuevoDelay = 300;
                        break;
                    case 3: // Velocidad media
                        nuevoDelay = 150;
                        break;
                    case 4: // Rápido
                        nuevoDelay = 80;
                        break;
                    case 5: // Muy rápido
                        nuevoDelay = 40;
                        break;
                    default:
                        nuevoDelay = 150;
                }

                // Cambiar el delay del temporizador sin detenerlo
                temporizador.setDelay(nuevoDelay);
            }
        });

        // Botón para pausar y reanudar la animación
        botonPausar = new JButton("Pausar");
        botonPausar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (animacionPausada) {
                    temporizador.start();
                    botonPausar.setText("Pausar");
                } else {
                    temporizador.stop();
                    botonPausar.setText("Reanudar");
                }
                animacionPausada = !animacionPausada;
            }
        });

        // Panel de controles
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(sliderVelocidad, BorderLayout.CENTER);
        controlPanel.add(botonPausar, BorderLayout.EAST);

        add(controlPanel, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Centrar la imagen en el panel
        int x = (getWidth() - imagenesDeSecuencia[indiceImagen].getWidth()) / 2;
        int y = (getHeight() - imagenesDeSecuencia[indiceImagen].getHeight()) / 2;
        g.drawImage(imagenesDeSecuencia[indiceImagen], x, y, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Cambiar al siguiente cuadro de la animación
        indiceImagen = (indiceImagen + 1) % imagenesDeSecuencia.length;
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Animación");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new Animacion());
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
