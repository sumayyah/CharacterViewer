package com.sumayyah.characterviewer.main.Managers;
import java.util.ArrayList;
import java.util.List;

import com.sumayyah.characterviewer.main.Model.Character;
import com.sumayyah.characterviewer.main.Model.RelatedTopic;

/**
 * Created by sumayyah on 8/10/16.
 */
public class DataManager {

    private static DataManager instance = null;
    public static synchronized DataManager getInstance() {
        if(instance == null) {
            instance = new DataManager();
            characterList = new ArrayList<>();
        }
        return instance;
    }

    private static ArrayList<Character> characterList = null;

    public void populateList(List<RelatedTopic> topicList, ListCompleteListener listCompleteListener) {

        for(RelatedTopic relatedTopic: topicList) {
            Character c = new Character(relatedTopic);
            if(!characterList.contains(c)) {
                characterList.add(c);
            }
        }
        listCompleteListener.onListPopulateComplete();
    }

    public interface ListCompleteListener {
        void onListPopulateComplete();
    }

    public ArrayList<Character> getList() {
        return characterList;
    }

    public void setCharacterList(ArrayList<Character> list) {
        characterList = list;
    }
}
