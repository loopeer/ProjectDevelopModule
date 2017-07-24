package com.loopeer.addresspicker;

import java.util.List;

public class Address {

    public List<Province> cityList;

    public static class Province {
        public String p;
        public String id;
        public List<City> c;

        public Province() {
        }

        public Province(String p, String id, List<City> c) {
            this.p = p;
            this.id = id;
            this.c = c;
        }

        public static class City {
            public String n;
            public String id;
            public List<District> a;

            public City() {
            }

            public City(String n, String id, List<District> a) {
                this.n = n;
                this.id = id;
                this.a = a;
            }

            public static class District {
                public District() {
                }

                public District(String s, String id) {
                    this.s = s;
                    this.id = id;
                }

                public String s;
                public String id;
            }
        }
    }
}
