package com.jony.controller.res;


import com.github.yitter.idgen.YitIdHelper;
import com.jony.api.CommonResult;
import com.jony.dto.WikiBookDTO;
import com.jony.entity.ResBook;
import com.jony.service.ResBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * resBook表-控制器层
 *
 * @author jony
 * @since 2024-03-14 19:03:53
 */
@RestController
@RequestMapping("wiki/book")
@Tag(name = "wiki书籍")
public class ResBookController {

    @Resource
    private ResBookService resBookService;

    @GetMapping("/fetchWhole")
    @Operation(summary = "取得所有wiki书籍")
    public CommonResult<?> fetchWhole() {
        List<ResBook> questionList = resBookService.fetchWhole();
        return CommonResult.success(questionList);
    }

    @GetMapping("/pagingQuery")
    @Operation(summary = "分页查询wiki书籍")
    public CommonResult<?> pagingQuery(@RequestParam("page_size") Integer pageSize, @RequestParam("page_num") Integer pageNum) {
        List<ResBook> questionList = resBookService.pagingQuery(pageSize, pageNum);
        return CommonResult.success(questionList);
    }


    @PostMapping("/write")
    @Operation(summary = "写wiki")
    public CommonResult<?> askForHelp(@RequestBody WikiBookDTO wikiBookDTO) {
        ResBook book = new ResBook();
        long id = YitIdHelper.nextId();
        book.setId(id);
        book.setUserId(wikiBookDTO.getUserId());
        book.setContent(wikiBookDTO.getContent());
        book.setCoverUrl(wikiBookDTO.getCoverUrl());
        book.setSummary(wikiBookDTO.getSummary());
        String resultMessage = resBookService.addWikiBook(book);
        return CommonResult.success(book, resultMessage);
    }

    @GetMapping("/fetchById")
    @Operation(summary = "根据id查询单个wiki书籍")
    public CommonResult<?> fetchById(@RequestParam("id") Long id) {
        ResBook question = resBookService.fetchById(id);
        return CommonResult.success(question);
    }
}

