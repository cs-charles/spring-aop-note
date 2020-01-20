## spring aop 案例
### 通配符
1. \* ：匹配任何数量字符；
2. .. ：（两个点）匹配任何数量字符的重复，如在类型模式中匹配任何数量子包；而在方法参数模式中匹配任何数量参数。
3. \+ ：匹配指定类型的子类型；仅能作为后缀放在类型模式后边。
### 匹配模式  
1. **匹配方法**  
格式：**annotation_pattern modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)throws-pattern?**   
* annotation_pattern(注解)：可选，方法上持有的注解，如java.lang.@Deprecated；  
* modifiers-pattern(修饰符)：可选，如public、protected；  
* ret-type-pattern(返回值类型)：必填，可以是任何类型模式；“*”表示所有类型；  
* declaring-type-pattern(类型声明)：可选，可以是任何类型模式；  
* name-pattern(方法名)：必填，可以使用“*”进行模式匹配；  
* param-pattern(参数列表)：“()”表示方法没有任何参数；“(..)”表示匹配接受任意个参数的方法，“(..,java.lang.String)”表示匹配接受java.lang.String类型的参数结束，且其前边可以接受有任意个参数的方法；“(java.lang.String,..)” 表示匹配接受java.lang.String类型的参数开始，且其后边可以接受任意个参数的方法；“(*,java.lang.String)” 表示匹配接受java.lang.String类型的参数结束，且其前边接受有一个任意类型参数的方法；  
* throws-pattern(异常列表)：可选，以“throws 异常全限定名列表”声明，异常全限定名列表如有多个以“，”分割，如throws java.lang.IllegalArgumentException, java.lang.ArrayIndexOutOfBoundsException。  
2. **匹配类型**  
格式：**注解? 类的全限定名字**  
* 注解：可选，类型上持有的注解，如java.lang.@Deprecated；  
* 类的全限定名：必填，可以是任何类全限定名。  
3. **类的全限定名称**  
类型全限定名，如:java.lang.String  
4. **参数全限定名称**  
5. **注解的全限定名称**  
6. **匹配Bean名称**  
格式：可以使用Bean的id或name进行匹配，并且可使用通配符“*”；  
              
