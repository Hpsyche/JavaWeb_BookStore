package cn.itcast.bookstore.order.service;

import cn.itcast.bookstore.order.dao.OrderDao;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.jdbc.JdbcUtils;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDao orderDao=new OrderDao();

    public void pay(String oid){
        /**
         * 1.获取订单状态
         *  如果为1，执行以下代码
         *  如果不为1，什么都不做
         */
        int state=orderDao.getStateByOid(oid);
        if(state==1){
            //修改订单状态为2
            orderDao.updateState(oid,2);
        }
    }

    //添加订单
    public void add(Order order){
        try{
            JdbcUtils.beginTransaction();

            orderDao.addOrder(order);
            orderDao.addOrderItem(order.getOrderItemList());

            JdbcUtils.commitTransaction();
        }catch (Exception e){
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }

        }
    }

    //我的订单
    public List<Order> myOrders(String uid) {
        return orderDao.findOrdersByUid(uid);
    }

    //加载订单
    public Order load(String oid) {
        return orderDao.load(oid);
    }

    //确认收货
    public void confirm(String oid) throws OrderException {
        int state=orderDao.getStateByOid(oid);
        if(state!=3){
            throw new OrderException("订单确认失败！！！");
        }else{
            orderDao.updateState(oid,4);
        }
    }

    public List<Order> allOrders() {
        return orderDao.allOrders();
    }

    public void send(String oid){
        orderDao.updateState(oid,3);
    }

    public List<Order> findOrdersByState(int state) {
        return orderDao.findOrdersByState(state);
    }
}
