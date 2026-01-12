const producto = 'Monitor 20 pulgadas';

//.repeat te va a permitir repetir una cadena de texto
//si le pasas un numero decimal (2,3) se redondea
const texto = ' en Promoción'. repeat(3);

console.log(texto)
console.log(`${producto} ${texto} !!!`)

//.split(), divir un string
const actividad = "Estoy aprendiendo JavaScript Moderno"
console.log(actividad.split(" "));

const hobbies = 'Leer, caminar, escuchar musica, escribit, aprender a programar';
console.log(hobbies.split(", "))

const tweet = "Aprendiendo JavaScript #JsModernoConJuan"
console.log(tweet.split('#'));