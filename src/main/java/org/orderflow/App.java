package org.orderflow;

import org.orderflow.domain.Order;
import org.orderflow.domain.OrderItem;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List<OrderItem> items = new ArrayList<>();
//       OrderItem item = new OrderItem("C100", 5);
//       items.add(item);
        items.add(new OrderItem("C100",5));
        items.add(new OrderItem("c101",7));

        Order order = new Order("U01", items);
        items.clear();
        // Clears only the caller's original list.
        // Order has its own defensive copy.

//        order.getItems().clear();
        // This would throw UnsupportedOperationException because
        // getItems() exposes an unmodifiable list.
        
        System.out.println(order);

        for (OrderItem item: order.getItems()) {
            System.out.println(item.getProductId());
        }
    }
}
