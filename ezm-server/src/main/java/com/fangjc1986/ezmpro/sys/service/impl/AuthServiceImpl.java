package com.fangjc1986.ezmpro.sys.service.impl;

import com.fangjc1986.ezmpro.sys.mapper.AuthMapper;
import com.fangjc1986.ezmpro.sys.model.Auth;
import com.fangjc1986.ezmpro.sys.service.IAuthService;
import com.fangjc1986.support.entity.IdsForm;
import com.fangjc1986.support.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author EricFang
 * @since 2020-03-18
 */
@Service
public class AuthServiceImpl extends BaseService<AuthMapper, Auth> implements IAuthService {

    /**
     * 一键添加 CRUD
     *
     * @param idsForm
     * @return
     */
    public boolean addCRUDByIds(IdsForm idsForm) {
        List<Auth> auths = (List<Auth>) listByIds(idsForm.getIds());
        List<Auth> addAuths = new ArrayList<>();
        for (Auth auth : auths) {
            addAuths.addAll(createCRUDAuths(auth, idsForm.getP1(), idsForm.getP2()));
        }
        return saveBatch(addAuths);
    }

    /**
     * 创建 CRUD 权限列表
     *
     * @param parent
     * @param baseApi
     * @param baseString
     * @return
     */
    private List<Auth> createCRUDAuths(Auth parent, String baseApi, String baseString) {
        List<Auth> auths = new ArrayList<>();
        String[] apis = new String[]{"list", "get", "save", "updateList", "remove"};
        String[] name = new String[]{"列表", "详情", "增改", "批改", "删除"};
        for (int i = 0; i < apis.length; i++) {
            Auth auth = new Auth();
            auth.setName(baseString + "-" + name[i]);
            auth.setPath(baseApi + "/" + apis[i]);
            auth.setCategory(parent.getCategory());
            auth.setType(2);
            auth.setIcon("icon-folder1");
            auth.setPid(parent.getId());
            auths.add(auth);
        }
        return auths;
    }

}
