package com.zyy.study.alldemo.other.compare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Comparator;

public class ComparatorDemo {
    public static void main(String[] args) {
        Person2 person1 = new Person2.PersonBuilder().age(10).money(100).name("张三").build();
        Person2 person2 = new Person2.PersonBuilder().age(10).money(200).name("李四").build();
        Person2 person3 = new Person2.PersonBuilder().age(15).money(100).name("王五").build();
        Person2 person4 = new Person2.PersonBuilder().age(15).money(100).name("老六").build();
        Person2[] people = new Person2[]{person1, person2, person3, person4};
        Arrays.sort(people, (p1,p2)->{
            int moneyCompare = Double.compare(p2.getMoney(), p1.getMoney());
            if (moneyCompare != 0) {
                return moneyCompare;
            }
            int ageCompare = Integer.compare(p1.getAge(), p2.getAge());
            if (ageCompare != 0) {
                return ageCompare;
            }
            int nameCompare = p2.getName().compareTo(p1.getName());
            return nameCompare;
        });
        System.out.println(Arrays.toString(people));
    }

}

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
class Person2 {
    public Person2(PersonBuilder personBuilder) {
        this.age = personBuilder.age;
        this.money = personBuilder.money;
        this.name = personBuilder.name;
    }

    private int age;
    private double money;
    private String name;

    public static class PersonBuilder {
        private int age;
        private double money;
        private String name;

        public PersonBuilder age(int age) {
            this.age = age;
            return this;
        }

        public PersonBuilder money(double money) {
            this.money = money;
            return this;
        }

        public PersonBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Person2 build() {
            return new Person2(this);
        }
    }
}
