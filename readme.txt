【模板】
接口定义：
    请求路径：
    请求方式：
    请求参数：
    响应数据：
思路分析：
    controller:
    service:
    mapper:

day01:
    开发环境搭建：前端环境+后端工程环境+数据库环境
    登录功能完善

day02：
员工模块
    1.新增员工：
        接口定义：
            请求路径：/admin/employee
            请求方式：POST
            请求参数：json格式 {"name":"xxx","sex":"xx"...}
            响应数据：Result data:null

        数据模型：employee员工表

        思路分析：
            controller:
                在EmployeeController中定义功能接口（方法）：
                1.调用service的新增方法
                2.返回结果
            service:
                在EmployeeService接口中定义方法，并在实现类中实现该方法：
                1.补充基础属性
                2.补充密码，将密码进行md5加密
                3.调用mapper的方法，新增员工
            mapper:
                操作数据，将员工数据新增到employee表
                SQL: insert into employee values(#{},#{},....)
                基于注解：@Insert

        问题分析：
            1.新增相同用户名账号的时候，前端没有反应
            原因：employee表中对username做了唯一约束，导致后端程序报错，并且后端没有捕获异常进行处理
            Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry 'zhangsan' for key 'employee.idx_username'
            解决方法：使用全局异常处理器，统一捕获SQLIntegrityConstraintViolationException异常

            2.新增员工的时候，所有的数据，创建人和更新人id都是10
            原因：service层代码逻辑写死了
            解决方法：需要动态获取登录人id，---》在登录成功的时候，将id存入到令牌中，下一次请求就可以获取令牌并解析出id了，
                    可以使用ThreadLocal方案，同一个请求公用一个线程，在一个线程中通过ThreadLocal局部变量，可以共享数据
                    1)在拦截器中设置ThreadLocal
                    2)在service层中获取ThreadLocal

    2.员工分页：
        接口定义：
            请求路径：/admin/employee/page
            请求方式：GET
            请求参数：普通参数: ?name=xxx&page=1&pageSize=10  使用DTO封装
            响应数据：Result data:PageResult
        思路分析：
            controller:
                在EmployeeController定义一个分页方法：
                1.调用service的分页方法
                2.返回结果
            service:(使用给分页插件)
                在EmployeeService接口中定义一个方法，并实现：
                0.导入分页插件依赖
                1.设置分页参数
                2.调用mapper的查询方法(普通条件查询), 获取page结果对象
                3.封装PageResult对象，并返回
            mapper:
                在EmployeeMapper中定义查询方法
                基于XML开发：使用动态sql拼接条件

    3.启用禁用员工
        接口定义：
            请求路径：/admin/employee/status/{status}
            请求方式：POST
            请求参数：路径参数 status, 普通参数  id
            响应数据：Resutl data:null

        思路分析：
            controller：
            service：
                注意：更新员工需要构造员工对象，补充更新时间和更新人
            mapper：
                基于xml，为了兼容后续的编辑员工操作

    4.修改员工
        回显操作：--根据id查询员工
            接口定义：
                请求路径：/admin/employee/{id}
                请求方式：GET
                请求参数：路径参数 id
                响应数据：Resutl data:员工对象

        更新操作：
            接口分析：
                请求路径：/admin/employee
                请求方式：PUT
                请求参数：json格式--使用EmployeeDTO封装
                响应数据：Resutl data:null

    5.修改密码：【扩展】
        接口分析：
            请求路径：/admin/employee/editPassword
            请求方式：PUT
            请求参数：{oldPassword: "123456", newPassword: "111111"}  //需要自己补充员工id
            响应数据：Result  data:null

        思路分析：
            controller：
                调用service
            service：
                1.需要自己补充员工id--从哪里获取？需要动态获取（获取当前登陆人的id）
                -- ThreadLocal存了id，从ThreadLocal获取
                2.对比数据库中的密码和前端传递过来的旧密码（加密之后对比）是否一致
                3.如果一致，对新密码进行加密
                4.调用mapper层的更新方法
            mapper：
                update employee set password = 111111 where id = ?

分类模块：--导入功能模块代码，涉及下面6个接口
    - 新增分类
    - 分类分页查询
    - 根据id删除分类【注意业务规则：需要判断当前分类下是否存在关联的菜品和套餐，如果存在则不允许删除！！】
    - 修改分类【注意：此处后端无需编写回显接口，因为点击修改没有发送动态请求调用后端的接口】
    - 启用禁用分类
    - 根据类型查询分类【注意：该接口是为菜品和套餐模块准备的】


----------------------------------------------------------------
day03
公共字段自动填充：
    问题分析：在多个模块中存在重复的逻辑，如新增员工、新增分类...都会需要补充基础属性字段（创建时间、创建人、更新时间、更新人）
        这种情况导致代码冗余，不方便后续维护

    思路分析：
        使用AOP技术，可以解决代码冗余问题
        步骤：
            1.导入aop相关依赖
            2.定义一个切面类，类上添加相关注解
            3.定义一个通知方法（选择前置通知@Before）
            4.写切入点方式（execution(推荐写注解的方式)）
                4.1 定义一个注解
            5.具体逻辑：
                重要：需要判断业务操作类型
                如果是新增，则补充4个字段
                如果是更新，则补充2个字段
                    如何判断方法的操作类型，可以在注解里添加2个属性，insert和update
                    可以通过枚举限制类型就是insert和update
                还需要通过反射去进行目标方法的增强，设置基础属性
        总结：AOP、注解、枚举、反射


新增菜品：
    接口分析：
        涉及三个接口
        1.根据类型查询分类列表【已完成】

        2.图片上传【待实现】
            接口定义：
                请求路径：/admin/common/upload
                请求方式：POST
                请求参数：multipartFile  file参数名
                响应数据：Result data:url图片路径
        3.新增菜品【待实现】
            接口定义：
                请求路径：/admin/dish
                请求方式：POST
                请求参数：{"name":"xxx","price":"xxx"...}  可以封装在DTO里面
                响应数据：Result data=null

    数据模型：
        菜品表dish、菜品口味表dish_flavor、分类表category
        dish ：dish_flavor = 1 ：N
        category : dish = 1 : N

删除菜品：
    接口定义：
        接口路径：/admin/dish
        请求方式：DELETE
        请求参数：ids=10,11,12   普通参数
        响应数据：Result data:null

    思路分析：
        controller:
            在DishController中定义一个删除方法：
                1.调用service的删除
                2.返回结果
        service:
            在DishService接口中定义删除方法，并在实现类中实现该方法：
                0.开启事务
                1.判断菜品的状态是否为启售，如果是启售，则禁止删除
                2.判断菜品是否被套餐关联，如果被关联，则禁止删除
                3.调用mapper删除菜品(删除的是dish)
                4.将关联的口味数据也删除（根据菜品id,删除的是dish_flavor）
        mapper:
            在DishMapper中定义删除方法
            在DishFlavorMapper中定义删除方法




