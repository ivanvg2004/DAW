//this: busca propiedad dentro de ese mismo objeto.
const producto = {
    nombre: 'Monitor 20 pulgadas',
    precio:  300,
    disponible: true,
    mostrarInfo: function() {
        console.log(`El Producto ${this.nombre} tiene un precio de ${this.precio}`)
    }
}
producto.mostrarInfo();