package com.loopeer.addresspicker;

import java.util.List;

public class Address {

    public List<Province> cityList;


    public class Province {
        public String p;
        public List<City> c;


        public class City {
            public String n;
            public List<District> a;


            public class District {
                public String s;
            }
        }
    }
}
