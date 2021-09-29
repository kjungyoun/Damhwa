#db_settings
DATABASES = {
	'default' : {
		'ENGINE' : 'django.db.backends.mysql',
		'NAME' : 'damhwa',
		'USER' : 'ssafy',
		'PASSWORD' : 'ssafy',
		# 'HOST' : '3.38.108.66', 배포 시
		'HOST' : 'localhost',
		'PORT' : '3306',
	}
}
SECRET_KEY = 'django-insecure-6=*xlb*5on8l#03m1r)9p16&psuk&kvzlj-k&*whr%=60uu_v@'
