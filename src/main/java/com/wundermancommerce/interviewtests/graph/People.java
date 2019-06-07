package com.wundermancommerce.interviewtests.graph;

public class People {
    private String name;
    private int age;
    private String email;

    public People(String name, String email, int age) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof People){
            People p2 = (People)obj;
            if(this.name.equals(p2.name) &&
                this.email.equals(p2.email) &&
                        this.age == p2.age){
                return true;
            }
        }
        return false;
    }
}
