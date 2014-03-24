ExtLinkChoice 0.11
=============

Grails plugin which lets you provide a link choice to users, so a user gets a pop up choice when hovering links. It is designed to support a full url 
so you may wish to g.createLink (...,absolute:true) if you wish to use it internally since the original purpose was to allow external links to be opened via modelbox which by default is not supported



## Installation:
Add plugin Dependency :

	compile ":extlinkchoice:0.11" 

Or via grails command line:

	grails install-plugin extlinkchoice




Please refer to [example site](https://github.com/vahidhedayati/ExtLinkChoiceExample/) which has some samples, to explain what needs to be done now:


# Version info
```
	0.11 moved multi modalbox calls back into one call - modified to include all the goodies from previous version, Controller+Action calls added.
	0.10 fix bug from 0.9 working plugin and added extra comments to taglib
	0.9 0.8 not resolving hostnames - changed resolv call back to original has a bug 
	0.8 converted all fixed values to attributes passed as optional values via taglib
	0.7 issues with low resolution and capped content fixed. 
	0.6 minor modification to loadmodalbox template - tested and working against standard grails site and kickstart with boostrap
	0.5 re-write of most of the plugin.
```	
	 

####/ grails-app / views / layouts / main.gsp 
```
		<g:layoutHead/>
		<g:javascript library="jquery"/>
		<r:layoutResources />
		<extlink:loadbootstrap/>
```		
  

Jquery is called as well as extlink:loadbootstrap, you need to add this to your main.gsp for things to work, 
if your site is already bootstrapped then no need to do this, you need to call: 
```gsp
	<extlink:loadplugincss/> 
```		
	rather than 
```gsp	
	<extlink:loadbootstrap/>
```	

Advanced calls to either loadbootstrap or loadplugincss :
```gsp
<extlink:loadbootstrap
			calctype="*"         
			height="0.6"         
			width="0.6"         
			bodyheight="0.4"    
			bodywidth='98%'     
			overflow="hidden"   
			position="fixed"    
			top="0"    
			margintop='10em' 
			marginright='auto' 
			left='auto'        
			right='auto'       
			iframescrolling='auto' 
			iframetransparency='true' 
			iframezoom='1'  
			iframewidth='100%' 
			iframeheight='100%'  
			iframemargin='0'     
			iframepadding='0'    
    	/>	
```


#### [grails-app / controllers / gtest / LinkController.groovy](https://github.com/vahidhedayati/ExtLinkChoiceExample/blob/master/grails-app/controllers/extlinkchoiceexample/LinkController.groovy)
Create a Controller it is called LinkController 



## Either
#### [grails-app / views / link / index.gsp](https://github.com/vahidhedayati/ExtLinkChoiceExample/blob/master/grails-app/views/link/index.gsp)
```
	<extlink:userPref update="linkPanel" updateShow="linkChooser"/>
	<a href="#" id="linkChooser">Link Choice Chooser</a>
	<div id="linkPanel" name="linkPanel" >
	</div>
```
	
This first component calls userPref which loads up a link choice link - upon clicking it drops down with options to update all the links on this index page.
So if modal is selected then all links will open up in modal format



## Or:
#### [grails-app / views / link / example2.gsp](https://github.com/vahidhedayati/ExtLinkChoiceExample/blob/master/grails-app/views/link/example2.gsp)
```
	<!-- method 2 - do not need to call any of above to use select method -->
	<extlink:selectPref id='autolinkUpdater' noSelection="['null': 'Choose Link Method']" />
```
	
	
	
## External Link Examples:
```
	<extlink:returnLink 
	link="http://www.example.com" 
	description="example.com" 
	choice="${session.linkchoice }" 
	/>

	<extlink:returnLink 
	link="http://www.example.com" 
	description="example.com" 
	choice="${session.linkchoice }" 
	title="example.com website" 
	/>
```

Description of input
```
description for the link and which appears on the modal pop up
choice which is session.linkchoice - which is what was last defined, 
title is the title of modal box 
```

## Internal Links (Controllers/Actions)
```
<extlink:returnLink 
	controller="tester"
	action="index"
	description="Tester Controller" 
	choice="${session.linkchoice }" 
	title="index of Tester Controller" 
/>
```

The above will produce a link for your controller tester and open index as a link



## External Links resolving hostnames within URL 
This was designed with the purpose of using hostnames within url and then letting site resolve those hostnames to fqdn.

So you can use
```
resolvit=1
```

If you have a similar use for it, you link could be something like
```
link="http://hostname/folder1/folder2/file.htm"
```
	
It will still resolve the hostname and show link as:
```
http://hostname.resolved-domain.com/folder1/folder2/file.htm
```




## Manually creating your own controller / actions
##### This may come in handy if you want to pass loads of variables across to your modal box:
 	 
		<% def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib() %>
 		<g:set var="myAttachparams" value="[formId:'AttachForm',title:'Attache a file', controller: 'mailingListAttachments', callPage: 'formAjax' , divId: 'mailerAttachments', id: 'ATTACH']" />
 		<% def confirmURL= g.createLink(controller: 'MYController', action: 'MyAction', params:myAttachparams,  absolute: 'true' ) %>
		<extlink:returnLink 
		link="${confirmURL}" 
		description="My created url" 
		choice="${session.linkchoice}" 
		title="whatever" 
		id="MyModal5"
		modalLabel="MyModalLabel5"
		/>



