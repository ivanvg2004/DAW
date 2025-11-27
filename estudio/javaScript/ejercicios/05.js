let array = [2,5,7,15,-5,-100,-55];

function getMenorMayor(arr){
    let mayor = arr[0];
    let menor = arr[0];
    for(i of arr){
        mayor = mayor > i ? mayor : i;
        menor = menor < i ? menor : i;
    }
    return [menor, mayor];
}

let numeros = getMenorMayor(array)
console.log(numeros);