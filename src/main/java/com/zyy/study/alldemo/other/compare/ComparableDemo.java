package com.zyy.study.alldemo.other.compare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class ComparableDemo {
    public static void main(String[] args) {
        Person person1 = new Person.PersonBuilder().age(10).money(100).name("张三").build();
        Person person2 = new Person.PersonBuilder().age(10).money(200).name("李四").build();
        Person person3 = new Person.PersonBuilder().age(15).money(100).name("王五").build();
        Person person4 = new Person.PersonBuilder().age(15).money(100).name("老六").build();
        Person[] people = new Person[]{person1, person2, person3, person4};
        Arrays.sort(people);
        System.out.println(Arrays.toString(people));
    }
}

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
class Person implements Comparable<Person> {
    public Person(PersonBuilder personBuilder) {
        this.age = personBuilder.age;
        this.money = personBuilder.money;
        this.name = personBuilder.name;
    }

    private int age;
    private double money;
    private String name;

    /**
     * 从上往下排
     * 钱多排在前，钱一样的，年龄小的排在前。都一样的，姓名排在前
     *
     * @param person
     * @return
     */
    @Override
    public int compareTo(Person person) {
        int moneyCompare = Double.compare(person.money, this.money);
        if (moneyCompare != 0) {
            return moneyCompare;
        }
        int ageCompare = Integer.compare(this.age, person.age);
        if (ageCompare != 0) {
            return ageCompare;
        }
        int nameCompare = person.name.compareTo(this.name);
        return nameCompare;
    }

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

        public Person build() {
            return new Person(this);
        }
    }
}
