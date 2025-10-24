<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <table border = 1>
        <tr>
            <td>index</td>
            <td>nPrim</td>
        </tr>
    <?php
        $cont = 1;
        $nPrim = 1;
        while($nPrim <= 20){
            $esPrimer = true;
            for($i = $cont-1; $i > 1; $i--){
                if($cont % $i == 0){
                    $esPrimer = false;
                    break;
                }
            }
                if($esPrimer){
                    echo "
                        <tr>
                            <td>$nPrim</td>
                            <td>$cont</td>
                        </tr>";
                    $nPrim++;
                }
                $cont++;
            }
    ?>
    </table>

</body>
</html>