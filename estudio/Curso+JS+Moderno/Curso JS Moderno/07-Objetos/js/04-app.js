//Objeto literal
const producto = {
    nombre: 'Monitor 20 pulgadas',
    precio:  300,
    disponible: true
}

//const nombre = producto.nombre;

//Destructuring: extraer propiedades de producto
//a variables literales
//Individualmente
    //const { nombre } = producto;
    //const { precio } = producto;
//Grupal
const {nombre, precio, disponible} = producto
console.log(nombre);
console.log(precio);
