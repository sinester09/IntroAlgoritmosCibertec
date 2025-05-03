package ejercicios;

public class Fibo {

    public static void main(String[] args) {
        // Inicializamos las dos primeras variables de la secuencia de Fibonacci
        int a = 0, b = 1;

        // Imprimimos el título de la serie de Fibonacci
        System.out.println("Serie de Fibonacci hasta 100:");

        // Bucle que se ejecuta mientras 'a' sea menor o igual a 100
        while (a <= 100) {
            // Imprime el valor de 'a' en cada ciclo (número de la serie de Fibonacci)
            System.out.println(a);

            // Calculamos el siguiente término de la secuencia (que es la suma de 'a' y 'b')
            int siguiente = a + b;

            // Reasignamos el valor de 'a' para que tome el valor de 'b' (como parte de la secuencia Fibonacci)
            a = b;

            // 'b' toma el valor del término recién calculado, que es 'a + b'
            b = siguiente;
        }
    }
}
