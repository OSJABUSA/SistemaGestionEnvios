package com.logiexpress.model;

// Se asume que las enumeraciones están en este paquete
// (o en com.logiexpress.enums según el prompt anterior)
import com.logiexpress.enums.EstadoEnvio;
import com.logiexpress.enums.Prioridad;
import com.logiexpress.enums.TipoEnvio;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Clase abstracta (como indica el diagrama) que representa un Envío genérico.
 * Define la estructura base para EnvioTerrestre, EnvioAereo y EnvioMaritimo.
 */
public abstract class Envio {

    // --- Atributos Protegidos (#) ---
    protected String id;
    protected String origen;
    protected String destino;
    protected double peso; // en kilogramos
    protected Prioridad prioridad;
    protected EstadoEnvio estado;
    protected LocalDate fechaEnvio;
    protected TipoEnvio tipoEnvio;

    /**
     * Constructor para la clase Envio.
     * Inicializa los atributos comunes.
     */
    public Envio(String origen, String destino, double peso, Prioridad prioridad, TipoEnvio tipoEnvio) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0 kg.");
        }
        // El método generarNumeroSeguimiento() se usa para inicializar el ID
        this.id = this.generarNumeroSeguimiento(); 
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.prioridad = prioridad;
        this.tipoEnvio = tipoEnvio;
        this.estado = EstadoEnvio.PENDIENTE; // Estado inicial por defecto
        this.fechaEnvio = LocalDate.now();
    }

    // --- Métodos Abstractos (Polimorfismo) ---
    // Estos métodos DEBEN ser implementados por las clases hijas (EnvioTerrestre, etc.)

    /**
     * Calcula el costo del envío (método abstracto).
     * @return El costo total del envío.
     */
    public abstract double calcularCosto();

    /**
     * Calcula el tiempo de entrega en días (método abstracto).
     * @return El número de días estimados.
     */
    public abstract int calcularTiempoEntrega();

    /**
     * Obtiene detalles específicos de la subclase (método abstracto).
     * @return Un String con detalles (ej. distancia, puerto, etc.).
     */
    public abstract String obtenerDetallesEspecificos();


    // --- Métodos Concretos (Comportamiento común) ---

    /**
     * Genera un ID único de seguimiento para el envío.
     * El diagrama lo marca como (+ public), pero por encapsulamiento
     * es mejor práctica definirlo como 'private' y llamarlo solo desde
     * el constructor, como se hizo aquí.
     * Si se requiere 'public' estrictamente, solo cambie 'private' por 'public'.
     *
     * @return Un String con el ID (ej. "LE-A8B3F1").
     */
    private String generarNumeroSeguimiento() {
        // Genera un ID único basado en UUID, corto y en mayúsculas
        String uuid = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "LE-" + uuid;
    }

    /**
     * Actualiza el estado actual del envío.
     *
     * @param nuevoEstado El nuevo estado (PENDIENTE, EN_TRANSITO, ENTREGADO, etc.).
     */
    public void actualizarEstado(EstadoEnvio nuevoEstado) {
        this.estado = nuevoEstado;
        System.out.println("Envío " + this.id + " actualizado a: " + nuevoEstado);
    }

    // --- Getters y Setters (Necesarios para que la clase funcione) ---
    // Aunque no están explícitos en el diagrama principal, son necesarios
    // para acceder y modificar los atributos protegidos.

    public String getId() {
        return id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0 kg.");
        }
        this.peso = peso;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public EstadoEnvio getEstado() {
        return estado;
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public TipoEnvio getTipoEnvio() {
        return tipoEnvio;
    }

    /**
     * Sobrescritura del método toString() para mostrar la información
     * del envío de forma polimórfica.
     */
    @Override
    public String toString() {
        return String.format(
            "--- Envío [%s] (%s) ---%n" +
            "  ID: %s%n" +
            "  Origen: %s | Destino: %s%n" +
            "  Fecha: %s | Estado: %s%n" +
            "  Peso: %.2f kg | Prioridad: %s%n" +
            "  Detalles (%s): %s%n" + // Llama a obtenerDetallesEspecificos()
            "  Costo Estimado: $%,.2f%n" + // Llama a calcularCosto()
            "  Tiempo Estimado: %d días%n", // Llama a calcularTiempoEntrega()
            this.tipoEnvio, this.getClass().getSimpleName(), this.id, this.origen, this.destino,
            this.fechaEnvio, this.estado, this.peso, this.prioridad,
            this.tipoEnvio, this.obtenerDetallesEspecificos(), 
            this.calcularCosto(),
            this.calcularTiempoEntrega()
        );
    }
}
