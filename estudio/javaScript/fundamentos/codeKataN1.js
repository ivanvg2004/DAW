function printGreet(){
    let copyright = "Ivan - OpenWebinars 2025"
    return copyright;
}

let result = printGreet();

console.log(result);  // "Ivan - OpenWebinars 2025"

result = function(name, year, formater){
    return formater(name, year);
}

let formater = function(name, year){
    return name + " | " + year;
}

console.log(result("Ivan", 2025, formater));  // "Ivan | 2025"

(function(nombre, año){
    console.log(nombre, año);  // "Ivan 2023"
})("Ivan", 2023);

