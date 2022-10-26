package com.example.petpal.ui.myPets;

public class Pet {
    private String name,type,breed,size;

    public Pet() {
    }

    public Pet(String name, String type, String breed, String size) {
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSize() {
        return size;
    }



    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", breed='" + breed + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
