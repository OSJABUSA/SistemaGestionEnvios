package com.logiexpress.model;

import com.logiexpress.enums.Prioridad;
import com.logiexpress.enums.TipoEnvio;

/**
 * Representa un envío terrestre.
 * Extiende de Envio e implementa las reglas de negocio específicas.
 * Tarea del Integrante 2 (Especialista en Envíos Terrestres).
 */
public class EnvioTerrestre extends Envio {

    // Atributo específico (-) como en el diagrama UML
    private double distanciaKm;
    
    // Constante para validación
    private static final double PESO_MAXIMO_KG = 5000.0;

    /**
     * Constructor para EnvioTerrestre.
     *
     * @param origen     Lugar de origen.
     * @param destino    Lugar de destino.
     * @param peso       Peso en kg.
     * @param prioridad  Prioridad (NORMAL, EXPRESS).
     * @param distanciaKm Distancia del recorrido en kilómetros.
     */
    public EnvioTerrestre(String origen, String destino, double peso, Prioridad prioridad, double distanciaKm) {
        // 1. Llama al constructor de la clase base (Envio)
        // Se define el TipoEnvio como TERRESTRE automáticamente
        super(origen, destino, peso, prioridad, TipoEnvio.TERRESTRE);

        // 2. Validaciones específicas de esta clase
        if (distanciaKm <= 0) {
            throw new IllegalArgumentException("La distancia debe ser mayor a 0 km.");
        }
        if (peso > PESO_MAXIMO_KG) {
            throw new IllegalArgumentException("El peso excede el límite de " + PESO_MAXIMO_KG + " kg para envíos terrestres.");
        }
        
        // 3. Asigna el atributo específico
        this.distanciaKm = distanciaKm;
    }

    /**
     * Calcula el costo de un envío terrestre.
     * Reglas:
     * 1. Costo base: $5.000 por kg
     * 2. Costo distancia: $500 por cada 100 km
     * 3. Recargo express: +50% al total
     */
    @Override
    public double calcularCosto() {
        // Regla 1: $5.000 por kilogramo
        double costoBase = this.peso * 5000;

        // Regla 2: $500 por cada 100 km
        // Se usa (distancia / 100.0) para calcular la cantidad de "bloques de 100km"
        double costoDistancia = (this.distanciaKm / 100.0) * 500.0;

        double costoTotal = costoBase + costoDistancia;

        // Regla 3: Recargo prioridad express: +50%
        if (this.prioridad == Prioridad.EXPRESS) {
            costoTotal *= 1.50; // Aumenta en un 50%
        }

        return costoTotal;
    }

    /**
     * Calcula el tiempo de entrega de un envío terrestre.
     * Reglas:
     * 1. 1 día por cada 500 km
     * 2. Mínimo 1 día
     */
    @Override
    public int calcularTiempoEntrega() {
        // Regla 1: 1 día por cada 500 km
        double diasCalculados = this.distanciaKm / 500.0;

        // Se redondea hacia arriba (ej. 1.2 días -> 2 días)
        int diasRedondeados = (int) Math.ceil(diasCalculados);

        // Regla 2: mínimo 1 día
        return Math.max(1, diasRedondeados);
    }

    /**
     * Retorna los detalles específicos del envío terrestre.
     */
    @Override
    public String obtenerDetallesEspecificos() {
        return String.format("Distancia: %.2f km", this.distanciaKm);
    }

    // --- Getters y Setters específicos ---

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        if (distanciaKm <= 0) {
            throw new IllegalArgumentException("La distancia debe ser mayor a 0 km.");
        }
        this.distanciaKm = distanciaKm;
    }
}
