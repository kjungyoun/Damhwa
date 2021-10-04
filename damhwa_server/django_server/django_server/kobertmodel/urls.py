from django.urls import path
from . import views


app_name = 'kobertmodel'

urlpatterns = [
    path('msg', views.msg_recomm),
    path('state', views.state_recomm),

]
