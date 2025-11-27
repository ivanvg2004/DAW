<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Resultado:</h1>
    <?php
        $a = $_GET["a"];
        $b = $_GET["b"];
        $suma = $a + $b;
        echo "<h2>La suma es: $suma</h2>"
    ?>
</body>
</html>