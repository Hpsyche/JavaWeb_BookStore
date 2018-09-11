package cn.itcast.bookstore.cart.web.servlet;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.cart.domain.Cart;
import cn.itcast.bookstore.cart.domain.CartItem;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {

    //添加购物条目
    public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到购物车
        Cart cart= (Cart) request.getSession().getAttribute("cart");
        if(cart==null){
            request.setAttribute("msg","尚未登录！！");
            return "f:/jsps/body.jsp";
        }
        //得到条目
        String bid=request.getParameter("bid");
        Book book= new BookService().load(bid);
        int count= Integer.parseInt(request.getParameter("count"));
        CartItem cartItem=new CartItem();
        cartItem.setBook(book);
        cartItem.setCount(count);
        cart.add(cartItem);
        return "f:/jsps/cart/list.jsp";
    }

    //清空购物条目
    public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart= (Cart) request.getSession().getAttribute("cart");
        cart.clear();
        return "f:/jsps/cart/list.jsp";

    }
      public String del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart= (Cart) request.getSession().getAttribute("cart");
        cart.delete(request.getParameter("bid"));
        return "f:/jsps/cart/list.jsp";

    }
}
