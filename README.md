# mvvmArchitecture
common mvvm architecture，make it easy to develop mvvm app project
MainActivity作为demo可以参考，
增加了MvvmApplication作为基类Application，自定义Application需要继承MvvmApplication，
BaseVmActivity，BasicViewModel,分别是Activity，ViewModel的基类，他们配合使用。
自定义的Activity与ViewModel需要分别继承以上基类。

自定义Activity代码与MainActivity大部分相同，只需要修改Model层depository获取数据的实现以及ViewModel层中拿到数据后给LiveData数据赋值的逻辑，layout层布局需要自己实现

想了解更多可以关注公众号： “你丫才IT民工”

扫以下二维码可以直达：
![image](https://github.com/fengmingye/mvvmArchitecture/blob/master/img/qrcode_for_gongzhonghao.jpg)
