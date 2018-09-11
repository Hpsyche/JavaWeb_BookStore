package cn.itcast.bookstore.category.web.servlet.admin;

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

@WebServlet("/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("categoryList", categoryService.findAll());
        return "f:/adminjsps/admin/category/list.jsp";
    }

    public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        category.setCid(CommonUtils.uuid());
        try {
            categoryService.add(category);
        } catch (CategoryException e) {
            request.setAttribute("msg", "已有此类！添加失败！");
            return "f:/adminjsps/admin/category/add.jsp";
        }
        return findAll(request, response);
    }

    public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        try {
            categoryService.delete(cid);
            return findAll(request, response);
        } catch (CategoryException e) {
            request.setAttribute("msg", e.getMessage());
            return "f:/adminjsps/msg.jsp";
        }
    }

    public String editPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        request.setAttribute("category", categoryService.load(cid));
        return "f:/adminjsps/admin/category/mod.jsp";
    }

    public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Category category=CommonUtils.toBean(request.getParameterMap(),Category.class);
        categoryService.edit(category);
        return findAll(request,response);
    }
}

