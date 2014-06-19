package com.webstub.domain;

public class Script {
    private Integer id;
    private String name, path, content;

    public Script(){}

    public Script(Integer id, String name, String path, String content){
        this.id = id;
        this.name = name;
        this.path = path;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + this.path.hashCode() + content.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if( !(other instanceof Script) ){
            return false;
        }

        Script compareTo = (Script)other;

        if(this.name.equals(compareTo.getName()) && this.path.equals(compareTo.getPath()) && this.content.equals(compareTo.getContent())){
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder toStringBuilder = new StringBuilder();
        toStringBuilder.append("Name: ").append(name);
        toStringBuilder.append("\nPath: ").append(path);

        return toStringBuilder.toString();
    }
}
