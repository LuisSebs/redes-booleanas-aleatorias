package kauffman;
import processing.core.PApplet;
import java.util.Random;
import java.util.HashMap;

public class Kauffman extends PApplet {

    /** Cantidad de celdas a lo ancho/cantidad de generaciones */
    int ancho = 100;
    /** Cantidad de celdas a lo largo/cantidad de nodos */
    int alto = 20;
    /** Tamaño en pixeles de una celda */
    int celda = 10;
    /** Representacion logica del automata */
    KauffmanModelo modelo;

    @Override
    public void setup(){
        frameRate(60);
        background(200);
        modelo = new KauffmanModelo(alto,ancho);
        modelo.run();
    }

    @Override
    public void settings(){
        size(ancho * celda, alto * celda);
    }

    @Override
    public void draw(){
        /* Nota: Esta implementacion es un poco inusual
         * ya que en el historial tenemos que iterar
         * primero por la cantidad de nodos y despues
         * por la cantidad de generaciones y al dibujar
         * el rectangulo primero ponemos j y después i.
         */
        for (int i = 0; i<alto; i++){
            for (int j = 0; j < ancho; j++){
                if (modelo.historial[j][i] == 1){
                    fill(0,0,0);
                    stroke(0,0,0);
                }else{
                    fill(255,255,255);
                    stroke(255,255,255);
                }
                rect(j * celda,i * celda, celda, celda);
            }
        }
    }

    @Override
    public void mouseClicked() {
        // Cambiamos el modelo para que el usuario pueda ver un patron nuevo
        this.modelo = new KauffmanModelo(alto, ancho);
        this.modelo.run();
    };

    /**
     * Representa un modelo
     * de las redes booleanas
     * aleatorias de Kauffman
     */
    class KauffmanModelo{

        /** Numero de nodos */
        public int n;

        /** Nodos */
        public int[] nodos;

        /** Adyacencias */
        public int[][] adyacencias = new int[n][];

        /** Tabla de verdad */
        public HashMap<String,Integer> tablaDeVerdad;

        /** Generacion actual */
        public int g = 0;

        /** Generaciones maximas */
        public int max;

        /** Historial de valores generados */
        public int[][] historial;

        /**Random */
        Random rn = new Random();

        /**
         * Constructor
         * @param n cantidad de nodos.
         * @param max cantidad maxima de iteraciones.
         */
        public KauffmanModelo(int n, int max){
            this.n = n;
            this.max = max;
            this.nodos = generaNodosAleatorios();
            this.adyacencias = generaAdyacenciasAleatorias();
            this.tablaDeVerdad = generaTablaDeVerdad();
            this.historial = new int[max][];
            this.historial[g] = nodos;
            this.g++;
        }

        /**
         * Regresa un arreglo de nodos aleatorios
         * @return arreglo de enteros que representan nodos del modelo
         * con valores entre 1 y 0.
         */
        public int[] generaNodosAleatorios(){

            int[] nodos = new int[n];

            for (int i = 0; i < n; i++){
                nodos[i] = rn.nextInt(2);
            }

            return nodos;
        }

        /**
         * Regresa un arreglo de ayacencias
         * @return arreglo de arreglos de longitud 3
         * que representan las adyacencias de cada nodo.
         */
        public int[][] generaAdyacenciasAleatorias(){
            
            int[][] adyacencias = new int[n][];

            for (int i = 0; i<n; i++){
                int nodo = i; 
                int[] arr = new int[3];
                for (int j = 0; j < 3;){
                    int adyacencia = rn.nextInt(n);
                    if (adyacencia != nodo){
                        arr[j] = adyacencia;
                        j++;
                    }
                }
                adyacencias[i] = arr;
            }
            
            return adyacencias;
        }

