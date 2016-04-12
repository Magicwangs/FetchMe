#coding:utf8
'''
@author: chenguanfu
'''
# -*- coding:utf-8 -*-
import urllib,urllib2,re
import os
import socket
import codecs
def getImage(word):
    savePath='est/' + str(word)
    ex = os.path.exists(savePath)
    if not ex:
        os.makedirs(savePath)
    tableEx = os.path.exists(savePath + '/list.table')
    if not tableEx:
        ExUrls = []
        name = 1
    else:
        f = open(savePath + '/list.table', 'rb')
        ExUrls = f.readlines()
#         print ExUrls
        name = len(ExUrls)
#         name = len(f.readlines())
        f.close()
    
    EnglishFileName = open('est/EnglishName', 'rb')
    ChineseFileName = open('est/ChineseName', 'rb')
    
    EnglishNames = []
    ChineseNames = []
    for EnglishName in EnglishFileName.readlines():
        EnglishNames.append(EnglishName.strip('\n').strip('\r'))
        
    for ChineseName in ChineseFileName.readlines():
        ChineseNames.append(ChineseName.strip('\n').strip('\r'))
    
    index = EnglishNames.index(str(word))
    chineseWord = ChineseNames[index]
    print EnglishNames, ChineseNames
    print word, chineseWord
    
    EnglishFileName.close()
    ChineseFileName.close()
    for page_num in range(100):
        Page_num = page_num
        url = 'http://image.baidu.com/search/wisemiddetail?tn=wisemiddetail&ie=utf8&word=' + chineseWord + '&pn='+str(Page_num)+'&size=big&fr=wiseresult&fmpage=result&pos=imglist'
#         print url
        request = urllib2.Request(url)
        response = urllib2.urlopen(request)
        content = response.read()
        
        ss=content.replace(" ","")
        urls=re.findall('<a.*?href=.*?<\/a>',ss,re.I) 
        for i in urls:
            if "原图" in i:
                items = re.findall(r'<ahref="(.+?)">',i)
                for item in items:
                    if item + '\n' not in ExUrls:
                        try:
                            socket.setdefaulttimeout(10)
                            u = urllib.urlopen(item)
                            img = u.read()
                            tmp = str(name)
                            f = open(savePath+'/'+tmp+'.jpg','wb')
                            f.write(img)
                            f.close()
                            
                            print item, name
                            f = open(savePath + '/list.table', 'ab')
                            f.write(item + '\n')
                            f.close()
                            name += 1
                        except IOError, e:
                            if hasattr(e,"code"):
                                print e.code
                            if hasattr(e,"reason"):
                                print e.reason
                            else:
                                print "OK"

getImage('American Curl')
