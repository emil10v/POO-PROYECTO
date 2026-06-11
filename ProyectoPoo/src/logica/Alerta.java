package logica;

import java.time.LocalDateTime;

public class Alerta {
    private Prestamo prestamo;
    private String mensaje;
    private boolean recurrente;
    private int frecuenciaMinutos;
    private LocalDateTime fechaProximaEjecucion;
    private boolean activa;

    public Alerta(boolean recurrente, int frecuenciaMinutos, Prestamo prestamo) {
        this.prestamo = prestamo;
        this.recurrente = recurrente;
        this.frecuenciaMinutos = frecuenciaMinutos;
        this.fechaProximaEjecucion = prestamo.getFechaPrestamo().plusMinutes(frecuenciaMinutos);
        this.activa = true;
    }

    
    public String mostrar() {
        if (!activa) 
            return null;
        if (LocalDateTime.now().isAfter(fechaProximaEjecucion)) {
            if (recurrente) {
                fechaProximaEjecucion = fechaProximaEjecucion.plusMinutes(frecuenciaMinutos);
            } else {
                activa = false;
            }
            Persona persona = prestamo.getPersona();
        	String mensaje = persona.getNombre() + " tiene prestados los items: \n";
            for (Item i : prestamo.getItems() ) {
            	mensaje += i.getNombre() + "\n";
            }
            return mensaje;
        }
        return null;
    }

    public void activar() {
        activa = true;
    }

    public void desactivar() {
        activa = false;
    }

    public boolean isActiva() {
        return activa;
    }

}