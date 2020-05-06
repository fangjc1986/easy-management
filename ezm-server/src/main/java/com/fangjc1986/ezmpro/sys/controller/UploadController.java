package  com.fangjc1986.ezmpro.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fangjc1986.ezmpro.sys.service.impl.UploadServiceImpl;
import com.fangjc1986.ezmpro.sys.model.Upload;
import com.fangjc1986.support.controller.BaseController;
import com.fangjc1986.support.entity.IdsForm;
import com.fangjc1986.support.entity.R;
import com.fangjc1986.support.util.ConditionUtil;
import com.fangjc1986.support.util.ValidateUtil;
import com.fangjc1986.support.util.EPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.List;


/**
 * @author EricFang
 * @since 2020-04-02
 */

@RestController
@AllArgsConstructor
@Api(description = "UploadController")
@RequestMapping("/sys/upload")
public class UploadController extends BaseController {

    private UploadServiceImpl service;

    @GetMapping("/list")
    @ApiOperation(value = "查询列表")
    R<IPage<Upload>> list(@RequestParam Map<String, Object> filter, EPage<Upload> page) {
        return R.data(
            service.page(
                page,
                ConditionUtil.getQW(filter, Upload.class)
            )
        );
    }

    @GetMapping("/get")
    @ApiOperation(value = "id 查询")
    R<Upload> get(Long id) {
        return R.data(
            service.getById(id)
        );
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    R<Upload> save(@RequestBody @Validated Upload form) {
        return saveOrUpdate(service, form);
    }

    @PostMapping("/updateList")
    @ApiOperation(value = "批量保存")
    R<String> updateList(@RequestBody @Validated List<Upload> list) {
        ValidateUtil.validate(list);
        return R.status(
                service.updateBatchById(list)
            );
    }

    @PostMapping("/remove")
    @ApiOperation(value = "ids 批量删除")
    R<String> remove(@RequestBody @Validated IdsForm idsForm) {
        return deleteCount(
            service.getBaseMapper().deleteBatchIds(idsForm.getIds())
        );
    }

}

