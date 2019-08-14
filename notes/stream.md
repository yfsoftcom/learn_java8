# Stream

> 参考资料: [https://www.cnblogs.com/mrhgw/p/9171883.html](https://www.cnblogs.com/mrhgw/p/9171883.html)

Iterator<T> 接口

外部迭代器迭代一个集合

```java
Iterator<Object> iterator = list.iterator();
while(iterator.hasNext()) {
    Object obj = iterator.next();
    // code here
    if(obj.equals("G")){
        System.out.println(obj);
    }    
}
```

Stream<T> 接口

内部迭代器迭代一个集合

```java
list.stream()
    .filter(x -> x.equals("G"))
    .forEach(System.out::println)
```

内/外部迭代器的差异

1) 迭代器提供next()、hasNext()等方法，开发者可以自行控制对元素的处理，以及处理方式，但是只能顺序处理；
2) stream()方法返回的数据集无next()等方法，开发者无法控制对元素的迭代，迭代方式是系统内部实现的，同时系统内的迭代也不一定是顺序的，还可以并行，如parallelStream()方法。并行的方式在一些情况下，可以大幅提升处理的效率。

#### Stream

##### 如何使用Stream?

聚合操作是Java 8针对集合类，使编程更为便利的方式，可以与Lambda表达式一起使用，达到更加简洁的目的。

聚合操作的使用可以归结为3个部分：

1) 创建Stream:通过stream()方法，取得集合对象的数据集。
2) Intermediate:通过一系列中间（Intermediate）方法，对数据集进行过滤、检索等数据集的再次处理。如上例中，使用filter()方法来对数据集进行过滤。
3) Terminal通过最终（terminal）方法完成对数据集中元素的处理。如上例中，使用forEach()完成对过滤后元素的打印。
在一次聚合操作中，可以有多个Intermediate，但是有且只有一个Terminal。也就是说，在对一个Stream可以进行多次转换操作，并不是每次都对Stream的每个元素执行转换。并不像for循环中，循环N次，其时间复杂度就是N。转换操作是lazy(惰性求值)的，只有在Terminal操作执行时，才会一次性执行。可以这么认为，Stream 里有个操作函数的集合，每次转换操作就是把转换函数放入这个集合中，在 Terminal 操作的时候循环 Stream 对应的集合，然后对每个元素执行所有的函数。

##### Stream的操作分类
刚才提到的Stream的操作有Intermediate、Terminal和Short-circuiting：

1) Intermediate：map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 skip、 parallel、 sequential、 unordered

2) Terminal：forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、iterator

3) Short-circuiting： 
anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit