//Objeto literal
const producto = {
    nombre: 'Monitor 20 pulgadas',
    precio: 300,
    disponible: true,
    informacion: {
        medidas: {
            peso: '1kg',
            medida: '1m'
        },
        fabricacion: {
            pais: 'China'
        }
    },
}

//Destructuring anidado, sacamos nombre y pais
const{ nombre, informacion: { fabricacion: { pais } } } = producto;

console.log(nombre);
console.log(pais);
console.log(informacion)//No existe ya que solo crea variable informacion

