package com.coder.zt.sobblog.model.article

object TestArticleContent {
    const val html="<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "\t<title>code demo</title>\n" +
            "\t<script type=\"text/javascript\" src=\"shCore.js\"></script>\n" +
            "\t<script type=\"text/javascript\" src=\"XRegExp.js\"></script>\n" +
            "\t<script type=\"text/javascript\" src=\"shBrushJava.js\"></script>\n" +
            "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"shThemeDefault.css\">\n" +
            "\t<script type=\"text/javascript\">SyntaxHighlighter.all();</script>\n" +
            "</head>\n" +
            "<body>\n" +
            "{{template}}" +
            "</body>\n" +
            "</html>"

    const val html2="<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Document</title>\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"shCore.css\">\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"shThemeDefault.css\">\n" +
            "</head>\n" +
            "<body>\n" +
            "<pre name=\"code\" class=\"brush: java\">\n" +
            "        public class Person {\n" +
            "\n" +
            "        }\n" +
            "    </pre>\n" +
            "<pre  name=\"code\" class=\"brush: js\">\n" +
            "         function foo() {\n" +
            "            var i = 3;\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "    </pre>\n" +
            "<pre class=\"brush: java; collapse: true;\">\n" +
            "        public void javaMethod() {\n" +
            "            int i = 3;\n" +
            "        }\n" +
            "    </pre>\n" +
            "<script type=\"syntaxhighlighter\" class=\"brush: js\">\n" +
            "        <![CDATA[\n" +
            "      /**\n" +
            "       * SyntaxHighlighter\n" +
            "       */\n" +
            "          function foo() {\n" +
            "              if (counter <= 10)\n" +
            "                  return;\n" +
            "              // it works!\n" +
            "          }\n" +
            "        ]]>\n" +
            "    </script>\n" +
            "<script type=\"text/javascript\" src=\"XRegExp.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"shCore.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"shBrushJava.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"shBrushPowerShell.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"shBrushJScript.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"shBrushSql.js\"></script>\n" +
            "<script type=\"text/javascript\">\n" +
            "        SyntaxHighlighter.all()\n" +
            "    </script>\n" +
            "</body>\n" +
            "</html>"

    const val html3="<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Document</title>\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"shCore.css\">\n" +
            "    <link rel=\"stylesheet\" type=\"text/css\" href=\"shThemeDjango.css\">\n" +
            "</head>\n" +
            "<body>\n" +
            "<button type=\"button\" id=\"button1\" weight=\"100px\" height=\"50px\" onclick=\"start()\"></button>"+
            "{{template}}" +
            "<script type=\"text/javascript\" src=\"XRegExp.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"shCore.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"shBrushJava.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"shBrushPowerShell.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"shBrushJScript.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"shBrushSql.js\"></script>\n" +
            "<script type=\"text/javascript\">\n" +
            "        SyntaxHighlighter.all()\n" +
            "function onTouchStart(e){\n" +
            "            console.log(e);\n" +
            "            alert(e)\n" +
            "            }\n" +
            "            function start(){\n" +
            "                var dom = document.getElementByName('code');\n" +
            "                dom.addEventListener('touchstart', onTouchStart, false);\n" +
            "alert(\"设置监听\");"+
            "androidApi.log(\"设置监听\");"+
            "            }"+
            "start()"+
            "    </script>\n" +
            "<style>" +
            ".code-background{\n" +
            "        background-color: #eeeeee;\n" +
            "    }"+
            "img{\n" +
            "        width: 100%;\n" +
            "    }"+
            "</style>" +
            "</body>\n" +
            "</html>"
}