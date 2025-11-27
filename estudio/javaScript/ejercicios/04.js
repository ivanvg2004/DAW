function impares(num){
    let lista;
    for(let i = 0; i < num; i++){
        if(i % 2 != 0){
            console.log(i+ " es impar")
        }
    }
    return lista
}

let num = 10;
impares(num)