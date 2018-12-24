package com.bantulogic.core.watermetrereader.Utilities;
import java.io.Serializable;
import java.util.ArrayList;
public class TabHistory implements Serializable {
    private ArrayList<Integer> stack = new ArrayList<Integer>();
    private boolean isEmpty = stack.size() == 0;
    public int size =  stack.size();

    public void push(int entry){
        stack.add(entry);
    }
    public int popPrevious(){
        int entry = -1;
        if (!isEmpty){
            Integer index = stack.size()-2;
            entry = stack.get(index);
            stack.remove(stack.size()-2);
        }
        return  entry;
    }
    public void clear(){
        stack.clear();
    }
}
