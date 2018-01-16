# BaseAdapterDemo
very² easy，very² simple ，and more flexible base adapter for recyclerview



**gif:**

![](gif/adapter.gif)





**引入：compile 'com.baozi:base-adapter:1.0.0'**

> 引入了26.+的recyclerView，如果你已经引入了recyclerView包那么引入的时候去除
>
> ```
> compile ('com.baozi:base-adapter:1.0.0',{
>        exclude group: 'com.android.support'
>     }))
> ```
>
> 

#### 使用方法见demo



采用注入方式，理论上认为所有item包括头布局、尾布局以及加载布局都是属于数据源。这种方式更加灵活，万物皆可RecyclerView;



**感谢：**

1.http://drakeet.me/multitype/

2.https://github.com/CymChad/BaseRecyclerViewAdapterHelper

3.https://github.com/SilenceDut/KnowWeather





