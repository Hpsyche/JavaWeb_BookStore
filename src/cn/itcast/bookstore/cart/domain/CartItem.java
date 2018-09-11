package cn.itcast.bookstore.cart.domain;

import cn.itcast.bookstore.book.domain.Book;

import java.math.BigDecimal;

public class CartItem {
    private Book book;
    private int count;

    //小计方法！处理了二进制运算误差问题
    public double getSubtotal(){
        BigDecimal d1=new BigDecimal(book.getPrice()+"");
        BigDecimal d2=new BigDecimal(count+"");
        return d1.multiply(d2).doubleValue();
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "book=" + book +
                ", count=" + count +
                '}';
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
