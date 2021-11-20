/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seminar;


import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author nenab
 */
public class Seminar {

    private static Map<Integer, Double> sortByValue(HashMap<Integer, Double> hm) {
        // Create a list from elements of HashMap
		List<Map.Entry<Integer, Double> > list =
			new LinkedList<Map.Entry<Integer, Double> >(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<Integer, Double> >() {
			public int compare(Map.Entry<Integer, Double> o1,
							Map.Entry<Integer, Double> o2)
			{
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		
		// put data from sorted list to hashmap
		HashMap<Integer, Double> temp = new LinkedHashMap<Integer, Double>();
		for (Map.Entry<Integer, Double> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
    }

    
    public void ucitajRjecnik(String dict, HashMap<Integer,String> rjecnik) throws IOException //ne mora primat rjecnik jer je globalan
    {
        File datoteka = new File(dict); // dict je oblika "dat.txt", nzm oce radit zbog putanje
       
            
        BufferedReader citac = new BufferedReader ( new InputStreamReader ( new FileInputStream(datoteka) ) ) ;
        String linija = "";
        
        int indeks = 1;
        while((linija = citac.readLine()) != null)
        {
            rjecnik.put(indeks,linija);
            indeks++;
            //System.out.println(linija);
        }


    }
    public void ucitajSinonime(String index, Map<Point, Integer> matrix, int dim) throws IOException // u matricu
    {
        File datoteka = new File(index);
        try {
            
            BufferedReader citac = new BufferedReader ( new InputStreamReader ( new FileInputStream(datoteka) ) ) ;
            String linija = "";
            while((linija = citac.readLine()) != null){
                
                String[] indeksiString = linija.split(" ");
                try{
                    Integer j = Integer.parseInt(indeksiString[0]);
                    Integer k = Integer.parseInt(indeksiString[1]);                   
                    

                    matrix.put(new Point(j, k), 1); // s ovim čuvam memoriju
                    //matrix.put(new Point(k, j), 1);
                    //Integer fiveTwo = matrix.get(new Point(5, 2));
                    
                }
                catch (Exception ex) {
                    //Logger.getLogger(Seminar.class.getName()).log(Level.SEVERE, null, ex);
                    continue;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Seminar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
  
    public ArrayList<Integer> nadji(Map<Point, Integer> matrix, Integer indeks, Integer dimenzija) //nalazi redak
    {
        ArrayList<Integer> lista1 = new ArrayList<Integer>();
        Integer br = 0;
        for(int i = 1; i < dimenzija+1; i++)
        {
            try{
                br = matrix.get(new Point(indeks, i));
                if(br != null)
                    lista1.add(i);
                else
                    lista1.add(0);
            }
            catch(Exception ex){
                System.out.println("Grreska");
                continue;
                
            }
        }
        return lista1;
    }
    public ArrayList<Integer> nadji2(Map<Point, Integer> matrix, Integer indeks, Integer dimenzija)//nalazi stupac
    {
        ArrayList<Integer> lista1 = new ArrayList<Integer>();
        Integer br = 0;
        for(int i = 1; i < dimenzija+1; i++)
        {
            try{
                br = matrix.get(new Point(i, indeks));
                if(br != null)
                    lista1.add(i);
                else
                    lista1.add(0);
            }
            catch(Exception ex){
                continue;
            }
        }
        return lista1;
    }
    public double udaljenost(ArrayList<Integer> lista1, ArrayList<Integer> lista2 )
    {
        double sum = 0;
        if(lista1.size()!= lista2.size())
            System.out.println("Greska!");
        for(int i = 0; i < lista1.size();i++)
        {
            sum += Math.pow(lista1.get(i)-lista2.get(i),2);
        }
        return sum;
    }
    public int provjera(ArrayList<Integer> lista)
    {
        int f = 0;
        for(Integer d:lista)
        {
            if(d!=0)
            {
                f = 1;
                break;
            }
        }
        return f;
            
    }
    public ArrayList<Integer> pokazujuNa(Map<Point, Integer> matrix, int indeks, int dimenzija)
    {
        ArrayList<Integer> lista = new ArrayList<Integer>();
        for(int i  = 0; i < dimenzija; i++)
        {
            if(matrix.get(new Point(i, indeks))!= null)
            {
                lista.add(i);
            }
        }
        return lista;
    }
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        HashMap<Integer,String> rjecnik = new HashMap<Integer,String>(); //ucitavanje dict datoteke, svaka riječ jedinstvena jer mi je lakše tako
        
        new Seminar().ucitajRjecnik("C:\\Users\\nenab\\Documents\\NetBeansProjects\\seminar\\src\\seminar\\index.txt", rjecnik);
        
        int dimenzija = rjecnik.size();
        //Integer[][] poveznice = new Integer[dimenzija][dimenzija]; //ovo nije proslo jer zauzima previse prostora
        Map<Point, Integer> matrix = new HashMap<Point, Integer>(); //point je zamjena za pair; na ovaj nacin zauzimamo manje prostora
        
        
        new Seminar().ucitajSinonime("C:\\Users\\nenab\\Documents\\NetBeansProjects\\seminar\\src\\seminar\\dico.txt", matrix, dimenzija);
       
        String input = "disappear"; // ovo treba bit input, da se moze trazit
        int i=0;
        for(i = 1; i < rjecnik.size()+1; i++) // ovo ne bi trebalo bit presporo
        {
            if(rjecnik.get(i).equals(input)) //nađem indeks traženog inputa
                break;                                
        }
        System.out.println(dimenzija);
        
        HashMap<Integer, Double> rjesenje = new  HashMap<Integer, Double>();
        
        ArrayList<Integer> lista1 = new Seminar().nadji(matrix, i,dimenzija); // i-ti redak
        ArrayList<Integer> lista3 = new Seminar().nadji2(matrix, i,dimenzija); //i-ti stupac
        
        Double rj = 0.0;
        double max = 0;
        int indeks = 0;
        for(int k = 0; k < dimenzija; k++)
        {

            if(lista1.get(k)!=0) //msm da je ovo ok jer je onda i lista2(k)!=0
            {
                ArrayList<Integer> lista2 = new Seminar().nadji(matrix, k,dimenzija);

                ArrayList<Integer> lista4 = new Seminar().nadji2(matrix, k,dimenzija);


                rj += new Seminar().udaljenost(lista1, lista2);
                rj += new Seminar().udaljenost(lista3, lista4);
                
                System.out.println(rjecnik.get(k));
            }
            
            rjesenje.put(k,rj);
            rj = 0.0;
        }
        
        ArrayList<Integer> lista = new Seminar().pokazujuNa(matrix, i, dimenzija);
        for(int k = 0; k < lista.size(); k++)
        {
            ArrayList<Integer> lista2 = new Seminar().nadji(matrix, lista.get(k),dimenzija);
            ArrayList<Integer> lista4 = new Seminar().nadji2(matrix, lista.get(k),dimenzija);


            rj += new Seminar().udaljenost(lista1, lista2);
            rj += new Seminar().udaljenost(lista3, lista4);
            
            System.out.println(rjecnik.get(lista.get(k)));
           

            rjesenje.put(lista.get(k),rj);
            rj = 0.0;
        }

        System.out.println(new Seminar().pokazujuNa(matrix, i, dimenzija));
        
        
        /*double maxx = 0.0;
        int indekss = 0;
        for(Integer s: rjesenje.keySet())
        {
            if(rjesenje.get(s)>maxx)
            {
                maxx = rjesenje.get(s);
                indekss = s;
            }
        }*/
        //System.out.println(rjecnik.get(indekss));
        Map<Integer, Double> hm1 = sortByValue(rjesenje);
 
        // print the sorted hashmap
        for (Map.Entry<Integer, Double> en : hm1.entrySet()) {
            if(en.getValue()!=0){
                System.out.println("Key = " + en.getKey() +
                          ", Value = " + en.getValue()+"rijec:"+rjecnik.get(en.getKey()));
            }
        }
        }
}


       
        //System.out.println(rjecnik.get(indeks));
        
        
        
        
        
       
    
