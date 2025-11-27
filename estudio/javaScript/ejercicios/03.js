// dado un indice analiza un array de numeros
//devolviendo el numero de posicion del array

function getByIdx(arr, idx){
    if(idx >= arr.length || idx < 0){
        return "Fuera de rango"
    }
    return arr[idx]
}

let resultado = getByIdx([1, 319312], -1)
console.log(resultado)