package com.example.ivanchepelkin.graf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity {
    public static void main(String[] args) {
        /*                                 A B C D E F             */
        Graf metro = new Graf(new int[][]{{0,4,0,3,0,0},   //A
                                          {0,0,0,2,0,3},   //B
                                          {0,0,0,0,0,0},   //C
                                          {0,0,0,0,6,0},   //D
                                          {2,0,1,0,0,0},   //E
                                          {1,0,0,0,0,0}}); //F

        metro.zeroInfinity(0);//обращаемся к методу zeroInfinity, отпраяляя ему начальную и конечную точки
        metro.searchPoint(0,2);
    }
}

class Graf {
    private int graf[][]; //матрица наших точек
    public int R[];//массив для строки
    public int P[];//массив
    public int H[];//массив содержит только еденицы, нужен для сравнения
    char points[] = {'A','B','C','D','E','F'};

    public int Y[];//массив

    int index = 0; //индекс эл-та строки
    int index2 = 0;//индекс эл-та столбца




    //создаем конструктор
    public Graf(int[][] graf) {
        this.graf = graf;
    }

    public void zeroInfinity(int startPoint){

        P = graf[startPoint].clone();//создаем клон нулевой строчки массива graf
       // P[startPoint] = 1; //первый элемент всегда равен 1
        for (int i = 0; i < P.length; i++) {
            if (i == startPoint) { // элемент startPoint всегда равен 1
                P[i] = 1;
            } else {
                P[i] = 0;//остальные элементы равны 0
            }
        }

        int a = 1000; //вместо нуля в массиве вставляем очень большое число
        R = graf[startPoint].clone();//создаем клон нулевой строчки массива graf
        H = graf[startPoint].clone();//создаем клон нулевой строчки массива graf

        for (int i =0 ; i < R.length; i++){

            H[i]=1; //все элементы массива равны 1
            if (R[i] == 0 && i != startPoint ){ //если эл-т массива равен 0 индекс i не равен индексу стартовой точки
                R[i] = a; // то он заеняется на число в переменной а
            }
        }

        int min = 1000; //значение для min числа

        while (!Arrays.equals(P, H)) { //пока масив P и H не равны

            for (int k = 0; k < R.length; k++)

                if (min >= R[k] && P[k] != 1) { //если min число больше или равно числу с индексом k и массив P с этим индексом не равен 1

                    min = R[k];//min число равно числу с индексом k
                    index = k; //индекс это числа записывается в переменную index
                }

            for (int x = 0; x < R.length; x++) {

                if (graf[index][x] == 0) { //если элемент строки с индексом index массива graf  равна 0
                    graf[index][x] = a; //он равен очень большому числу
                }
                if (x != startPoint && R[x] > R[index] + graf[index][x]) { //если эл-т массива R больше суммы эл-та массива R c индексом index и элемента строки с индексом index массива graf
                    R[x] = R[index] + graf[index][x]; // индекс x не равен индексу стартовой точки (чтоб точка не искала путь к самой себе)
                }                                     //то он заменяется на это число

                if (x == R.length - 1) { //как только прошли по всем элементам массива R
                    P[index] = 1; //выставляем еденицу в элемент массива P c индексом index
                    min = 1000; //min число опять очень большое число
                }
            }
        }
        System.out.println("Массив R " + Arrays.toString(R));
    }//метод находит кратчайший путь от точки А к остальным точкам
    public void searchPoint(int startPoint,int endPoint){

        ArrayList<Character> listPoint  = new ArrayList<>();
        index2 = endPoint;
        index = 0;


        Y = R.clone();//создаем клон нулевой строчки массива graf

        for (int i = 0; i < Y.length; i++) {
            if (i == startPoint) { // элемент endPoint всегда равен 1
                Y[i] = 1;
            } else {
                Y[i] = 0;//остальные элементы равны 0
            }
        }

        while (index2 != startPoint) {
            listPoint.add(points[index2]);
            for (int i = 0; i < R.length; i++) {
                if (graf[i][index2] > 0 && graf[i][index2] != 1000) {
                    index = i;
                    break;
                }
            }
            for (int i = 0; i < R.length; i++) {
                if (R[i] == R[index2] - graf[index][index2]) {
                    Y[index2] = 1;
                    index2 = i;
                    break;
                }
            }
        }
        listPoint.add(points[startPoint]);
        Collections.reverse(listPoint); // инвертируем массив
        //System.out.println("Массив индексов точек "+Arrays.toString(Y));
        System.out.println("Массив индексов точек "+listPoint.toString());
    }//метод выводит точки, через которые строится кратчайший путь
}
