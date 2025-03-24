package com.example.groupproject;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.example.groupproject.loadDataFunction.FoodData;
import com.example.groupproject.p2pFunction.DataAdapter;

import java.util.ArrayList;

/**
 * Test if the DataAdapter works rightly
 *
 * @author ${Wendi Fan}
 * @studentId ${u7041989}
 */

public class DataAdapterTest {
    private DataAdapter adapter;
    private ArrayList<FoodData> data;

    @Before
    public void setUp() {
        data = new ArrayList<>();

        adapter = new DataAdapter(null, data);
    }

    @Test
    public void testAdapterData() {

        FoodData item1 = new FoodData();
        item1.productName = "Apple";
        item1.category = "Fruit";
        item1.date = "2024-05-09";
        item1.expiration = "2024-06-09";
        item1.provider = "Provider1";
        item1.address = "123 Orchard Street";
        item1.offer = "Special offer";
        item1.eventName = "Apple Festival";
        item1.phoneNumber = "123-456-7890";

        FoodData item2 = new FoodData();
        item2.productName = "Broccoli";
        item2.category = "Vegetable";
        item2.date = "2024-05-10";
        item2.expiration = "2024-06-10";
        item2.provider = "Provider2";
        item2.address = "456 Green Street";
        item2.offer = "Discount offer";
        item2.eventName = "Veggie Fair";
        item2.phoneNumber = "234-567-8901";

        data.add(item1);
        data.add(item2);

        assertEquals(2, adapter.getItemCount());

        assertEquals("Apple", data.get(0).getProductName());
        assertEquals("Broccoli", data.get(1).getProductName());
    }
}
