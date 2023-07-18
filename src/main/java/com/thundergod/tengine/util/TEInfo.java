package com.thundergod.tengine.util;

import com.thundergod.ITPart;
import com.thundergod.tiostream.core.TIOStream;
import com.thundergod.tutil.core.TUText;

import java.util.List;

public class TEInfo implements ITPart {
    String name;
    String version;
    String description;
    List<String> authors;

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    int allComponentCount = 0;
    public TEInfo(String name,String version){
        this.name = name;
        this.version = version;
    }
    public void add(){
        allComponentCount++;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public void setDescription(String description){this.description = description;};

    @Override
    public String toString() {
        return  name+"/*0007/*- "+version+" -> ["+allComponentCount+"] "+authors.toString();
    }

    @Override
    public void init() {

    }

    @Override
    public void loop(float dt) {

    }

    @Override
    public void dispose() {

    }

    String VERSION="ver";
    String NAME = "name";
    String DESCRIPTION = "desc.";
    String AUTHORS = "authors";
    public void refresh() {
        TIOStream stream = new TIOStream("tengine.tei",true);
        stream.start();
        while (stream.canRead()){
            String r1 = stream.read();
            if (r1.equals(VERSION)){
                String r2 = stream.read();
                this.version = r2;
            }
            if (r1.equals(NAME)){
                String r2 = stream.read();
                this.name = r2;
            }
            if (r1.equals(DESCRIPTION)){
                String r2 = stream.read();
                this.description = description;
            }
            if (r1.equals(AUTHORS)){
                String r2 = stream.read();
                this.authors = TUText.convertDotToList(r2);
            }
        }
        stream.stop();
    }
}
