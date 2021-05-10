package com.alisarrian.reflection.tasks;

class Bicycle {

    int gear;
    int speed;

    Bicycle(int gear, int speed) {
        this.gear = gear;
        this.speed = speed;
    }

    void applyBrake(int decrement) {
        speed -= decrement;
    }

    void speedUp(int increment) {
        speed += increment;
    }

    public String toString() {
        return ("No of gears are " + gear + "\n"
                + "speed of bicycle is " + speed);
    }
}

