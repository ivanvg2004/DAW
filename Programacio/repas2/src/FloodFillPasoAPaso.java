import java.util.Scanner;

public class FloodFillPasoAPaso {

    public static void main(String[] args) {
        int[][] matriz = {
                {1,1,1,1,1,1,1,1,1,1,1,1},
                {1,0,0,0,1,1,0,0,0,0,1,1},
                {1,0,1,0,1,1,0,1,1,0,0,1},
                {1,0,0,0,1,1,0,0,1,0,0,1},
                {1,1,1,0,1,1,1,0,1,1,0,1},
                {1,0,0,0,0,0,1,0,0,0,0,1},
                {1,0,1,1,1,0,1,1,1,1,0,1},
                {1,0,0,0,1,0,0,0,0,1,0,1},
                {1,1,1,1,1,1,1,1,1,1,1,1}
        };

        Scanner sc = new Scanner(System.in);

        // Pedimos coordenadas al usuario
        System.out.print("Introduce la fila inicial: ");
        int filaInicial = sc.nextInt();

        System.out.print("Introduce la columna inicial: ");
        int columnaInicial = sc.nextInt();

        System.out.println("\nMatriz inicial:");
        imprimirMatriz(matriz);

        if (matriz[filaInicial][columnaInicial] == 0) {
            rellenar(matriz, filaInicial, columnaInicial);
            System.out.println("\nMatriz final (toda la isla pintada):");
            imprimirMatriz(matriz);
        } else {
            System.out.println("La posición inicial no es 0.");
        }

        sc.close();
    }

    // Función DFS recursiva para pintar la isla de ceros
    static void rellenar(int[][] matriz, int fila, int columna) {
        int filas = matriz.length;
        int columnas = matriz[0].length;

        // Limites y corte si ya es 1
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) return;
        if (matriz[fila][columna] != 0) return;

        // Pintar la celda
        matriz[fila][columna] = 1;
        System.out.println("Pintando celda: (" + fila + "," + columna + ")");
        imprimirMatriz(matriz);
        System.out.println("------------------");

        // Explorar vecinos en orden: arriba, abajo, izquierda, derecha
        rellenar(matriz, fila - 1, columna); // Arriba
        rellenar(matriz, fila + 1, columna); // Abajo
        rellenar(matriz, fila, columna - 1); // Izquierda
        rellenar(matriz, fila, columna + 1); // Derecha
    }

    // Función para imprimir la matriz
    static void imprimirMatriz(int[][] matriz) {
        for (int[] fila : matriz) {
            for (int valor : fila) {
                System.out.print(valor + " ");
            }
            System.out.println();
        }
    }
}
