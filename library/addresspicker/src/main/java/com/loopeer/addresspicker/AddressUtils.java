package com.loopeer.addresspicker;

import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class AddressUtils {

    private Address mAddress;


    private AddressUtils() {
        Gson gson = new Gson();
        String file = "assets/city.json";
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
        mAddress = gson.fromJson(new InputStreamReader(in), Address.class);
    }


    protected static class InstanceHolder {
        private static AddressUtils INSTANCE = new AddressUtils();
    }


    public static AddressUtils getInstance() {
        return InstanceHolder.INSTANCE;
    }


    public String[] getProvinces(Address address) {
        List<Address.Province> provinceList = address.cityList;
        if (provinceList == null) return new String[] { " " };
        int len = provinceList.size();
        String[] provinces = new String[len];
        for (int i = 0; i < len; i++) {
            provinces[i] = provinceList.get(i).p;
        }
        return provinces;
    }


    public String[] getCities(Address.Province province) {
        List<Address.Province.City> cityList = province.c;
        if (cityList == null) return new String[] { " " };
        int len = cityList.size();
        String[] cities = new String[len];

        for (int i = 0; i < len; i++) {
            cities[i] = cityList.get(i).n;
        }

        return cities;
    }


    public String[] getDistricts(Address.Province.City city) {
        List<Address.Province.City.District> districtList = city.a;
        if (districtList == null) return new String[] { " " };
        int len = districtList.size();
        String[] districts = new String[len];

        for (int i = 0; i < len; i++) {
            districts[i] = districtList.get(i).s;
        }

        return districts;
    }


    public Address getAddress() {
        return mAddress;
    }
}
