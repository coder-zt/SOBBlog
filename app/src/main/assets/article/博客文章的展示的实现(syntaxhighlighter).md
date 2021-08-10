# 使用WebView展示博客文章详情(syntaxhighlighter.js的简单使用)

刚开始开发博客文章详情，由于对于前端知识了解不熟悉，所以选择了使用原生的自定View来完成，但是在开发中发现该方案的成本和效果差强人意，所以改变方式使用WebView结合JS的方案来展示文章详情，既然对前端知识不是很熟悉，那就摸着石头过河，慢慢探索。大不了就是再多掉几根头发

### 观察网站的前端

- 按下F12查看文章详情页的网页源码

![图片描述](https://images.sunofbeaches.com/content/2021_08_09/874322082878455808.png)


- 可以发现代码和关键字是使用了`<code>`标签，并且代码块还带有class属性,第一个表示代码语言类型，及我们在书写md文档是添加的标注，那么`hljs`这个值代表的是什么意思呢，问一下度娘

- 搜索之后，第一条记录就是`highlight.js`的结果，刚好hljs就是这个库的简写，所以这个库应该对我们的需求有帮助，这是我第一次知道这个js库，紧接着就是开始搜索`hightlight.js`相关的资料，搜索的相关信息如下：

    1. [highlight的github地址](https://github.com/highlightjs/highlight.js/)
    2. [如何使用 highlight.js 高亮代码](https://www.cnblogs.com/wx1993/p/8042226.html)
    3. [syntaxhighlighter的github地址](https://github.com/syntaxhighlighter/syntaxhighlighter)
    4. [JS 插件----SyntaxHighlighter的使用](https://blog.csdn.net/GrootBaby/article/details/81746211)

syntaxhighlighter这个库是我再搜索highlight库过程中找到的，因为前者可以在github下载资源，并通过本地加载进行文章的渲染，所以在这里选择了后者，接下来就是根据教程编写demo进行测试和学习：


### 测试与学习

1. 下载资源

    -  从github可以下载该js库的资源，然后将js、css放在assets目录下:

    ![](https://images.sunofbeaches.com/content/2021_08_11/874810776592842752.png)

    - 其中html文件夹下artile_template.html是文章的样板后面才会用到，article_demo.html是根据教程编写的demo，其代码如下:

    ```html
        <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>Document</title>
                <link rel="stylesheet" type="text/css" href="SyntaxHighlighter/css/shCore.css">
                <link rel="stylesheet" type="text/css" href="SyntaxHighlighter/css/shThemeDjango.css">
                <script type="text/javascript" src="SyntaxHighlighter/js/XRegExp.js"></script>
                <script type="text/javascript" src="SyntaxHighlighter/js/shCore.js"></script>
                <script type="text/javascript" src="SyntaxHighlighter/js/shBrushJava.js"></script>
                <script type="text/javascript">
                    SyntaxHighlighter.all()
                    </script>
            </head>
            <body>
                <pre class="brush: java; collapse: false;">
                    public void javaMethod() {
                        int i = 3;
                    }
                </pre>
                <pre class="brush: java; collapse: false;">
                    public static String utf8ToUnicode(String inStr) {
                        char[] myBuffer = inStr.toCharArray();
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < inStr.length(); i++) {
                            UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
                            if(ub == UnicodeBlock.BASIC_LATIN){
                                //英文及数字等
                                sb.append(myBuffer[i]);
                            }else if(ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS){
                                //全角半角字符
                                int j = (int) myBuffer[i] - 65248;
                                sb.append((char)j);
                            }else{
                                //汉字
                                short s = (short) myBuffer[i];
                                String hexS = Integer.toHexString(s);
                                String unicode = "\\u"+hexS;
                                sb.append(unicode.toLowerCase());
                            }
                        }
                        return sb.toString();
                    }
                </pre>
                <pre class="brush: java; collapse: true;">
                    public void javaMethod() {
                    int i = 3;
                    }
                </pre>
                <pre class="brush: java; collapse: true;">
                    public void javaMethod() {
                    int i = 3;
                    }
                </pre>
            </body>
        </html>
    ```
    - 紧接着就是使用webview加载上面的html

    ```kotlin
        dataBinding.wvArticle.settings.javaScriptEnabled = true
        //getTestHtml("article_demo")：从assets目录下读取测试数据
        dataBinding.wvArticle.loadDataWithBaseURL("file:///android_asset/",getTestHtml("article_demo"), "text/html", "utf-8", null)
    ```
    - 其加载结果如下：
    ![](https://images.sunofbeaches.com/content/2021_08_11/874814978165571584.jpg)

    - 关于学习、测试的总结：

    上文只是讲述实现使用`SyntaxHighlighter`库展示代码的简单使用，下列总结时在开发中慢慢发现的一些特点，期间走了很多弯路，浪费了很多时间，但是这个库的更多用法还有待挖掘：

    1. 在demo实例中代码被`<pre>`标签包裹，这个标签时可以在`shCore.js`中修改的，该属性在77行，应为网站返回的数据中代码使被`<code>`标签包裹的，所以需要修改一下

    ```
        /** Name of the tag that SyntaxHighlighter will automatically look for.
            该代码高亮插件会自动寻找该标签 */
		tagName : 'code',
    ```

    2. 必须要加载样式文件，不同的样式文件带有不同的主题，并且可以自定义样式，这个在后面还会讲到

    ```html
        <!-- 加载主题样式 -->
        <link rel="stylesheet" type="text/css" href="SyntaxHighlighter/css/shCore.css">
        <link rel="stylesheet" type="text/css" href="SyntaxHighlighter/css/shThemeDjango.css">
    ```
    3. 加载js文件时必须要加载`XRegExp.js`、`shCore.js`两个js文件且`XRegExp.js需要加载在前面`和对应的不同语言类型的文件，语言类型的文件也可以自定义
    
    ```html
        <script type="text/javascript" src="SyntaxHighlighter/js/XRegExp.js"></script>
        <script type="text/javascript" src="SyntaxHighlighter/js/shCore.js"></script>
        <!-- 加载需要渲染代码的类型 -->
        <script type="text/javascript" src="SyntaxHighlighter/js/shBrushJava.js"></script>
        <!-- 自定义渲染kotlin代码 -->
    <script type="text/javascript" src="SyntaxHighlighter/js/shBrushKotlin.js"></script>
    ```

    4. 在代码块的标签`<pre>`可以通过控制class的值控制对应的功能：

        class="brush: java; collapse: true;"

    - brush: 指定code的类型，并需要加载对应的js文件，例如指定java，需要加载`shBrushJava.js`
    - collapse: 是否默认收缩隐藏代码