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
        this.modelo = new KauffmanModelo(alto, ancho);
        this.modelo.run();
    };

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

        public KauffmanModelo(int n, int max){
            this.n = n;
            this.max = max;
            this.nodos = generaNodosALeatorios();
            this.adyacencias = generaAdyacenciasAleatorias();
            this.tablaDeVerdad = generaTablaDeVerdad();
            this.historial = new int[max][];
            this.historial[g] = nodos;
            this.g++;
        }

        public int[] generaNodosALeatorios(){

            int[] nodos = new int[n];

            for (int i = 0; i < n; i++){
                nodos[i] = rn.nextInt(2);
            }

            return nodos;
        }

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

        public HashMap<String,Integer> generaTablaDeVerdad(){

            HashMap<String,Integer> hashmap = new HashMap<>();

            for (int i = 0; i<8; i++){
                String bin = String.format("%3s", Integer.toBinaryString(i)).replaceAll(" ", "0");
                hashmap.put(bin, rn.nextInt(2));
            }

            return hashmap;
        }

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

        public void imprimeNodos(){
            for (int i = 0; i < n; i++){
                int nodo = this.nodos[i];
                System.out.println("Nodo: "+i+" Valor: "+nodo);
            }
        }

        public void imprimeAdyacencias(){
            for (int i = 0; i < n; i++){
                String resultado = "";
                int[] arr = adyacencias[i];
                for (int nodo: arr)
                    resultado += nodo+" ";
                System.out.println("Nodo: "+i+" -> "+resultado);
            }
        }

        public void imprimeTablaDeVerdad(){
            // Laves 
            String[] llaves = {"000","001","010","011","100","101","110","111",};

            for (String llave : llaves){
                System.out.println(llave+" "+"->"+" "+tablaDeVerdad.get(llave));
            }
        }

        public void imprimeHistorial(){
            for (int i = 0; i<n; i++){
                String resultado = "";
                for (int j = 0; j < max; j++){
                    resultado += historial[j][i];
                }
                System.out.println(resultado);
            }
        }

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

        public void run(){
            for (; g<max; g++){
                int[][] valores = calculaValores();
                int[] nuevosNodos = calculaNuevosNodos(valores);
                this.historial[g] = nuevosNodos;
                this.nodos = nuevosNodos;
            }
            imprimeHistorial();
        }
    }

    public static void main(String[] args){
        PApplet.main(new String[] { "kauffman.Kauffman" });
    }

}