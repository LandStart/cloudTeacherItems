# cloudTeacherItems
==============================
文件相关软件的下载链接为：
链接: https://pan.baidu.com/s/1IJi_xrGAFRN4MTDjVvcPBg 提取码: 1qaz 复制这段内容后打开百度网盘手机App，操作更方便哦。
push test
===============================
nginx实现80端口请求负载到其他主机
在nginx的http中添加，实现nginx请求负载 localhost:8763 localhost:8764

	upstream loadBlanceT{
		
		server localhost:8763;
		
		server localhost:8764;
	}

    server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;
		
		location / {
			proxy_pass  http://loadBlanceT; # 或 http://www.baidu.com
			proxy_set_header Host $proxy_host;
			proxy_set_header X-Real-IP $remote_addr;
			proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		}

