from __future__ import unicode_literals

from django.db import models

# Create your models here.
class User(models.Model):
    def __str__(self):
        return self.userName
    userName = models.CharField(max_length=200)
    userPwd = models.CharField(max_length=200)
