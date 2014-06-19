package com.webstub.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

import static com.webstub.util.SyntacticSugar.safe;

@Document(collection = "stub")
public class Stub {
    @Id
    private String id;
    private String name;
    private Script activeScript;
    private List<Script> scripts;

    public Stub(){}

    public Stub(String name, List<Script> scripts){
        this.name = name;
        this.scripts = new ArrayList<>(scripts);
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public Script getActiveScript() {
        return activeScript;
    }

    public void setActiveScript(Script activeScript) {
        this.activeScript = activeScript;
    }

    public void setActiveScript(String scriptName) {
        for (Script script : safe(scripts)){
            if(script.getName().equals(scriptName)){
                setActiveScript(script);
            }
        }
    }

    public List<Script> getScripts() {
        return scripts;
    }

    public void setScripts(List<Script> scripts){
        this.scripts = scripts;
    }

    @Override
    public boolean equals(Object obj) {
        if( !(obj instanceof Stub)){
            return false;
        }

        Stub toCompare = (Stub) obj;

        return this.name.equals(toCompare.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder toStringBuilder = new StringBuilder();
        toStringBuilder.append("\nName: ").append(name);
        toStringBuilder.append("\nActive script: ").append(activeScript);
        toStringBuilder.append("\nNumber of scripts: " + (scripts != null ? scripts.size() : "0"));

        return toStringBuilder.toString();
    }

}
