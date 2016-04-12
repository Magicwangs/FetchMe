#coding:utf8
'''
@author: chenguanfu
'''
#coding:utf8
'''
@author: chenguanfu
'''
# -*- coding:utf-8 -*-
import urllib,urllib2,re
import os
def getImage(word):
    name = 1
    savePath='est/' + str(word)
    ex = os.path.exists(savePath)
    if not ex:
        os.makedirs(savePath)
    for page_num in range(20):
        Page_num = page_num * 6
#         url = 'http://image.baidu.com/search/wisemidresult?tn=wisemidresult&ie=utf8&word='+ str(word) + '&pn='+str(Page_num)+'&rn=6&fmpage=result&size=big'
#         url = 'http://image.baidu.com/search/wisemiddetail?tn=wisemidresult&ie=utf8&word='+ str(word) + '&pn='+str(Page_num)+'&rn=6&fmpage=result&size=big'
        url = 'http://image.baidu.com/search/wisemiddetail?tn=wisemiddetail&ie=utf8&word=' + str(word) + '&pn='+str(Page_num)+'&size=big&fr=wiseresult&fmpage=result&pos=imglist'
#         url = 'http://image.baidu.com/search/wisemiddetail?tn=wisemiddetail&ie=utf8&word=Affenpinscher&pn=62&size=big&fr=wiseresult&fmpage=result&pos=imglist'
        request = urllib2.Request(url)
        response = urllib2.urlopen(request)
        content = response.read()
#         print content
#         pattern = re.compile('img class.*?src="(.*?)"',re.S)
        pattern = re.compile(r'http://.*.jpg')
        items = re.findall(pattern,content)
        for item in items:
            print item
            try:
                u = urllib.urlopen(item)
            except urllib2.URLError, e:
                if hasattr(e,"code"):
                    print e.code
                if hasattr(e,"reason"):
                    print e.reason
            img = u.read()
            tmp = str(name)
            f = open(savePath+'/'+tmp+'.jpg','wb')
            f.write(img)
            f.close()
            name += 1

getImage('Affenpinscher')