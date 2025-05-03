package ejercicios;

public class Fibo {

    public static void main(String[] args) {
        // Inicializamos las dos primeras variables de la secuencia de Fibonacci
        int a = 0, b = 1;

        // Imprimimos el título de la serie de Fibonacci
        System.out.println("Serie de Fibonacci hasta 100:");

        // Bucle que se ejecuta mientras 'a' sea menor o igual a 100
        while (a <= 100) {
            // Usamos un if para manejar el caso cuando 'a' es 0 o 1
            if (a == 0) {
                System.out.println(a); // Imprime 0 si 'a' es 0
            } else if (a == 1) {
                System.out.println(a); // Imprime 1 si 'a' es 1
            } else {
                // Para otros números, imprime normalmente
                System.out.println(a);
            }

            // Calculamos el siguiente término de la secuencia (que es la suma de 'a' y 'b')
            int siguiente = a + b;

            // Reasignamos el valor de 'a' para que tome el valor de 'b' (como parte de la secuencia Fibonacci)
            a = b;

            // 'b' toma el valor del término recién calculado, que es 'a + b'
            b = siguiente;
        }
    }
}
