package com.logiexpress.model;

import com.logiexpress.enums.Prioridad;
import com.logiexpress.enums.TipoEnvio;

/**
 * Representa un envío marítimo.
 * Extiende de Envio e implementa las reglas de negocio específicas.
 * Tarea del Integrante 3 (Especialista en Envíos Aéreos y Marítimos).
 */
public class EnvioMaritimo extends Envio {

    // Atributo específico (-) como en el diagrama UML
    private String puertoDestino;

    /**
     * Constructor para EnvioMaritimo.
     * Nota: La prioridad siempre se establece en NORMAL según las reglas.
     *
     * @param origen        Lugar de origen (usualmente un puerto).
     * @param destino       País/Ciudad de destino.
     * @param peso          Peso en kg.
     * @param puertoDestino Nombre del puerto de destino.
     */
    public EnvioMaritimo(String origen, String destino, double peso, String puertoDestino) {
        
        // Regla de negocio: Prioridad siempre NORMAL (no aplica express)
        // Regla de negocio: Se asume que Marítimo siempre es internacional.
        // Se llama al constructor de la clase base (Envio)
        super(origen, destino, peso, Prioridad.NORMAL, TipoEnvio.MARITIMO);
        
        // Validación específica
        if (puertoDestino == null || puertoDestino.trim().isEmpty()) {
            throw new IllegalArgumentException("El puerto de destino no puede estar vacío.");
        }
        
        // Asignación del atributo específico
        this.puertoDestino = puertoDestino;
    }

    /**
     * Calcula el costo de un envío marítimo.
     * Reglas:
     * 1. Costo base: $2.000 por kg.
     * 2. Recargo contenedor: +$150.000 si el peso supera los 1.000 kg.
     */
    @Override
    public double calcularCosto() {
        // Regla 1: $2.000 por kilogramo
        double costoTotal = this.peso * 2000;

        // Regla 2: Recargo contenedor: +$150.000 si peso > 1.000 kg
        if (this.peso > 1000) {
            costoTotal += 150000;
        }

        return costoTotal;
    }

    /**
     * Calcula el tiempo de entrega de un envío marítimo.
     * Regla:
     * 1. Tiempo estimado: 15-45 días (se usa el promedio de 30 días).
     */
    @Override
    public int calcularTiempoEntrega() {
        // Regla: Tiempo estimado: 15-45 días (usamos promedio de 30 días)
        return 30;
    }

    /**
     * Retorna los detalles específicos del envío marítimo.
     */
    @Override
    public String obtenerDetallesEspecificos() {
        return String.format("Puerto Destino: %s", this.puertoDestino);
    }

    // --- Getters y Setters específicos ---

    public String getPuertoDestino() {
        return puertoDestino;
    }

    public void setPuertoDestino(String puertoDestino) {
        if (puertoDestino == null || puertoDestino.trim().isEmpty()) {
            throw new IllegalArgumentException("El puerto de destino no puede estar vacío.");
        }
        this.puertoDestino = puertoDestino;
    }
}
