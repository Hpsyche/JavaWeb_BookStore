package cn.itcast.bookstore.book.web.servlet.admin;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
    private BookService bookService=new BookService();
    private CategoryService categoryService=new CategoryService();
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> bookList=bookService.findAll();
        request.setAttribute("bookList",bookList);
        return "f:/adminjsps/admin/book/list.jsp";
    }
    public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bid=request.getParameter("bid");
        Book book=bookService.load(bid);
        request.setAttribute("book",book);
        //获取所有分类
        request.setAttribute("categoryList",categoryService.findAll());
        return "f:/adminjsps/admin/book/desc.jsp";
    }

    //查询所有分类
    public String addPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> list=categoryService.findAll();
        request.setAttribute("categoryList",list);
        return "f:/adminjsps/admin/book/add.jsp";
    }

    //删除图书
    public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bid=request.getParameter("bid");
        bookService.delete(bid);
        return findAll(request,response);
    }

    //编辑图书
    public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Book book= CommonUtils.toBean(request.getParameterMap(),Book.class);
        Category category=CommonUtils.toBean(request.getParameterMap(),Category.class);
        book.setCategory(category);
        bookService.edit(book);
        return findAll(request,response);
    }
}
