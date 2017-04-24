<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ page import="java.io.*"%>
<%
    //需要在次处输出异常堆栈,方便复现问题
    if(exception!=null){
        exception.printStackTrace();
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>服务器不在家</title>
</head>
<body>
 <style type="text/css" >
	.msg p:first-child {text-align: center;font-family: cursive;font-size: 150px;font-weight: bold;line-height: 100px;letter-spacing: 5px;color: #fff;}
	.msg p:first-child span {cursor: pointer;text-shadow: 0px 0px 2px #686868,0px 1px 1px #ddd,0px 2px 1px #d6d6d6,0px 3px 1px #ccc,0px 4px 1px #c5c5c5,0px 5px 1px #c1c1c1,0px 6px 1px #bbb,0px 7px 1px #777,0px 8px 3px rgba(100, 100, 100, 0.4),0px 9px 5px rgba(100, 100, 100, 0.1),0px 10px 7px rgba(100, 100, 100, 0.15),0px 11px 9px rgba(100, 100, 100, 0.2),0px 12px 11px rgba(100, 100, 100, 0.25),0px 13px 15px rgba(100, 100, 100, 0.3);-webkit-transition: all .1s linear;transition: all .1s linear;}
	.msg p:first-child span:hover {text-shadow: 0px 0px 2px #686868,0px 1px 1px #fff,0px 2px 1px #fff,0px 3px 1px #fff,0px 4px 1px #fff,0px 5px 1px #fff,0px 6px 1px #fff,0px 7px 1px #777,0px 8px 3px #fff,0px 9px 5px #fff,0px 10px 7px #fff,0px 11px 9px #fff,0px 12px 11px #fff, 0px 13px 15px #fff;-webkit-transition: all .1s linear;transition: all .1s linear;}
	.msg p:not(:first-child) {text-align: center;color: #666;font-family: cursive;font-size: 14px;text-shadow: 0 1px 0 #fff;letter-spacing: 1px;line-height: 2em;margin-top: -50px;}
  </style>
  <div class="msg">
    <p><span>出</span><span>错</span><span>啦</span><span>!</span></p>
    <p><a href="javascript:void(0);"  onclick="$('#msg').toggle();">查看错误详细信息</a></p>
    <div id="msg" style="display:;">
		<pre>                
			<%                    
				try {
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();                        
					PrintStream printStream = new PrintStream(byteArrayOutputStream);                        
					printStream.println(exception.getClass() + ":" + exception.getMessage());                        
					printStream.println();                        
					exception.printStackTrace(printStream);                        
					printStream.println();                        
					out.print(byteArrayOutputStream);    
				} catch (Exception ex) {                        
					ex.printStackTrace();                    
				}               
			%>            
		</pre>
	</div>
  </div>
</body>
</html>