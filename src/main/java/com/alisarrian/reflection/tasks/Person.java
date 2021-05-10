package com.alisarrian.reflection.tasks;

public class Person {

    private String name;
    private int age;
    private Address address;

    Person(){}

    Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    void printInfoAbout(Person person) {
        System.out.println(details(person));
    }

    @Override
    public String toString() {

        return details(this);
    }

    private String details(Person p) {
        return "My name is " + p.name + ". I am " + p.age + " years old and I live in " + p.address.city + ".";
    }

}

class Address {

    String city;

    Address(String city) {
        this.city = city;
    }

}
