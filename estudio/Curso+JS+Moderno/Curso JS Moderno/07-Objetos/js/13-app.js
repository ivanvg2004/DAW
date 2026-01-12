//Object Literal
const producto = {
    nombre: 'Monitor 20 pulgadas',
    precio:  300,
    disponible: true,
}

//Devuelve nombre propiedades
console.log(Object.keys (producto))

//devuelve valores de propiedades
console.log(Object.values(producto));

//Devuelve todo
console.log(Object.entries(producto));