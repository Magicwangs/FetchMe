# -*- coding: utf-8 -*-
import time
import urllib2
import datetime
import re
from bs4 import BeautifulSoup#导入 from bs4 import BeautifulSoup 
import os
from selenium import webdriver#要想使用selenium的webdriver 里的函数，首先把包导进来

starttime = datetime.datetime.now()#设置程序运行时间

###########全局变量区、配置区 
BASE_DIR = os.path.dirname(__file__)#获取当前文件夹的绝对路径
#os.path.dirname(BASE_DIR)可以取上一级目录

my_headers=[
    'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0',
    'Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko',
    'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36',
    'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.11 TaoBrowser/2.0 Safari/536.11',
    'Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E; LBBROWSER)',
    'Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; QQBrowser/7.0.3698.400)',
    'Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)',
    'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SV1; QQDownload 732; .NET4.0C; .NET4.0E; SE 2.X MetaSr 1.0)',
    'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11',
    'Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)',
    'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:16.0) Gecko/20100101 Firefox/16.0',
    'Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10',
    'Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50',
    'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)'
    ]
sample=['http://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=Ibizan%20Hound&pn=','8','Ibizan Hound']
####################################

def getHtml(url,browser):#获得Uniform Resoure Locator:统一资源定位器
    browser.get(url)
    html = browser.page_source
    return html

def getImgAddress(html):#正则匹配，找到初步链接。
    reg = r'src="(.+?\.jpg)"'
    imgre = re.compile(reg)
    imgaddress = re.search(imgre,html)#创建了getImg()函数，用于在获取的整个页面中筛选需要的图片连接。re模块主要包含了正则表达式：
    try:
        return imgaddress.group(1)
    except Exception,e:
        print "This picture doesn't exist"
        return False

def getImg(imglist,startIndex):#下载图片
    for url in imglist:  #注：此处必须添加referer，否则失败；同时加上Host和User_Agent
        req=urllib2.Request(url)
        req.add_header('User-Agent',my_headers[1])
        req.add_header('Host',url[7:27])
        req.add_header('Referer','http://image.baidu.com/')
        req.add_header('GET',url)
        content=urllib2.urlopen(req)
        fobj=open(DATA_DIR+'//%s.jpg'%startIndex,'wb')
        fobj.write(content.read())
        fobj.close()
        startIndex+=1


browser=webdriver.Firefox()#设置火狐自动下载

####Here:you can choose different dog by changing X  HERE!!!!!!!HERE..........HERE!!!!!!!#################
x=0

dir ="./"+sample[x][2]   #创建对应的文件夹
try:
    if not os.path.exists(dir):  
        os.mkdir(dir)
    DATA_DIR=BASE_DIR+'\\'+sample[x][2]#获取上级目录
except:  
    print "Failed to create directory:"  
    exit()
print "config OK!"
fobj=open(DATA_DIR+'//'+sample[x][2]+'.txt','w+')

for page in range((sample[x][1])):#遍历页面
    current_index=page*15 #每页15张图片
    current_url=sample[x][0]+str(current_index)+'&gsm=0'
    flag=False
    while(flag != True):#一个页面一直到成功为止    
        try:
            imglist=[]# 用于存放图片最终的网址列表
            html = getHtml(current_url,browser)        
            soup=BeautifulSoup(html.encode('utf-8'))
            imgBlocklist=soup.find('ul',attrs={"class": "imglist"})
            print 'Dog name:',sample[x][2],';Current_page:',page,
            print ';len(imgBlocklist)=',len(imgBlocklist)/2
            for index,img_item in enumerate(imgBlocklist):
                if(index%2==1):#提取有效的字符串
                    html_address=getImgAddress(str(img_item))#去除amp;防止无效
                    if(html_address==False):#不能用的链接跳过
                        continue
                    else:
                        html_address=html_address.replace('amp;','')
                        imglist.append(html_address)
                        fobj.write(html_address+'\n')
                else:
                    continue
            getImg(imglist,current_index)
            flag=True
        except Exception,e:#找寻不到，则重新刷新页面
            print e
fobj.close()

#print getImgAddress(html)

endtime = datetime.datetime.now()
print ('程序运行时间为：'+str((endtime - starttime).seconds))