### 组合表达式
AspectJ使用 且（&&）、或（||）、非（！）来组合切入点表达式。
在Schema（配置文件）风格下，由于在XML中使用“&&”需要使用转义字符“&amp;&amp;”来代替之，所以很不方便，因此Spring ASP 提供了and、or、not来代替&&、||、！。            
### 参数传递
除了execution和bean指示符不能传递参数给通知方法，其他指示符都可以将匹配的相应参数或对象自动传递给通知方法。   
### aop的常用指示符
1. execution 用于匹配方法执行的连接点  
**语法规则：execution("匹配方法")**  
例子：  
********
|模式|描述|
|-|-|
|public * *(..)|任何公共方法的执行|  
|* cn.javass..IPointcutService.*()|cn.javass包及所有子包下IPointcutService接口中的任何无参方法|  
|* cn.javass..*.*(..) | cn.javass包及所有子包下任何类的任何方法|
|* cn.javass..IPointcutService.*(*) | cn.javass包及所有子包下IPointcutService接口的任何只有一个参数方法|
|* (!cn.javass..IPointcutService+).*(..) | 非“cn.javass包及所有子包下IPointcutService接口及子类型”的任何方法|
|* cn.javass..IPointcutService+.*() | cn.javass包及所有子包下IPointcutService接口及子类型的的任何无参方法|
|* cn.javass..IPointcut*.test*(java.util.Date) | cn.javass包及所有子包下IPointcut前缀类型的的以test开头的只有一个参数类型为java.util.Date的方法，注意该匹配是根据方法签名的参数类型进行匹配的，而不是根据执行时传入的参数类型决定的，如定义方法：public void test(Object obj);即使执行时传入java.util.Date，也不会匹配的；|
|* cn.javass..IPointcut*.test*(..)  throws IllegalArgumentException,ArrayIndexOutOfBoundsException | cn.javass包及所有子包下IPointcut前缀类型的的任何方法，且抛出IllegalArgumentException和ArrayIndexOutOfBoundsException异常|
|* (cn.javass..IPointcutService+ && java.io.Serializable+).*(..) | 任何实现了cn.javass包及所有子包下IPointcutService接口和java.io.Serializable接口的类型的任何方法|
|@java.lang.Deprecated * *(..) | 任何持有@java.lang.Deprecated注解的方法|
|@java.lang.Deprecated @cn.javass..Secure  * *(..) | 任何持有@java.lang.Deprecated和@cn.javass..Secure注解的方法|
|@(java.lang.Deprecated &#124;&#124; cn.javass..Secure) * *(..) | 任何持有@java.lang.Deprecated或@ cn.javass..Secure注解的方法|
|(@cn.javass..Secure  *)  *(..) | 任何返回值类型持有@cn.javass..Secure的方法|
|*  (@cn.javass..Secure *).*(..) | 任何持有@cn.javass..Secure类的任意方法|
|* *(@cn.javass..Secure (*) , @cn.javass..Secure (*)) | 任何签名带有两个参数的方法，且这个两个参数都被@ Secure标记了，如public void test(@Secure String str1,@Secure String str1);|
|* *((@ cn.javass..Secure *))或 * *(@ cn.javass..Secure *) | 任何带有一个参数的方法，且该参数类型持有@ cn.javass..Secure；如public void test(Model model);且Model类上持有@Secure注解,参数不加括号，默认注解是标记在类上的|
|* *(@cn.javass..Secure (@cn.javass..Secure *) ,@ cn.javass..Secure (@cn.javass..Secure *)) | 任何带有两个参数的方法，且这两个参数都被@ cn.javass..Secure标记了；且这两个参数的类型上都持有@ cn.javass..Secure；|
|* *(java.util.Map<cn.javass..Model, cn.javass..Model>, ..) | 任何带有一个java.util.Map参数的方法，且该参数类型是以< cn.javass..Model, cn.javass..Model >为泛型参数；注意只匹配第一个参数为java.util.Map,不包括子类型；如public void test(HashMap<Model, Model> map, String str);将不匹配，必须使用“* *(java.util.HashMap<cn.javass..Model,cn.javass..Model>, ..)”进行匹配；而public void test(Map map, int i);也将不匹配，因为泛型参数不匹配|
|* *(java.util.Collection<@cn.javass..Secure *>) | 任何带有一个参数（类型为java.util.Collection）的方法，且该参数类型是有一个泛型参数，该泛型参数类型上持有@cn.javass..Secure注解；如public void test(Collection<Model> collection);Model类型上持有@cn.javass..Secure|
|* *(java.util.Set<? extends HashMap>) | 任何带有一个参数的方法，且传入的参数类型是有一个泛型参数，该泛型参数类型继承与HashMap；Spring AOP目前测试不能正常工作|
|* *(java.util.List<? super HashMap>) | 任何带有一个参数的方法，且传入的参数类型是有一个泛型参数，该泛型参数类型是HashMap的基类型；如public voi test(Map map)；Spring AOP目前测试不能正常工作|
|* *(*<@cn.javass..Secure *>) | 任何带有一个参数的方法，且该参数类型是有一个泛型参数，该泛型参数类型上持有@cn.javass..Secure注解；Spring AOP目前测试不能正常工作 |   
********     
2. within 匹配指定类型内的方法执行  
**语法规则：within("匹配类型")**  
例子:  
*******
|模式|描述|
|-|-|
|within(cn.javass..*) | cn.javass包及子包下的任何方法执行|
|within(cn.javass..IPointcutService+) | cn.javass包或所有子包下IPointcutService类型及子类型的任何方法|
|within(@cn.javass..Secure *) | 持有cn.javass..Secure注解的任何类型的任何方法必须是在目标对象上声明这个注解，在接口上声明的对它不起作用|
*******   
3. this 匹配当前AOP代理对象类型的执行方法；注意是AOP代理对象的类型匹配，这样就可能包括引入接口方法也可以匹配；**注意this中使用的表达式必须是类型全限定名，不支持通配符**；  
**语法规则：this("类的全限定名称")**  
例子：
******
|模式|描述|
|-|-|
|this(cn.javass.spring.chapter6.service.IPointcutService) | 当前AOP对象实现了 IPointcutService接口的任何方法|
|this(cn.javass.spring.chapter6.service.IIntroductionService) | 当前AOP对象实现了 IIntroductionService接口的任何方法也可能是引入接口|
******
4. target 使用“target(类型全限定名)”匹配当前目标对象类型的执行方法；注意是目标对象的类型匹配，这样就不包括引入接口也类型匹配；注意target中使用的表达式必须是类型全限定名，不支持通配符。  
**语法规则：target("类的全限定名称")**  
例子：
******
|模式|描述|
|-|-|
|target(cn.javass.spring.chapter6.service.IPointcutService) | 当前目标对象（非AOP对象）实现了 IPointcutService接口的任何方法|
|target(cn.javass.spring.chapter6.service.IIntroductionService) | 当前目标对象（非AOP对象） 实现了IIntroductionService 接口的任何方法，不可能是引入接口|
******
5. args 使用“args(参数类型列表)”匹配当前执行的方法传入的参数为指定类型的执行方法；注意是匹配传入的参数类型，不是匹配方法签名的参数类型；参数类型列表中的参数必须是类型全限定名，通配符不支持；args属于动态切入点，这种切入点开销非常大，非特殊情况最好不要使用；  
**语法规则：args("参数类型列表")**  
例子：
*****
|模式|描述|
|-|-|
|args (java.io.Serializable,..) | 任何一个以接受“传入参数类型为 java.io.Serializable” 开头，且其后可跟任意个任意类型的参数的方法执行，args指定的参数类型是在运行时动态匹配的|
*****
6. @within 使用“@within(注解类型)”匹配所以持有指定注解类型的*类*中的所有方法；注解类型也必须是全限定类型名；  
**语法规则：@within(注解类型)**  
例子：
******
|模式|描述|
|-|-|
|@within cn.javass.spring.chapter6.Secure) | 任何目标对象对应的类型持有Secure注解的类方法；必须是在目标对象上声明这个注解，在接口上声明的对它不起作用|           
******            
7. @target 使用“@target(注解类型)”匹配当前*目标对象类型*的执行方法，其中目标对象持有指定的注解；注解类型也必须是全限定类型名  
**语法规则：@target("注解类型")**  
例子：
*****
|模式|描述|
|-|-|
|@target (cn.javass.spring.chapter6.Secure) | 任何目标对象持有Secure注解的类方法；必须是在目标对象上声明这个注解，在接口上声明的对它不起作用|
*****       
8. @args 使用“@args(注解列表)”匹配当前执行的方法传入的*参数*持有指定注解的执行；注解类型也必须是全限定类型名；  
**语法规则：@args("注解类型")**  
例子：
*****
|模式|描述|
|-|-|
|@args (cn.javass.spring.chapter6.Secure) | 任何一个只接受一个参数的方法，且方法运行时传入的参数持有注解 cn.javass.spring.chapter6.Secure；动态切入点，类似于arg指示符；|
*****         
9. @annotation 使用“@annotation(注解类型)”匹配当前执行方法持有指定注解的*方法*；注解类型也必须是全限定类型名；  
**语法规则：@annotation("注解类型")**  
例子：
*****
|模式|描述|
|-|-|
|@annotation(cn.javass.spring.chapter6.Secure )|当前执行方法上持有注解 cn.javass.spring.chapter6.Secure将被匹配|
*****
10. bean使用“bean(Bean id或名字通配符)”匹配特定名称的Bean对象的执行方法；Spring ASP扩展的，在AspectJ中无相应概念；  
**语法规则：bean("Bean id或名字通配符")**  
例子：
*****
|模式|描述|
|-|-|
|bean(*Service)| 匹配所有以Service命名（id或name）结尾的Bean|
*****
11. reference pointcut 表示引用其他命名切入点，只有@ApectJ风格支持，Schema风格不支持，如下所示：  
@Pointcut注解配合使用

## 区别
1. @within 与 @target的区别
**相同的:**
* 对象的运行时绑定的方法所属的类必须与被@within或@target中的注解类型所注解的类是同一个类，方法拦截才生效。  
* 运行时绑定的方法是指运行时对象动态绑定的方法，一般指override方法。  
**不同点：**  
* @target要求对象的运行时类型与被注解的类型是同一个类型  
* @within要求对象的运行时类型是被注解的类型或者其子类，只要子类运行的方法不去覆盖父类对应的方法
