### Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理？
- 动态SQL可以根据传入的参数进行判断，来动态拼接SQL。
- 动态SQL有\<if>、\<foreach>、\<where>、\<choose>
- XMLScriptBuilder把动态SQL解析成一个DynamicSqlSource，DynamicSqlSource里包含了一个MixedNode，MixedNode中有一个SqlNode的List。在生成BoundSql的时候，会生成一个DynamicContext，DynamicContext中有一个SqlBuilder，通过对MixedNode中的每个ListNode调用apply.(dynamicContext)来对dynamicContext中的SqlBuilder进行拼接，最终得到BoundSql。

### Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？
	支持，通过CGLIB创建目标对象的代理对象，当调用目标方法时，进入拦截器方法，比如调用a.getB().getName()，拦截器invoke()方法发现a.getB()是null值，那么就会单独发送事先保存好的查询关联B对象的sql，把B查询上来，然后调用a.setB(b)，于是a的对象b属性就有值了，接着完成a.getB().getName()方法的调用。
### Mybatis都有哪些Executor执行器？它们之间的区别是什么？
Mybatis中，Executor接口的实现类有BaseExecutor接口和CachingExecutor接口，BaseExecutor是一个抽象类，BaseExecutor的子类有SimpleExecutor、ReuseExecutor、BatchExecutor、ClosedExecutor。
- `SimpleExecutor`提供了比较基本的SQL执行功能，是默认的执行器。
- `CachingExecutor`有缓存的功能，内部的TransactionalCacheManager会存储缓存，`CachingExecutor`在使用时其实把执行委托给了内部的一个Executor对象。
- `ReuseExecutor`可以重用Statement对象，它在内部用一个Map把执行的Statement缓存起来，每次执行时都会检查缓存，如果有已经存在的相同的Statement，则重复使用。
- `BatchExecutor`可以用来做批量更新操作。
- `ClosedExecutor`不支持SQL执行，内部SQL执行方法都抛出了异常。
### 简述下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景。三个方面来作答）？
- 一级缓存：存储结构为HashMap，key由MappedStatement和参数生成，value为之前查询的结果。一级缓存的作用范围是SqlSession，当SqlSession执行数据库的修改操作时，会使缓存失效。
- 二级缓存：底层还是HashMap结构，作用范围是namespace，当Mapper执行修改数据库操作时会使缓存失效。
### 简述Mybatis的插件运行原理，以及如何编写一个插件？
- 定义好插件后，MyBatis启动时，会加载插件并保存到拦截器链中。每当被拦截的类被创建时，就会为它创建代理对象，之后被拦截对象相关方法调用时就会调用插件逻辑。
- 通过实现Interceptor接口，并加上@Intercepts注解，@Intercepts注解中包含@Signature注解，@Signature中type定义了被拦截的类，method定义了被拦截的方法，args定义了被拦截方法的参数
### 