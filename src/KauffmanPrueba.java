import java.util.Random;

class KauffmanPrueba{

    // Cantidad de nodos
    public static int n = 20;
    // Nodos
    public static int[] nodes = new int[n];
    // Conexiones
    public static int[][] connections = new int[20][3];
    // Valores de las conexiones
    public static int[][] values = new int[n][3];
    // Valores de la tabla
    public static int[] tableValue = new int[8];
    // Generacion
    public static int g = 1;
    // g maximas
    public static int max = 100;
    // Historial de valores
    public static int[][] historyValues = new int[max][n];
    // Random
    public static Random rn = new Random();

    public static void conexionesRandom(){
        // Conexiones aleatorias
        for (int i = 0; i < n; i++){
            for (int j = 0; j < 3; j++){
                int node = rn.nextInt(n);
                connections[i][j] = node;
            }
        }
    }

    public static void coneccionesValores(){
        // Llenamos el arreglo de valores de las conexiones
        for (int i = 0; i < n; i++){
            for (int j = 0; j < 3; j++){
                int node = connections[i][j];
                int value = nodes[node];
                values[i][j] = value;
            }
        }
    }

    public static void nuevosValores(){
        // Nuevos valores del nodo
        int[] newValues = new int[n];

        for (int i = 0; i < n; i++){
            String input = "";
            for (int j = 0; j<3; j++){
                input += values[i][j];
            }
            switch (input) {
                case "000":
                    newValues[i] = tableValue[0];
                    break;
                case "001":
                    newValues[i] = tableValue[1];
                    break;
                case "010":
                    newValues[i] = tableValue[2];
                    break;
                case "011":
                    newValues[i] = tableValue[3];
                    break;
                case "100":
                    newValues[i] = tableValue[4];
                    break;
                case "101":
                    newValues[i] = tableValue[5];
                    break; 
                case "110":
                    newValues[i] = tableValue[6];
                    break;             
                case "111":
                    newValues[i] = tableValue[7];
                    break;
            }
        }
        // Agregamos los nuevos valores al historial
        historyValues[g] = newValues;

        // Actualizamos los valores nuevos
        nodes = newValues;
    }

    public static void print(String str){
        System.out.println(str);
    }

    public static void main (String[] args){

        // Llenamos el arreglo de nodos con valores aleatorios
        for (int i = 0; i < n ; i++){
            nodes[i] = rn.nextInt(2);
        }

        // Llenamos con valores aleatorios
        for (int i = 0; i < 8; i++){
            tableValue[i] = rn.nextInt(2);
        }

        // Agregamos la primera generacion
        historyValues[0] = nodes;

        // Conexiones random
        conexionesRandom();

        for (; g < max; g++){
            coneccionesValores();
            nuevosValores();
        }
        
        for (int i = 0; i<n; i++){
            String result = "";
            for(int j = 0; j<max; j++){
                result += historyValues[j][i];
            }
            print(result.replaceAll("1", " "));
        }
    }
}

/*
 * print("Valores de los nodos");
        for (int i = 0; i < n; i++){
            print(""+i+": "+nodes[i]);
        }

        print("\n");

        print("Las conexiones de los nodos");
        for (int i = 0; i < n; i++){
            int[] arr = connections[i];
            String cons = "";
            for (int j = 0; j<3; j++){
                cons += arr[j]+" ";
            }
            print(""+i+" -> "+ cons);
        }

        print("\n");

        print("Valores de las conexiones");
        for (int i = 0; i < n; i++){
            int[] arr = values[i];
            String vals = "";
            for (int j = 0; j<3; j++){
                vals += arr[j];
            }
            print(""+i+" -> "+vals);
        }
        
        print("\n");

        print("Valores tabla");
        for (int i = 0; i<8; i++){
            // Numero en binario
            String bin = String.format("%3s", Integer.toBinaryString(i)).replaceAll(" ", "0");
           print(""+i+": "+bin+" -> "+tableValue[i]);
        }

        print("\n");

        print("Nuevos valores");
        for (int i = 0; i<n; i++){
            print(""+i+": "+newValues[i]);
        }
 */