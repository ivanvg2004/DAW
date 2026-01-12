"use strict";

const producto = {
    nombre: 'Monitor 20 pulgadas',
    precio:  300,
    disponible: true
}

//Puede modificar el objeto pero no puede agregar
//ni eliminar propiedades
Object.seal( producto );

producto.disponible = false;
//producto.imagen = "imagen.jpg";

console.log(producto);

console.log(Object.isSealed(producto));
