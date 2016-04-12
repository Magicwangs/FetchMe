#coding:utf8
'''
@author: chenguanfu
'''
import urllib
import urllib2
import cookielib

filename = 'cookie.txt'
cookie = cookielib.MozillaCookieJar(filename)
opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cookie))
postdata = urllib.urlencode({
            'Txt_UserName':'201203090202',
            'Txt_Password':'560127'
        })
loginUrl = 'http://www.ycjw.zjut.edu.cn/logon.aspx'
result = opener.open(loginUrl,postdata)
cookie.save(ignore_discard=True, ignore_expires=True)
gradeUrl = 'http://www.ycjw.zjut.edu.cn//stdgl/cxxt/byshmx.aspx'
result = opener.open(gradeUrl)
print result.read()