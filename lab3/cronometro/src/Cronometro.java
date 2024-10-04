import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Cronometro {
    
    static int segundosTotales = 0; // Almacena el tiempo transcurrido en segundos
    static Timer timer = new Timer();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Configurar la alarma
        System.out.println("Ingrese el tiempo en minutos para la alarma:");
        int minutosAlarma = scanner.nextInt();
        int segundosAlarma = minutosAlarma * 60; // Convertir minutos a segundos

        // Iniciar el cronómetro
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                segundosTotales++;
                mostrarTiempo();
                
                // Verificar si se debe activar la alarma
                if (segundosTotales == segundosAlarma) {
                    activarAlarma();
                }
            }
        }, 0, 1000); // Ejecuta cada segundo
        
        System.out.println("Cronómetro iniciado. Se activará la alarma después de " + minutosAlarma + " minutos.");
    }

    // Método para mostrar el tiempo en formato HH:mm:ss
    public static void mostrarTiempo() {
        int horas = segundosTotales / 3600;
        int minutos = (segundosTotales % 3600) / 60;
        int segundos = segundosTotales % 60;
        
        System.out.printf("%02d:%02d:%02d\n", horas, minutos, segundos);
    }
    
    // Método para activar la alarma
    public static void activarAlarma() {
        Timer alarmaTimer = new Timer();
        alarmaTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Alarma que se ejecuta cada 10 segundos
                System.out.println("¡Alarma activada! Sonando cada 10 segundos.");
                JOptionPane.showMessageDialog(null, "¡Alarma! Tiempo transcurrido.");
            }
        }, 0, 10000); // Se ejecuta cada 10 segundos
    }
}
