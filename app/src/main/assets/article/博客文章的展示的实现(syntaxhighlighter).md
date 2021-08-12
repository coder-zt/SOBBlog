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
        - class-name： 给高亮的标签新增一个类名
        - first-line : 代码块开始的行号
        - title: 代码块的标题会显示在代码块上方
        - quick-code： 双击是否可以复制和粘贴代码
        - toolbar： 是否使用工具条（启用，代码块右上方有个？方块，但是点击没什么反应）
        - gutter：是否显示行号
    - 关于class中使用的属性值很多，这里只是列举的几个，主要英语能力有限，测试其他属性发现变化并不明显，还有待大佬解析

### 适配网站博客文章

1. 首先是html的模板，先引用js、css资源，和编写额为的自定义样式的css属性和一些js代码供后面拓展，如下：

```html
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <!-- 引入css文件 -->
    <link rel="stylesheet" type="text/css" href="SyntaxHighlighter/css/shCore.css">
    <link rel="stylesheet" type="text/css" href="SyntaxHighlighter/css/shThemeArticle.css">
    <!-- 引入js文件，两面连个的引用顺序不可变 -->
    <script type="text/javascript" src="SyntaxHighlighter/js/XRegExp.js"></script>
    <script type="text/javascript" src="SyntaxHighlighter/js/shCore.js"></script>
    <script type="text/javascript" src="SyntaxHighlighter/js/shBrushJava.js"></script>
    <script type="text/javascript" src="SyntaxHighlighter/js/shBrushPowerShell.js"></script>
    <script type="text/javascript" src="SyntaxHighlighter/js/shBrushJScript.js"></script>
    <script type="text/javascript" src="SyntaxHighlighter/js/shBrushSql.js"></script>
    <script type="text/javascript" src="SyntaxHighlighter/js/shBrushXml.js"></script>
    <script type="text/javascript" src="SyntaxHighlighter/js/shBrushPlain.js"></script>
    <script type="text/javascript" src="SyntaxHighlighter/js/shBrushKotlin.js"></script>
    <script type="text/javascript">
        SyntaxHighlighter.all()
        // 给代码块设置监听事件，当点击时通知给ktolin
        function onTouchStart(e) {
            androidApi.touchEvent(0);
        }

        function onTouchEnd(e) {
            androidApi.touchEvent(2);
        }

        function onTouchMove(e) {
         androidApi.touchEvent(1);
        }
        function addTouchListener() {
            var dom = document.getElementsByClassName('code');
            androidApi.log(dom.length);
            for (i in dom){
                dom[i].addEventListener('touchstart', onTouchStart, false);
                dom[i].addEventListener('touchmove', onTouchMove, false);
                dom[i].addEventListener('touchend', onTouchEnd, false);
            }
        }
        </script>
    <style>
        /* 控制文章中的image不超过屏幕 */
        img {
            max-width: 100%;
        }
        /* 代码块的样式 */
        .code_block{
            display: block;
            overflow-x: auto;
            padding: .5em;
            background: #f0f0f0;
        }

        /* 文章中关键字的样式，及markdown中``修饰的文字 */
        .key_word{
            word-break: break-word;
            border-radius: 2px;
            margin-left: 3px;
            margin-right: 3px;
            overflow-x: auto;
            background-color: #f7f7f7;
            color: #ff502c;
            font-size: .87em;
            padding: .065em .4em!important;
            display: inline!important;
        }
    </style>
</head>
<body>
{{template}}
</body>
</html>
```

