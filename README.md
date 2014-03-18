ExtLinkChoice 0.6
=============

Grails plugin which lets you provide a link choice to users, so a user gets a pop up choice when hovering links. It is designed to support a full url 
so you may wish to g.createLink (...,absolute:true) if you wish to use it internally since the original purpose was to allow external links to be opened via modelbox which by default is not supported



## Installation:
Add plugin Dependency :

	compile ":extlinkchoice:0.6" 

Or via grails command line:

	grails install-plugin extlinkchoice




Please refer to [example site](https://github.com/vahidhedayati/ExtLinkChoiceExample/) which has some samples, to explain what needs to be done now:


# Version info

	0.6 minor modification to loadmodalbox template - tested and working against standard grails site and kickstart with boostrap
	0.5 re-write of most of the plugin.
	 

####/ grails-app / views / layouts / main.gsp 

		<g:javascript library="jquery"/>
		<g:layoutHead/>
		<r:layoutResources />
		<extlink:loadbootstrap/>
  

Jquery is called as well as extlink:loadbootstrap, you need to add this to your main.gsp for things to work, 
if your site is already bootstrapped then no need to do this, you need to call: 

	<extlink:loadplugincss/> rather than <extlink:loadbootstrap/>



#### [grails-app / controllers / gtest / LinkController.groovy](https://github.com/vahidhedayati/ExtLinkChoiceExample/blob/master/grails-app/controllers/extlinkchoiceexample/LinkController.groovy)
Create a Controller it is called LinkController 



#### [grails-app / views / link / index.gsp](https://github.com/vahidhedayati/ExtLinkChoiceExample/blob/master/grails-app/views/link/index.gsp)

	<extlink:userPref update="linkPanel" updateShow="linkChooser"/>
	<a href="#" id="linkChooser">Link Choice Chooser</a>
	<div id="linkPanel" name="linkPanel" >
	</div>
	
	<br/>
	
	<extlink:returnLink 
		link="http://www.grails.org" 
		description="Main Grails Site" 
		choice="${session.linkchoice}" 
		title="Grails.ORG" 
		id="MyModal1"
		modalLabel="MyModalLabel1"
	/>
	
	<extlink:returnLink 
		link="http://www.grails.info" 
		description="Grails information Site" 
		choice="${session.linkchoice }" 
		title="Grails.INFO" 
		id="MyModal2" 
		modalLabel="MyModalLabel2"
	/>
	
	<extlink:returnLink 
	link="http://www.happy.com" 
	description="Happy.com" 
	choice="${session.linkchoice }" 
	title="Walmart happy.com" 
	id="MyModal3"
	modalLabel="MyModalLabel3"
	/>


The first component calls userPref which loads up a link choice link - upon clicking it drops down with options to update all the links on this index page.
So if modal is selected then all links will open up in modal format

the returnLink is now doing all the work, 

	it takes url as variable called link 
	description for the link and which appears on the modal pop up
	choice which is session.linkchoice - which is what was last defined, 
	title is the title of modal box 
	id is the id for modalbox 
	modalLabel is the label to give Modalbox 

Id and modalLabel need to be unique upon multiple calls of this taglib on the same page



#### [grails-app / views / link / example2.gsp](https://github.com/vahidhedayati/ExtLinkChoiceExample/blob/master/grails-app/views/link/example2.gsp)

	<!-- method 2 - do not need to call any of above to use select method -->
	<extlink:selectPref id='autolinkUpdater' noSelection="['null': 'Choose Link Method']" />


Is the only difference between both pages, it simply loads up a select box for options and upon updates the page is redirected back with new config in session

This was designed with the purpose of using hostnames within url and then letting site resolve those hostnames to fqdn.

So you can use
 
	resolvit=1

If you have a similar use for it, you link could be something like

	link="http://hostname/folder1/folder2/file.htm"
	
It will still resolve the hostname and show link as http://hostname.resolved-domain.com/folder1/folder2/file.htm


The limitation of this plugin is the fact that it was designed for external urls, there is nothing stopping you from using it internally its just a bit more fuss and probably better plugins for it, none the less this is how to go about it:

 	 
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



