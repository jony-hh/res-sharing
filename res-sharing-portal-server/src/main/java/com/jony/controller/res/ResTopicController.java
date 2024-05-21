package com.jony.controller.res;


import com.github.yitter.idgen.YitIdHelper;
import com.jony.api.CommonResult;
import com.jony.convert.ResTopicConvert;
import com.jony.dto.ResTopicDTO;
import com.jony.entity.ResTopic;
import com.jony.mapper.ResTopicMapper;
import com.jony.service.ResTopicService;
import com.jony.service.impl.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * resTopic表-控制器层
 *
 * @author jony
 * @since 2024-03-12 12:27:31
 */
@RestController
@RequestMapping("res/topic")
@Tag(name = "元素话题接口")
public class ResTopicController {

    @Resource
    private ResTopicService resTopicService;
    @Resource
    private ResTopicMapper resTopicMapper;

    @GetMapping("/fetchWhole")
    @Operation(summary = "取得所有话题元素数据")
    public CommonResult<?> fetchWhole() {
        List<ResTopic> documentList = resTopicService.fetchWhole();
        return CommonResult.success(documentList);
    }

    @GetMapping("/pagingQuery")
    @Operation(summary = "分页查询话题元素数据")
    public CommonResult<?> pagingQuery(@RequestParam("page_size") Integer pageSize, @RequestParam("page_num") Integer pageNum) {
        List<ResTopic> documentList = resTopicService.pagingQuery(pageSize, pageNum);
        return CommonResult.success(documentList);
    }

    @GetMapping("/single")
    @Operation(summary = "根据id查询单个话题元素数据")
    public CommonResult<?> fetchById(@RequestParam("id") Long id) {
        ResTopic documentList = resTopicService.fetchById(id);
        return CommonResult.success(documentList);
    }

    // region

    @PostMapping
    @Operation(summary = "新增话题元素数据")
    public CommonResult<?> add(@RequestBody ResTopicDTO resTopicDTO) {
        ResTopic resTopic = ResTopicConvert.INSTANCE.toResTopic(resTopicDTO);
        resTopic.setId(YitIdHelper.nextId());
        int insert = resTopicMapper.insert(resTopic);
        return CommonResult.success(insert > 0 ? "success" : "failed");
    }


    // endregion

}

