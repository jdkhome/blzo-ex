package com.jdkhome.blzo.ex.authj.core;

import com.google.gson.reflect.TypeToken;
import com.jdkhome.blzo.ex.authj.enums.AuthjResponseError;
import com.jdkhome.blzo.ex.authj.generator.model.Admin;
import com.jdkhome.blzo.ex.authj.generator.model.Group;
import com.jdkhome.blzo.ex.authj.generator.model.GroupAdmin;
import com.jdkhome.blzo.ex.authj.generator.model.GroupAuth;
import com.jdkhome.blzo.ex.authj.pojo.dto.LayerDTO;
import com.jdkhome.blzo.ex.authj.service.AdminBasicService;
import com.jdkhome.blzo.ex.authj.service.GroupBasicService;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.utils.tools.gson.PerfectGson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jdk on 18/1/6.
 * 权限业务
 */
@Slf4j
@Component
public class AuthjService {

    @Autowired
    AdminBasicService adminBasicService;

    @Autowired
    GroupBasicService groupBasicService;

    @Autowired
    AuthjCache authjCache;

    /**
     * 初始化管理员菜单layer
     *
     * @param adminId
     */
    public void initLayer(Integer adminId) {

        // 拿到该用所在的组
        List<GroupAdmin> groupAdmins = groupBasicService.getGroupAdminByAdminId(adminId);

        List<LayerDTO> list = new ArrayList<>(groupAdmins.size());

        LayerDTO dto = new LayerDTO();
        dto.setName("公共");
        dto.setUris(new ArrayList<>(this.allCommonMenuUri()));
        list.add(dto);

        groupAdmins.forEach(groupAdmin -> {
            LayerDTO layer = new LayerDTO();
            Group group = groupBasicService.getGroupById(groupAdmin.getGroupId());
            layer.setName(group.getName());
            Set<String> groupUris = this.getValidAuth(groupAdmin.getGroupId(), groupAdmin.getGroupId());
            List<String> uris = new ArrayList<>();
            groupUris.stream()
                    .filter(uri -> authjCache.hasAuthj(uri) && authjCache.getAuthj(uri).menu)
                    .forEach(uris::add);
            layer.setUris(uris);
            if (!CollectionUtils.isEmpty(uris)) {
                list.add(layer);
            }
        });


        adminBasicService.editAdmin(adminId, null, null, null, null, null, null, null, list);
    }

    private Set<String> allCommonMenuUri() {
        Set<String> set = new HashSet<>();
        authjCache.getAuthjBeans().stream().filter(authjBean -> authjBean.getCommon() && authjBean.getMenu())
                .forEach(authjBean -> set.add(authjBean.uri));

        return set;
    }

    /**
     * 加载菜单 这里通过UserAuthjConfBean来传结构体
     *
     * @param layers
     * @param all
     * @return
     */
    private UserAuthjConfBean reloadMenus(List<LayerDTO> layers, Set<String> all) {

        UserAuthjConfBean bean = new UserAuthjConfBean();

        List<MenuVO> menus = new ArrayList<>();

        // 记录所有配置中有的uri
        Set<String> hasLayerUri = new HashSet<>();

        if (!CollectionUtils.isEmpty(layers)) {
            layers.forEach(layer -> {
                MenuVO menu = new MenuVO();
                menu.setName(layer.getName());

                List<AuthjBean> list = new ArrayList<>();
                layer.getUris().stream()
                        .filter(uri -> authjCache.hasAuthj(uri) && authjCache.getAuthj(uri).menu)
                        .forEachOrdered(uri -> {
                            list.add(authjCache.getAuthj(uri));
                            hasLayerUri.add(uri);
                        });
                menu.setMenus(list);
                menus.add(menu);
            });
        }

        MenuVO noLayerMenus = new MenuVO();
        noLayerMenus.setName("未分组");
        List<AuthjBean> list = new ArrayList<>();

        Set<String> thisAll = new HashSet<>(all.size());
        thisAll.addAll(all);
        thisAll.addAll(this.allCommonMenuUri());
        thisAll.removeAll(hasLayerUri);
        thisAll.stream()
                .filter(uri -> authjCache.hasAuthj(uri) && authjCache.getAuthj(uri).menu)
                .forEach(uri -> list.add(authjCache.getAuthj(uri)));
        noLayerMenus.setMenus(list);
        if (!CollectionUtils.isEmpty(list)) {
            bean.setMoreMenu(noLayerMenus);
        } else {
        }

        bean.setLayerList(menus);


        return bean;

    }

