/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seminar;

/**
 *
 * @author nenab
 */
class Pair<T extends Integer, S extends Integer> {
    T moguciSinonimIndeks;
    S rijecIndeks;
    Pair(T sinonim, S rijec)
    {
        moguciSinonimIndeks = sinonim;
        rijecIndeks = rijec;
    }
    @Override
    public String toString()
    {
        return moguciSinonimIndeks+","+rijecIndeks;
    }
    
}
