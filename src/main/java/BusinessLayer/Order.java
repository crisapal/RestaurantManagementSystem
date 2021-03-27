package BusinessLayer;

import java.util.Date;
import java.util.Objects;

public class Order {
    private int orderId;
    private Date date = new Date();
    private int tableNumber;

    public Order(int orderId, Date date, int tableNumber) {
        this.orderId = orderId;
        this.date = date;
        this.tableNumber = tableNumber;
    }

    @Override
    public int hashCode() { ///generate it with an increasing factor of random in order to ensure unicity
        int hashcode = 13; ///some prime number
        hashcode += hashcode * 7 + 29 * orderId + 3 * date.getTime() + 5 * tableNumber;
        return hashcode;
    }


    public int getOrderId() {
        return orderId;
    }

    public Date getDate() {
        return date;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", date=" + date +
                ", tableNumber=" + tableNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                tableNumber == order.tableNumber &&
                Objects.equals(date, order.date);
    }
}
