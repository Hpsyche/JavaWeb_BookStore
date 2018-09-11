package cn.itcast.bookstore.order.web.servlet.admin;

import cn.itcast.bookstore.order.service.OrderService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
    private OrderService orderService=new OrderService();
    //查询所有订单
    public String allOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("orderList",orderService.allOrders());
        return "f:/adminjsps/admin/order/list.jsp";
    }
    public String send(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oid = request.getParameter("oid");
        orderService.send(oid);
        request.setAttribute("orderList",orderService.allOrders());
        return "f:/adminjsps/admin/order/list.jsp";
    }
    public String findOrdersByState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String state=request.getParameter("state");
        request.setAttribute("orderList",orderService.findOrdersByState(Integer.parseInt(state)));
        return "f:/adminjsps/admin/order/list.jsp";
    }

}
