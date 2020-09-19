package AllDice.Classes.Implementations;

import AllDice.Classes.Implementations.Tuple;

import java.util.ArrayList;

public class JDictionary<T2> {
    private ArrayList<Tuple<String, T2>> list;

    public JDictionary(){
        this.list = new ArrayList<>();
    }

    public void add(String key, T2 value){
        if (this.contains(key)){
            this.replace(key, value);
        } else {
            list.add(new Tuple<>(key, value));
        }
    }

    public boolean replace(String key, T2 value){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).Item1.equals(key)){
                list.get(i).Item2 = value;
                return true;
            }
        }
        return false;
    }

    public T2 get(String key){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).Item1.equals(key)){
                return list.get(i).Item2;
            }
        }
        return null;
    }

    public boolean contains(String key){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).Item1.equals(key)){
                return true;
            }
        }
        return false;
    }

    public boolean remove(String key){
        int listSize = list.size();
        for(int i = 0; i < listSize; i++){
            if (list.get(i).Item1.equals(key)){
                list.remove(i);
                return true;
            }
        }
        return false;
    }
}
