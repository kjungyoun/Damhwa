from django.db import models


class Flower(models.Model):
    fno = models.BigIntegerField(primary_key=True)
    fnamekr = models.CharField(db_column='fnameKR', max_length=40, blank=True, null=True)  # Field name made lowercase.
    fnameen = models.CharField(db_column='fnameEN', max_length=40, blank=True, null=True)  # Field name made lowercase.
    fmonth = models.IntegerField(blank=True, null=True)
    fday = models.IntegerField(blank=True, null=True)
    flang = models.CharField(max_length=100, blank=True, null=True)
    fcontents = models.CharField(max_length=500, blank=True, null=True)
    fuse = models.CharField(max_length=500, blank=True, null=True)
    fgrow = models.CharField(max_length=500, blank=True, null=True)
    img1 = models.CharField(max_length=400, blank=True, null=True)
    img2 = models.CharField(max_length=400, blank=True, null=True)
    img3 = models.CharField(max_length=400, blank=True, null=True)
    watercolor_img = models.CharField(max_length=400, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'flower'


class History(models.Model):
    hno = models.BigIntegerField(primary_key=True)
    userno = models.ForeignKey('User', models.DO_NOTHING, db_column='userno', blank=True, null=True)
    fno = models.ForeignKey(Flower, models.DO_NOTHING, db_column='fno', blank=True, null=True)
    htype = models.IntegerField(blank=True, null=True)
    contents = models.CharField(max_length=500, blank=True, null=True)
    to = models.CharField(max_length=100, blank=True, null=True)
    regdate = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'history'


class User(models.Model):
    userno = models.BigIntegerField(primary_key=True)
    email = models.CharField(max_length=50)
    username = models.CharField(max_length=30, blank=True, null=True)
    profile = models.CharField(max_length=400, blank=True, null=True)
    token = models.CharField(max_length=500, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'user'

class Emotion(models.Model):
    fno = models.BigIntegerField(primary_key=True)
    happy = models.FloatField(db_column='Happy', blank=True, null=True)  # Field name made lowercase.
    unstable = models.FloatField(db_column='Unstable', blank=True, null=True)  # Field name made lowercase.
    embarrass = models.FloatField(blank=True, null=True)
    sad = models.FloatField(blank=True, null=True)
    angry = models.FloatField(blank=True, null=True)
    hurt = models.FloatField(blank=True, null=True)
    emotion = models.TextField(db_column='Emotion', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'emotion'