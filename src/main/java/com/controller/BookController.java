package com.controller;

import com.pojo.Books;
import com.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;

    /**
      * 查询所有书籍
      *
      * @Param model:
      * @return: java.lang.String
      **/
    @RequestMapping(value = "/allBook")
    public String list(Model model) {
        List<Books> list = bookService.queryAllBook();
        model.addAttribute("list", list);
        return "allBook";
    }


    /**
      * 添加书籍
      *
      * @return: java.lang.String
      **/
    @RequestMapping("/toAddBook")
    public String toAddPaper(){
        return "addBook";
    }

    //一定要重定向，否则跳转到allBook页面时没有数据
    @RequestMapping("/addBook")
    public String addBook(Books book){
        bookService.addBook(book);
        return "redirect:/book/allBook"; //重定向到我们的@RequestMapping(value = "/allBook")
    }


    /**
      * 修改数据
      *
      * @Param id:
     * @Param model:
      * @return: java.lang.String
      **/
    @RequestMapping("/toUpdateBook")
    public String toUpdatePaper(int id,Model model){
        Books book = bookService.queryBookById(id);
        model.addAttribute("QBook",book);
        return "updateBook";
    }

    @RequestMapping("/updateBook")
    public String updateBook(Books book){
        System.out.println("updateBook==>"+book);
        bookService.updateBook(book);
        return "redirect:/book/allBook";
    }


    /**
      * 删除用户
      *
      * @Param id:
      * @return: java.lang.String
      **/
    @RequestMapping("/deleteBook/{bookID}")
    public String deleteBook(@PathVariable("bookID") int id){
        bookService.deleteBookById(id);
        return "redirect:/book/allBook";
    }



    @RequestMapping("/queryBookByName")
    public String queryBookByName(Model model,String bookName){
        Books book = bookService.queryBookByName(bookName);
        List<Books> list = new ArrayList<Books>();
        list.add(book);
        if(book==null){
            list = bookService.queryAllBook();
            model.addAttribute("error","未查到");
        }
        model.addAttribute("list", list);
        return "allBook";
    }
}
