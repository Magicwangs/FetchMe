from django.shortcuts import render, render_to_response
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from django.template import RequestContext
from django.template.context_processors import csrf
from .models import User
from .forms import UploadFileForm

# Create your views here.
def index(request):
    #return HttpResponse("index")
    #return render(request, 'FetchMe/index.html')
    return JsonResponse({'content':'FetchMe'})
def login(request):
    if request.method == 'GET':
        loginResponse = {'isLogin' : False, 'csrfToken' : 0,}
        userNameStr = request.GET.get('userName')
        userPwdStr = request.GET.get('userPwd')
        user = User.objects.filter(userName=userNameStr, userPwd=userPwdStr)
        isUser = len(user)
        if(isUser):
            #return HttpResponse("login success")
            csrfToken = {}
            csrfToken.update(csrf(request))
            loginResponse['isLogin'] = True
            loginResponse['csrfToken'] = unicode(csrfToken['csrf_token'])
        return JsonResponse(loginResponse)
def uploadImage(request):
    if request.method == 'POST':
        form = UploadFileForm(request.POST, request.FILES)
        if form.is_valid():
			#with open('/tmp/%s.jpg' % form['title'].value(), 'wb+') as destination:
			with open('/tmp/%s' % request.FILES['fileUpload'], 'wb+') as destination:
				for chunk in request.FILES['fileUpload'].chunks():
					destination.write(chunk)
			return HttpResponse("upload success")
	return HttpResponse("upload failed")
def get_csrfToken(request):
    csrfToken = {}
    csrfToken.update(csrf(request))
    form = NameForm()
    return HttpResponse("hello %s" % csrfToken['csrf_token'])
