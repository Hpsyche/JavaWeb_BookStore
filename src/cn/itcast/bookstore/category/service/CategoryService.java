package cn.itcast.bookstore.category.service;

import java.util.List;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.category.dao.CategoryDao;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.web.servlet.admin.CategoryException;

public class CategoryService {
	private CategoryDao categoryDao = new CategoryDao();
	private BookDao bookDao=new BookDao();
	/**
	 * 查询所有分类
	 * @return
	 */
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	public void add(Category category) throws CategoryException {
		if(categoryDao.ifCategory(category.getCname())==null){
			categoryDao.add(category);
		}else{
			throw new CategoryException();
		}
	}

	public void delete(String cid) throws CategoryException {
		int count=bookDao.getCountByCid(cid);
		if(count==0){
			categoryDao.delete(cid);
		}else{
			throw new CategoryException("该分类下有图书，无法删除");
		}
	}

	public Category load(String cid) {
		return categoryDao.load(cid);
	}

	public void edit(Category category) {
		categoryDao.edit(category);
	}
}
