package cn.itcast.bookstore.order.dao;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.bookstore.order.domain.OrderItem;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDao {
    private QueryRunner qr=new TxQueryRunner();
    //添加订单
    public void addOrder(Order order){
        String sql="insert into orders values(?,?,?,?,?,?)";
        /**
         * 处理util的Date转换成sql的TimeStamp
         */
        Timestamp timestamp=new Timestamp(order.getOrdertime().getTime());
        Object [] params={order.getOid(),timestamp,order.getTotal(),order.getState(),order.getFrom().getUid(),order.getAddress()};
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //添加订单条目
    public void addOrderItem(List<OrderItem> orderItems){
        String sql="insert into orderitem values(?,?,?,?,?)";
        Object params [][]=new Object[orderItems.size()][];
        for(int i=0;i<orderItems.size();i++){
            OrderItem item=orderItems.get(i);
            params[i]=new Object[]{item.getIid(),item.getCount(),item.getSubtotal(),item.getOrder().getOid(),item.getBook().getBid()};
        }
        try {
            qr.batch(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //根据uid查询订单
    //  循环遍历每个Order，为其加载所有的OrderItem
    public List<Order> findOrdersByUid(String uid){
        String sql="select * from orders where uid=?";
        try {
            List<Order> list= qr.query(sql,new BeanListHandler<Order>(Order.class),uid);
            for(Order order:list){
                findOrderItemsByOrder(order);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //通过order查询orderItems
    private void findOrderItemsByOrder(Order order){
        String sql="select * from orderitem i,book b where i.bid=b.bid and oid=?";
        try {
            //返回的为两个表，不是一个javaBean对象，因此用MapListHandler
            List<Map<String,Object>> list=qr.query(sql,new MapListHandler(),order.getOid());
            List<OrderItem> orderItemList=toOrderItemList(list);
            order.setOrderItemList(orderItemList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private List<OrderItem> toOrderItemList(List<Map<String, Object>> list) {
        List<OrderItem> orderItemList=new ArrayList<OrderItem>();
        for(Map<String,Object> map:list){
            OrderItem item=toOrderItem(map);
            orderItemList.add(item);
        }
        return orderItemList;
    }

    private OrderItem toOrderItem(Map<String, Object> map) {
        OrderItem orderItem=CommonUtils.toBean(map,OrderItem.class);
        Book book=CommonUtils.toBean(map,Book.class);
        orderItem.setBook(book);
        return orderItem;
    }

    //加载订单
    public Order load(String oid) {
        String sql="select * from orders where oid=?";
        try {
            Order order=qr.query(sql,new BeanHandler<Order>(Order.class),oid);
            findOrderItemsByOrder(order);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //通过oid获取订单状态
    public int getStateByOid(String oid){
        String sql="select * from orders where oid=?";
        try {
            Order order=qr.query(sql,new BeanHandler<Order>(Order.class),oid);
            return order.getState();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //修改订单状态
    public void updateState(String oid,int state){
        String sql="update orders set state=? where oid=?";
        Object [] params={state,oid};
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> allOrders() {
        String sql="select * from orders";
        try {
            List<Order> list=qr.query(sql,new BeanListHandler<Order>(Order.class));
            for(Order order:list){
                findOrderItemsByOrder(order);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> findOrdersByState(int state) {
        String sql="select * from orders where state=?";
        try {
            List<Order> list=qr.query(sql,new BeanListHandler<Order>(Order.class),state);
            for(Order order:list){
                findOrderItemsByOrder(order);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
