package com.zyy.study.alldemo.other.designPattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

public class Builder {
    public static void main(String[] args) {
        BuilderData builderData = new BuilderData().id(1).param1("p1").param2("p2");
        System.out.println(builderData);
        BuilderData2 build = new BuilderData2.DataBuilder().id(2).param1("p3").param2("p4").build();
        System.out.println(build);
    }
}

/**
 * 构建者模式实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class BuilderData {
    private Integer id;
    private String param1;
    private String param2;

    public BuilderData id(Integer id) {
        this.id=id;
        return this;
    }

    public BuilderData param1(String param1) {
        this.param1 = param1;
        return this;
    }

    public BuilderData param2(String param2) {
        this.param2 = param2;
        return this;
    }
}
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class BuilderData2{
    private Integer id;
    private String param1;
    private String param2;

    public BuilderData2(DataBuilder dataBuilder) {
        this.id = dataBuilder.id;
        this.param1 = dataBuilder.param1;
        this.param2 = dataBuilder.param2;
    }

    public static class DataBuilder{
        private Integer id;
        private String param1;
        private String param2;

        public DataBuilder id(Integer id) {
            this.id = id;
            return this;
        }
        public DataBuilder param1(String param1) {
            this.param1 = param1;
            return this;
        }

        public DataBuilder param2(String param2) {
            this.param2 = param2;
            return this;
        }

        public BuilderData2 build() {
            return new BuilderData2(this);
        }
    }
}
