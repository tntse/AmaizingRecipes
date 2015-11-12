package com.taeyeona.amaizingunicornrecipes;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by thomastse on 11/7/15.
 */
public class IngredientsManager implements Set<String> {
    private ArrayList<String> list;

    public IngredientsManager(ArrayList<String> setList){
        list = setList;
    }

    public IngredientsManager(){
        list = new ArrayList<String>();
    }

    public IngredientsManager(Set<String> other){
        list = new ArrayList<>();
        list.addAll(other);
    }

    @Override
    public boolean add(String s) {
        return list.add(s);
    }

    @Override
    public boolean addAll(Collection<? extends String> collection) {
        return false;
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return list.containsAll(collection);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<String> iterator() {
        return list.iterator();
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return list.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return list.retainAll(collection);
    }

    @Override
    public int size() {
        return list.size();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] ts) {
        return list.toArray(ts);
    }

    public String[] findMissingIngredients(String[] ingredientList){
        ArrayList<String> ret = new ArrayList<String>();
        boolean found;

        for(int i = 0; i < ingredientList.length; i++){
            found = false;
            for(int j = 0; j < list.size() || !found; j++)
                if(ingredientList[i].equals(list.get(j)))
                    found = true;
            if(!found)
                ret.add(ingredientList[i]);
        }

        return (String[]) ret.toArray();
    }

    public String toString(){
        String ret = "";
        for(int i = 0; i < list.size(); i++){
            ret += list.get(i).toString() + "\n";
        }
        return ret;
    }
}
