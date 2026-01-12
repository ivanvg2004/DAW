const producto = 'Monitor 20 pulgadas';

//.replace() para reemplazar
console.log(producto);
console.log(producto.replace('pulgadas', '"'));

//.slice() para cortar
console.log(producto.slice(0, 10))
console.log(producto.slice(8))//corta de 8 a adelante

//alternativa a slice
console.log(producto.substring(0, 10))

const usuario = "Juan";
console.log(usuario.substring(0, 1));
console.log(usuario.charAt(0))