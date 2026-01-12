//Objeto literal
const producto = {
    nombre: 'Monitor 20 pulgadas',
    precio:  300,
    disponible: true
}

//Se pueden reescribir las propiedades de un const
producto.disponible = false;
console.log(producto);