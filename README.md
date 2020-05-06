## Easy Management (EZM) 后台管理系统
 Easy Management (EZM) 后台管理系统是一整套全栈前后分离的 **免费开源** 中后台框架，可快速实现后台系统的用户权限、CRUD表格操作，帮助开发人员快速搭建基础后台功能。
### 预览
   DEMO演示地址：[http://ezm.demo.fangjc1986.com](http://ezm.demo.fangjc1986.com)
### 前端集成功能：
   * CRUD 界面可根据数据库表自动生成；
   * 独立部署，可根据自身需求独立使用；
   * 集成与本系统后端配套的 RBAC + 租户隔离管理界面；
   * 表格组件使用 vxe-table, 并根据约定可简易创建自定义 vxe-table 组件，由框架自动注入；
   * 实现窗口标签切换保持，关闭刷新功能，更贴近自然；
   * 提供常用开箱即用组件（分页选择，整体选择，字典选择，文件上传等等）；
   * 封装 axios 异步框架，自动识别后端 code 并处理；
   * 封装 mockjs 以快速模拟数据 axios 返回数据，用于前端单独调试；

### 后端集成功能：
   * Controller, Mapper, Model, Service 根据数据库表自动生成（可自行修改模板）；
   * 独立部署，可根据自行需求独立使用；
   * 集成与本系统前端配套的 RBAC + 租户隔离管理接口；
   * 数据库主从配置；
   * 集成 Mongodb 查询接口和返回格式与 MyBatis-plus 一致（前端无需修改）；
   * 集成自定义字段查询功能，类似（name_like, name_not_in, name_eq 等）；
   * 提供 redis 缓存，与 redis 锁功能；

### 前端技术栈：
   * 基础框架 : Vue.js + Vue-Router + Vuex 全家桶;
   * 基础 UI : element-ui;
   * 表格 : vxe-table;
   * 异步框架 : axios;
   * 调试模拟 : mockjs ;

### 后端技术栈：
   * 基础框架 : SpringBoot + Mybatis-Plus ;
   * 数据库 : Mysql , Mongodb, Redis ;
  

### CRUD 生成：
生成器位置： src/generator ；

配置需要生成的数据表以及不需要生成的字段：src/generator/GeneratorConfig.js
```javascript
module.exports = {
    excludeFields: ['id', 'tenant_id', 'create_user', 'create_time', 'update_time', 'update_user'],
    models: [
            {
               // 数据库表名
               tableName: 'sys_dict', 
               // api前缀
               apiPrefix: 'sys/dict',
               // 页面生成位置
               pagePath: 'base/dict',
            },
            {
               tableName: 'sys_dict_detail',
               apiPrefix: 'sys/dict-detail',
               pagePath: 'base/dict_detail',
            },
    ],
};
```

运行生成CRUD页面：src/generator/CodeGenerator.js
```javascript
let Mysql = require("./MysqlUtil");
let Config = require("./GeneratorConfig");
let Generator = require("./GeneratorPage");

// 数据库名称
const schema = 'ezmpro';

for (let model of Config.models) {
    let mysql = new Mysql();
    mysql.connection.query(
        `select * from information_schema.COLUMNS where table_name = '${model.tableName}' and table_schema = '${schema}'`,
        (error, results, fields) => {
            let generator = new Generator(results, model);
            generator.generatorPage();
            generator.generatorApi();
            generator.generatorEdit();
            generator.generatorFilter();
            generator.generatorConfigMixin();
            generator.generatorSelector();
            mysql.connection.end();
        }
    );
}
```
后端生成CRUD接口生成器：com.fangjc1986.generator.CodeGenerator 

Controller 模板位置： template/code_generator/controller.java.vm

> 如需自定义其他 Service, Mapper, Model 等模板，请自行查看 Mybatis-Plus 手册；


### CRUD 表格使用：

- 用生成器生成一个 入库单 的 CRUD 页面：

  生成 5 个文件： receipt.vue, receipt_edit.vue, receipt_filter.vue, receipt_selector.vue, ReceiptConfigMixin.js

  只需编辑 ReceiptConfigMixin.js 即可完成大部分功能；
  > 大多数参数可不用填写；


```javascript
editSpan: {}, // 编辑窗口默认 span 值
defaultEditGroupName: '', // 默认编辑分组名称
defaultFilterPrefix: '', // 默认过滤前缀（数据库表前缀）
fields:[
   {
      // COMMENT： 订单号
      prop: 'orderCode',
      label: '订单号',
      editRender: true,
      filterRender: true,
      rules: [commonValidate.required,],
   },
   {
      prop: 'name', // 字段名
      importProp: 'name', // 导入中的prop
      label: '名称', // 名称
      importLabel: '名称', //导入中的名称
      tableEdit: true, // 是否允许在table中编辑
      editLabel: true, // 在编辑窗口被显示的字段
      editValue: '', // 新增默认值
      filterValue: '', // 筛选默认值
      width: 200, // 列宽
      show: true, // 是否显示列
      addDisabled:false, // 新增时不可编辑
      editDisabled:true, // 修改时不可编辑
      addShow: true, // 新增显示
      editShow: true, // 编辑显示
      filterDisabled:true, // 筛选不可编辑
      filters: [{data: ""}], // 开启 table 中筛选
      filterRender: true, // 过滤渲染配置
      formatter: formatterByOptions, // 自动转换 options 
      editGroup:'基本信息', // 编辑分组
      editRemarkText: '新增时密码必须填写', // 编辑时的字段备注内容
      editRender: { // 编辑渲染方式
         name: 'EzmTextInput', // 渲染组件
         isLabelValue: '', // 是否将Label 作为 key 来操作
         // 组件 在触发 $emit('input',v) 之前会执行此函数的返回， v 为原本要 input 的参数
         outputFormatter(v){
               return v + 1;
         },
         // 组件 value 变换时将自动通过此函数转换
         inputFormatter(v){
               return v + 2;
         },
         
      }, 
      cellRender: true, // 内容渲染
      editWidth: '20px', // 编辑窗口中的 input 或 select 的宽度
      filterWidth: '200px', // 过滤窗口中的 input 或 select 的宽度
      // 字段额外参数
      params: {
         filterField: 'prop', // 过滤使用字段名
         // 表格中过滤字段追加后缀
         filterSuffix: '_like',
         filterPrefix: 't.',
         // 默认筛选
         defaultFilterOptions:[
               {label: '选择全部', value: ''},
         ],
         // 筛选选项
         options: [
               {label: '菜单页面', value: 1},
               {label: 'API接口', value: 2},
         ],
         // 过滤前缀
         
      },
      // 验证规则
      rules: [
         commonValidate.required,
      ],
      // 编辑窗口中本字段所占用的宽度
      editSpan:{
         sm: 12, // >=768
         md: 8, // >= 992
         lg: 6, // >= 1200
         xl: 4, // >= 1920
         span: 12,
      }
   }
]
```

- 渲染组件可直接使用自定义组件并支持require写法：
  > require 方式加载的组件都会自动注册到 vxe-table 中

  > 开发人员可自行写一个 带 value 和 $emit('input', v) 的组件即可，其他事情框架会自行装配。

```javascript
editRender: {
   name: require("@/views/wms/company/company-selector").default,
   props: {
         fieldValue: 'name',
         fieldShow: 'name',
         canEdit: true,
   },
   on: {
         'on-change': list => {
            this.editForm.companyId = list.length ? list[0].id : '';
         }
   }
},
```

> 其他更多内容与疑问请查看源码或 [邮件(fangjc1986@qq.com)](mailTo:fangjc1986@qq.com) 交流，谢谢。




