        /**
         * Regresa una tabla de verdad
         * @return hashmap, las llaves son 
         * representaciones de los numeros
         * del 0 al 7 en binario a 3 bits,
         * con valores aleatorios entre
         * 1 y 0.
         */
        public HashMap<String,Integer> generaTablaDeVerdad(){

            HashMap<String,Integer> hashmap = new HashMap<>();

            for (int i = 0; i<8; i++){
                String bin = String.format("%3s", Integer.toBinaryString(i)).replaceAll(" ", "0");
                hashmap.put(bin, rn.nextInt(2));
            }

            return hashmap;
        }

        /**
         * Calcula los valores dado las
         * adyacencias de los nodos.
         * @return arreglo de arreglos de enteros de longitud 3
         * que representan los valores de los nodos segun sus
         * adyacencias.
         */
        public int[][] calculaValores(){

            int[][] valores = new int[n][];

            for (int i = 0; i < n; i++){
                int[] arrNodos = adyacencias[i];
                int[] arrValores = new int[3];
                for (int j = 0; j < 3; j++){
                    int nodo = arrNodos[j];
                    arrValores[j] = nodos[nodo];
                }
                valores[i] = arrValores;
            }
            return valores;
        }

        /**
         * Calcula los nuevos nodos
         * dado un arreglo de valores.
         * @param valores arreglo de valores
         * @return nuevos nodos.
         */
        public int[] calculaNuevosNodos(int[][] valores){

            int[] nuevosNodos = new int[n];

            for (int i = 0; i < n; i++){
                String llave = "";
                int[] arrValores = valores[i];
                for (int valor : arrValores){
                    llave += valor;
                }
                nuevosNodos[i] = tablaDeVerdad.get(llave);
            }

            return nuevosNodos;
        }

        /**
         * Imprime los nodos del modelo
         */
        public void imprimeNodos(){
            for (int i = 0; i < n; i++){
                int nodo = this.nodos[i];
                System.out.println("Nodo: "+i+" Valor: "+nodo);
            }
        }

        /**
         * Imprime las adyacencias de los nodos
         */
        public void imprimeAdyacencias(){
            for (int i = 0; i < n; i++){
                String resultado = "";
                int[] arr = adyacencias[i];
                for (int nodo: arr)
                    resultado += nodo+" ";
                System.out.println("Nodo: "+i+" -> "+resultado);
            }
        }

        /**
         * Imprime la tabla de verdad
         */
        public void imprimeTablaDeVerdad(){
            // Laves 
            String[] llaves = {"000","001","010","011","100","101","110","111",};

            for (String llave : llaves){
                System.out.println(llave+" "+"->"+" "+tablaDeVerdad.get(llave));
            }
        }

        /**
         * Imprime el historial
         */
        public void imprimeHistorial(){
            for (int i = 0; i<n; i++){
                String resultado = "";
                for (int j = 0; j < max; j++){
                    resultado += historial[j][i];
                }
                System.out.println(resultado);
            }
        }

        /**
         * Imprime el historial pero cambia
         * los 1's por espacios en blanco para
         * ver el patron generado.
         */
        public void imprimeHistorialPatron(){
            for (int i = 0; i<n; i++){
                String resultado = "";
                for (int j = 0; j < max; j++){
                    resultado += historial[j][i];
                }
                resultado = resultado.replaceAll("1", " ");
                System.out.println(resultado);
            }
        }

        /**
         * Imprime un arreglo de valores.
         * @param valores
         */
        public void imprimeValores(int[][] valores){
            for (int i = 0; i < n; i++){
                String resultado = "";
                int[] arr = valores[i];
                for  (int valor : arr){
                    resultado+= valor;
                }
                System.out.println("Nodo: "+i+" Valores: "+resultado);
            }
        }

        /**
         * Ejecuta la red booleana
         */
        public void run(){
            for (; g<max; g++){
                int[][] valores = calculaValores();
                int[] nuevosNodos = calculaNuevosNodos(valores);
                this.historial[g] = nuevosNodos;
                this.nodos = nuevosNodos;
            }
            //imprimeHistorial(); // imprime el historial en terminal
        }
    }

    public static void main(String[] args){
        PApplet.main(new String[] { "kauffman.Kauffman" });
    }

}