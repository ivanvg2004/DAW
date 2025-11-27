//Arrays
let namedCollection = ["Armando", "Alejandro", "David", "Alejandra"];

console.log(namedCollection);

console.log(namedCollection[3]);

//Set
let enumu = new Set([1, 7, true, "Hola"]);
    console.log(enumu)

    //Existe el valor en el set?
    console.log(enumu.has(7))

    //Añade un elemento
    enumu.add("Adios");
    console.log(enumu);

//Map
let mapeo = new Map([
    ["1", "Armando"],
    [2, "Alumno"]
])

console.log(mapeo)
    //Añadir un nuevo elemento. Nota: no se modifica
    //Armando ya que la clave de armando es de tipo
    //String y nosotros llamamos una numerica
    mapeo.set(1, "OpenWebinars")
    console.log(mapeo);

    //Acceder a un valor mediante la clave
    //que hemos inicializado
    console.log(mapeo.get(1));

//Objeto
let objeto = {
    name: "Armando",
    surname: "Torres"
}
console.log(objeto);

console.log(objeto.surname);

//Date
let currentDate = new Date();
console.log(currentDate)

let secondCurrentDate = new Date();
console.log(secondCurrentDate)