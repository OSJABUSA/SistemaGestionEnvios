package com.logiexpress.model;

public class EnvioAereo {

}
/**
 * Representa un envío aéreo dentro del sistema LogiExpress.
 * Puede ser nacional o internacional, y se caracteriza por su rapidez.
 *
 * Reglas de negocio:
 * - Costo base: $15.000 por kilogramo
 * - Recargo internacional: +$50.000 fijo
 * - Recargo prioridad EXPRESS: +80% del costo total
 * - Tiempo nacional: entre 1 y 3 días
 * - Tiempo internacional: entre 3 y 7 días
 * - Peso máximo permitido: 1.000 kg
 */
public class EnvioAereo extends Envio {

    private boolean esInternacional;

    private static final double COSTO_BASE_POR_KG = 15000.0;
    private static final double RECARGO_INTERNACIONAL = 50000.0;
    private static final double RECARGO_EXPRESS = 0.8;
    private static final double PESO_MAXIMO = 1000.0;

    /**
     * Constructor para crear un envío aéreo.
     *
     * @param origen         Ciudad o país de origen
     * @param destino        Ciudad o país de destino
     * @param peso           Peso del envío (kg)
     * @param prioridad      Prioridad del envío (NORMAL o EXPRESS)
     * @param esInternacional Indica si el envío es internacional
     * @throws IllegalArgumentException si el peso es inválido o supera el máximo permitido
     */
    public EnvioAereo(String origen, String destino, double peso, Prioridad prioridad, boolean esInternacional) {
        super(origen, destino, peso, prioridad, TipoEnvio.AEREO);

        if (peso <= 0)
            throw new IllegalArgumentException("El peso debe ser mayor a 0 kg.");
        if (peso > PESO_MAXIMO)
            throw new IllegalArgumentException("El peso máximo permitido para envíos aéreos es de 1.000 kg.");

        this.esInternacional = esInternacional;
    }

    /**
     * Calcula el costo total del envío aéreo según las reglas de negocio.
     *
     * @return costo total del envío (COP)
     */
    @Override
    public double calcularCosto() {
        double costo = getPeso() * COSTO_BASE_POR_KG;

        if (esInternacional) {
            costo += RECARGO_INTERNACIONAL;
        }

        if (getPrioridad() == Prioridad.EXPRESS) {
            costo *= (1 + RECARGO_EXPRESS); // Recargo 80%
        }

        return costo;
    }

    /**
     * Calcula el tiempo estimado de entrega (en días).
     * - Nacional: entre 1 y 3 días
     * - Internacional: entre 3 y 7 días
     *
     * @return tiempo estimado promedio
     */
    @Override
    public int calcularTiempoEntrega() {
        if (esInternacional) {
            return 5; // promedio entre 3 y 7 días
        } else {
            return 2; // promedio entre 1 y 3 días
        }
    }

    /**
     * Retorna los detalles específicos del envío aéreo.
     *
     * @return Cadena con detalles del envío
     */
    @Override
    public String obtenerDetallesEspecificos() {
        return String.format(
                "Modalidad: AÉREO | %s | Costo: $%,.2f | Tiempo estimado: %d día(s)",
                (esInternacional ? "Internacional" : "Nacional"),
                calcularCosto(),
                calcularTiempoEntrega()
        );
    }

    // Getters y Setters
    public boolean isEsInternacional() {
        return esInternacional;
    }

    public void setEsInternacional(boolean esInternacional) {
        this.esInternacional = esInternacional;
    }

    @Override
    public String toString() {
        return super.toString()