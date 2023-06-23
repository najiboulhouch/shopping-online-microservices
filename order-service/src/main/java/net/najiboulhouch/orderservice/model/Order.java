package net.najiboulhouch.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String orderNumber ;
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "order")
    private Set<OrderLineItems> orderLineItems = new HashSet<>();

    public void add(OrderLineItems item){
        if (item != null){
            if(orderLineItems == null){
                orderLineItems = new HashSet<>();
            }

            orderLineItems.add(item);
            item.setOrder(this);
        }
    }

}
