#coding:utf8
'''
@author: chenguanfu
'''
import urllib2

requset = urllib2.Request('http://blog.csdn.net/cqcre')
try:
    response = urllib2.urlopen(requset)
    print response.read()
except urllib2.URLError, e:
    if hasattr(e,"code"):
        print e.code
    if hasattr(e,"reason"):
        print e.reason
    else:
        print "OK"