    /**
     * 获取管理员的权限集合
     *
     * @param thisAdminId 当前查找AdminId
     * @param stopAdminId 终止查找AdminId(防止循环授权)
     * @return
     */
    public Set<String> getAdminAuth(Integer thisAdminId, Integer stopAdminId) {
        /**
         * 如果是root用户拥有全部权限
         */
        if (AuthjConstants.ROOT_ID.equals(thisAdminId)) {
            return authjCache.getUris();
        }

        // 拿到该用所在的组
        List<GroupAdmin> groupAdmins = groupBasicService.getGroupAdminByAdminId(thisAdminId);

        // 改用户拥有的所有组的有效权限 并集 即为该用户的权限集合
        Set<String> result = new HashSet<>();
        // 过滤掉种植查找创建的权限组 下一级查找时也设置同样的stop
        groupAdmins.stream()
                .filter(groupAdmin -> !groupAdmin.getCreateAdminId().equals(thisAdminId))
                .filter(groupAdmin -> !groupAdmin.getCreateAdminId().equals(stopAdminId))
                .forEach(groupAdmin -> result.addAll(this.getValidAuth(groupAdmin.getGroupId(), stopAdminId)));

        result.addAll(this.allCommonMenuUri());

        return result;
    }

    /**
     * 获取组的有效权限集合
     *
     * @param groupId
     * @return
     */
    public Set<String> getValidAuth(Integer groupId, Integer stopAdminId) {

        Group group = groupBasicService.getGroupById(groupId);

        /**
         * 改组登记的权限集合
         */
        List<GroupAuth> groupAuths = groupBasicService.getGroupAuthByGroupId(groupId);
        Set<String> groupAuthUriSet = new HashSet<>(groupAuths.size());
        groupAuths.forEach(groupAuth -> groupAuthUriSet.add(groupAuth.getUri()));

        /**
         * 和组创建者的权限取交集
         */
        groupAuthUriSet.retainAll(this.getAdminAuth(group.getCreateAdminId(), stopAdminId));

        return groupAuthUriSet;
    }

    public UserAuthjConfBean getUserAuthjConf(Integer userId) {

        UserAuthjConfBean userAuthjConfBean = new UserAuthjConfBean();

        Admin admin = adminBasicService.getAdminById(userId);

        userAuthjConfBean.setId(admin.getId());
        userAuthjConfBean.setName(admin.getNickName());

        userAuthjConfBean.setAuthUriSet(this.getAdminAuth(userId, userId));

        UserAuthjConfBean tempBean = this.reloadMenus(PerfectGson.getGson().fromJson(admin.getLayer(),
                new TypeToken<List<LayerDTO>>() {
                }.getType()), userAuthjConfBean.getAuthUriSet());

        userAuthjConfBean.setLayerList(tempBean.layerList);
        userAuthjConfBean.setMoreMenu(tempBean.moreMenu);

        // 2019年03月07日  增加组织Id
        if (admin.getOrganizeId() == null) {
            log.error("[Authj] -> 无法给无组织用户授权! adminId={}", admin.getId());
            throw new ServiceException(AuthjResponseError.ADMIN_HAVENOT_ORG);
        }
        userAuthjConfBean.setOrganizeId(admin.getOrganizeId());


        return userAuthjConfBean;
    }
}
