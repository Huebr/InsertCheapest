package Insert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Insert {
    private static ArrayList<ArrayList<Double>> matrix;
    private static ArrayList<ArrayList<Double>> cmatrix;
    private static int tp;
    private static ArrayList<Integer> path;
    public static void main(String[] args) {//C:\Users\pedro\Downloads\Tsp29.txt
	// write your code here
        String filename,line;
        matrix= new ArrayList();//Matrix de adjacencia
        BufferedReader read;
        Scanner sc=new Scanner(System.in);
        while(true) {
            System.out.print("Digite nome do arquivo : ");
            filename=sc.nextLine();
            try {
                read = new BufferedReader(new FileReader(filename));
                line=read.readLine();
                if(line.contains("Tipo=1"))tp=1;
                else if(line.contains("Tipo=2"))tp=2;
                else tp=3;
                int col = 0;
                while((line=read.readLine())!=null){
                    matrix.add(new ArrayList<>());
                    String temp[]=line.split("(\\s+)");
                    for(String t1:temp){
                        if(!t1.equals(""))
                        matrix.get(col).add(Double.valueOf(t1));
                    }
                    col++;
                }
                read.close();
                break;
            }
            catch (IOException ex){
                System.out.println("Erro ao tentar abrir arquivo.");
            }
        }
        if(tp==2){
            Prep();
        }
        HInsert();
        print();


    }
    public static void HInsert(){//será
        path=new ArrayList<>();
        Random r1=new Random(System.currentTimeMillis());
        int t;
        int tam=matrix.get(0).size()+1;
        t=r1.nextInt(tam);//Analisar jorge vida louca
        int min=(t+1)%tam;

        for(int j=0;j<tam;j++){
            if(t!=j) {
                if(tp==3) {
                    if (c2(t, j) < c2(t, min)) min = j;
                }
                else if (c1(t, j) < c1(t, min)) min = j;
            }
        }
        path.add(t+1);
        path.add(min+1);
        path.add(t + 1);
        while(path.size()<tam+1){
            int tmin=0;
            int v = 0;
            double cmin=999999999;

            for (int i = 0; (i + 1) < path.size(); i++) {
                for(int y=0;y<tam;++y) {
                    if (!path.contains(y+1)) {
                        if (tp == 3) {
                            double a1=c2(path.get(i)-1,y)+c2(y,path.get(i+1)-1)-c2(path.get(i)-1,path.get(i+1)-1);
                            if((a1<cmin)) {
                                v = y;
                                tmin = i;
                                cmin=a1;
                            }
                        } else {
                            double a1=c1((path.get(i)-1), y)+c1(y,( path.get(i + 1)-1))-c1((path.get(i)-1), (path.get(i + 1)-1));
                            if((a1<cmin)) {
                                v = y;
                                tmin = i;
                                cmin=a1;
                            }
                        }
                    }
                }

            }
            ArrayList<Integer> temp=new ArrayList<>();
            for(int k =0;k<path.size();k++){//
                if(k==tmin+1){
                    temp.add(v+1);
                    k--;
                    tmin=-999;
                }
                else temp.add(path.get(k));

            }
            path=temp;
        }

    }
    public static void print(){//TODO falta termina o print
        double custo=0;
        for(int i=0;i+1<path.size();++i){
            if(tp==3)custo+=c2(path.get(i)-1,path.get(i+1)-1);
            else custo+=c1(path.get(i)-1,path.get(i+1)-1);
        }
        if(tp==1){
            System.out.print("Tipo=1(Matriz Simetrica):=>Solucao:=");
            System.out.print(custo+" Rota:");
            for(int i=0;i<path.size();i++){
                System.out.print(path.get(i));
                if(i+1<path.size())System.out.print("-");
            }
            System.out.println("\nN="+(matrix.get(0).size()+1));

        }
        else if(tp==2){
            System.out.print("Tipo=2(Coordenadas Cartesianas):=>Solucao:=");
            System.out.print(custo+" Rota:");
            for(int i=0;i<path.size();i++){
                System.out.print(path.get(i));
                if(i+1<path.size())System.out.print("-");
            }
            System.out.print("\nN="+(matrix.get(0).size()+1));
            for(int i=0;i<path.size();i++){
                System.out.print("("+cmatrix.get(path.get(i)-1).get(0)+","+cmatrix.get(path.get(i)-1).get(1)+")");
            }
        }else{
            System.out.print("Tipo=3(Matriz nao-Simetrica):=>Solucao:=");
            System.out.print(custo+" Rota:");
            for(int i=0;i<path.size();i++){
                System.out.print(path.get(i));
                if(i+1<path.size())System.out.print("-");
            }
            System.out.println("\nN="+(matrix.size()+1));
        }
    }
    public static double c1(int i, int j){
        if(i>j)return matrix.get(j).get(i-j-1);
        return matrix.get(i).get(j-i-1);
    }
    public static double c2(int i,int j){
        return matrix.get(i).get(j);
    }
    public static void Prep(){
        ArrayList<ArrayList<Double>> tmatrix=new ArrayList<>();
        int tam=matrix.size();
        for(int i=0;i<tam;i++){
            if(i+1<tam)tmatrix.add(new ArrayList<>());
            for(int j=i+1;j<tam;j++){
                double t1,t2;
                t1=matrix.get(i).get(0)-matrix.get(j).get(0);
                t2=matrix.get(i).get(1)-matrix.get(j).get(1);
                t1*=t1;
                t2*=t2;
                tmatrix.get(i).add(Math.sqrt(t1+t2)*1.0);
            }
        }
        cmatrix=matrix;
        matrix=tmatrix;
    }
}
