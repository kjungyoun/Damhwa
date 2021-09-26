# rest_framework
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

# Message Recommend
@api_view(['POST', 'GET'])
def msg_recomm(request):
    if request.method == 'POST':
        print("Django Success!")
        data = request.data.get('msg') # Spring 요청 데이터
        print(data)
        return Response(data="call OK!", status=status.HTTP_200_OK)