2. 在获取到文章数据的基础上对文章内容进行处理：
    
    首先时对关键字的处理，前文提到特殊关键字时被`<code>`标签包裹，我们在这里使用正则将其替换成`<span>`标签并添加属性`key_word`,代码如下：

    ```kotlin
        /**
        * content：网页返回的文章内容
        */
        private fun handleKeywords(content: String): String {
            var handleText = content
            val parttern = Regex("<code>(.*?)</code>")
            val results = parttern.findAll(handleText)
            for (result in results) {
                var keyString: String = result.value
                keyString = keyString.replace("<code>", "<span class=\"key_word\">")
                keyString = keyString.replace("</code>", "</span>")
                handleText = handleText.replace(result.value, keyString)
            }
            return handleText
        }
    ```

    再是对代码块的处理,前文中代码块除了被`<code>`标签包裹以为，其中还带有代码语言类型的类名，所以需要将原先的类名进行转换，然后再此基础上加上其他属性的值来控制代码块的样式，也可以直接再shCore.js中进行全局控制，处理代码如下：

    ```kotlin
     /**
        * content：网页返回的文章内容
        */
        private fun handleHtmlContent(content: String): String {
            //处理文章内容中是关键字的情况
            val handleText = handleKeywords(content)
            //读取模板代码
            val template = getTestHtml("article_demo")
            //利用jsoup转化未html对象，方便处理
            val document = Jsoup.parse(template.replace("{{template}}",handleText))
            //使用递归处理html中的代码块
            verseChild(document.body())
            return document.toString()
        }

        private fun verseChild(body: Element?) {
        body?.let {
            for (child in body.children()) {
                if (child.tagName() == "code") {
                    //给代码父标签添加属性，可以自定义样式
                    child.parent().attributes().add("class", "code_block")
                    //给code设置新的类名，来控制代码的属性
                    child.attributes().put("class", "brush: ${getLanguageType(child.attr("class"))}; toolbar:false; quick-code:false;")
                }
                verseChild(child)
            }
        }
    }

    /**
     * 将原来内容中的代码类型属性进行转换
     */
    private fun getLanguageType(attr: String): String {
        Log.d(TAG, "getLanguageType: $attr")
        if (attr.isEmpty()) {
            return "text"
        }else{
            return when(attr){
                "language-shell" -> "ps"
                "language-java" -> "java"
                "language-kotlin" -> "kotlin"
                "language-xml" -> "xml"
                "language-sql" -> "sql"
                "language-json" -> "text"
                else -> "text"
            }
        }
    }
    ```

3. 适配文章后的结果如下：

    ![](https://images.sunofbeaches.com/content/2021_08_12/875376583227998208.jpg)

### 自定义样式、拓展语言类型、交互优化

syntaxhighlighter的资源下已经提供很多语言类型和文章主题，但是与网站的文章样式的差异较大，并且还不支持kotlin代码的渲染

1. 自定义样式

    打开资源中css文件，内容就是代码块的样式属性，但是里面某些类名的意思不是很清楚的，所以需要获取webview中的网页源代码，查看代码发生了什么变化，并且可以直接找到css中的类名修饰了那些html代码

    获取webview中的网页源代码：

    ```kotlin
       class DemoJsApi {

            companion object{
                private const val TAG = "DemoJsApi"
            }

            @JavascriptInterface
            fun printPageSource(html: String?) {
                Log.d(TAG, "pageSource: $html")
                saveHtml(html!!)
            }

            private fun saveHtml(html: String) {
                val f = File("data/data/com.coder.zt.sobblog/result.html")
                if (!f.exists()) {
                    f.createNewFile()
                }
                val fos = FileOutputStream(f)
                val input = OutputStreamWriter(BufferedOutputStream(fos))
                val bfReader = BufferedWriter(input)
                bfReader.write(html)
                bfReader.flush()
                bfReader.close()
            }
        }
    ```

    ```kotlin
        //为webview设置js接口
        dataBinding.wvArticle.addJavascriptInterface(DemoJsApi(), "androidApi")
        //调用js代码获取webview中的网页源代码
        dataBinding.btnSource.setOnClickListener{
            dataBinding.wvArticle.loadUrl("javascript:window.androidApi.printPageSource('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');")
        }
    ```
    获取网页源代码后，通过比较网页源代码中的类名即可知道css中各个类名修饰的作用，接着结合自己所需要的主题编辑自己的主题css文件

2. 拓展语言类型-以kotlin为例


    ![](https://images.sunofbeaches.com/content/2021_08_12/875431897491046400.png)

