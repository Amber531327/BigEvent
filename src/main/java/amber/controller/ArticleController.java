package amber.controller;

import amber.pojo.Article;
import amber.pojo.PageBean;
import amber.pojo.Result;
import amber.service.ArticleService;
import amber.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }
    @GetMapping
    public Result<PageBean<Article>> list(
          Integer pageNum,
          Integer pageSize,
          @RequestParam(required = false) Integer categoryId,
          @RequestParam(required = false) String state
    )
    {
        PageBean<Article> pb=articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }

    @PutMapping
    public Result update(@RequestBody @Validated Article article) {
        articleService.update(article);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam Integer id) {
        articleService.delete(id);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<Article> detail(@RequestParam Integer id) {
        Article article=articleService.detail(id);
        return Result.success(article);
    }
}
