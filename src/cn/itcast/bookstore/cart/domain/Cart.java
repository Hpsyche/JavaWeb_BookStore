package cn.itcast.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<String,CartItem> map=new LinkedHashMap<String, CartItem>();

    //计算合计
    //免去二进制误差
    public double getTotal(){
        BigDecimal total=new BigDecimal("0");
        for(CartItem cartItem:map.values()){
            BigDecimal subtotal=new BigDecimal(""+cartItem.getSubtotal());
            total=total.add(subtotal);
        }
        return total.doubleValue();
    }

    //添加条目
    public void add(CartItem cartItem){
         if(map.containsKey(cartItem.getBook().getBid())){
             CartItem cartItem1=map.get(cartItem.getBook().getBid());
             cartItem1.setCount(cartItem1.getCount()+cartItem.getCount());
             map.put(cartItem.getBook().getBid(),cartItem1);
         }else{
             map.put(cartItem.getBook().getBid(),cartItem);
         }
    }

    //清空条目
    public void clear(){
        map.clear();
    }

    //删除条目
    public void delete(String bid){
        map.remove(bid);
    }

    public Collection<CartItem> getCartItems(){
        return map.values();
    }

}
