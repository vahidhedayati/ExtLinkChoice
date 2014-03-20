ExtLinkChoice 0.8
=============

Grails plugin which lets you provide a link choice to users, so a user gets a pop up choice when hovering links. It is designed to support a full url 
so you may wish to g.createLink (...,absolute:true) if you wish to use it internally since the original purpose was to allow external links to be opened via modelbox which by default is not supported



## Installation:
Add plugin Dependency :

	compile ":extlinkchoice:0.8" 

Or via grails command line:

	grails install-plugin extlinkchoice




Please refer to [example site](https://github.com/vahidhedayati/ExtLinkChoiceExample/) which has some samples, to explain what needs to be done now:


# Version info
	0.9 0.8 not resolving hostnames - changed resolv call back to original - 0.9 broken build will fix later
	0.8 converted all fixed values to attributes passed as optional values via taglib
	0.7 issues with low resolution and capped content fixed. 
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



## Either
#### [grails-app / views / link / index.gsp](https://github.com/vahidhedayati/ExtLinkChoiceExample/blob/master/grails-app/views/link/index.gsp)

	<extlink:userPref update="linkPanel" updateShow="linkChooser"/>
	<a href="#" id="linkChooser">Link Choice Chooser</a>
	<div id="linkPanel" name="linkPanel" >
	</div>
	


## Or:
#### [grails-app / views / link / example2.gsp](https://github.com/vahidhedayati/ExtLinkChoiceExample/blob/master/grails-app/views/link/example2.gsp)

	<!-- method 2 - do not need to call any of above to use select method -->
	<extlink:selectPref id='autolinkUpdater' noSelection="['null': 'Choose Link Method']" />

	
	
	
## Examples:
	

## pre < extlinkchoice:0.6

	<extlink:returnLink 
	link="http://www.example.com" 
	description="example.com" 
	choice="${session.linkchoice }" 
	/>

### Basic usage since 0.6
 	
	<extlink:returnLink 
	link="http://www.example.com" 
	description="example.com" 
	choice="${session.linkchoice }" 
	title="example.com website" 
	id="MyModal3"
	modalLabel="MyModalLabel3"
	/>

### Enhanced features since 0.8

Below you will see lots of new options that can be defined, if not defined the values shown in the example are 
the actual default values, which can of course be set by you to anything that is valid as shown: 


	<extlink:returnLink 
	link="http://www.example.com" 
	description="example.com" 
	choice="${session.linkchoice }" 
	title="example.com website" 
	id="MyModal3"
	modalLabel="MyModalLabel3"
	
	
	calctype="*" 			[ModalBox: choices : */-+ ]
	height="0.6"			[ModalBox: ScreenSize * 0.6 = modalbox height ]
	width="0.6"			[ ModalBox: ScreenSize * 0.6 = modalbox width ]
	bodyheight="0.4"	[ modal-body: container height default is 0.2 down from actual height ]
	bodywidth='98%'		[ modal-body: container width default is 98%]
	overflow="hidden"	[ ModalBox: css value for overflow(scrollbars) to display modalbox ]
	position="fixed"	[ ModalBox: css value for position to display modalbox ]
	top="0"		[ ModalBox: css value for top to display modalbox from ]
	margintop='10em'	[ ModalBox: css value for margintop to display modalbox from ]
	marginright='auto'	[ ModalBox: css value for marginright to display modalbox from ]
	left='auto'			[ ModalBox: css value for left to display modalbox from ]
	right='auto'		[ ModalBox: css value for right to display modalbox from ]
	
	
	iframescrolling='auto' 	[ modal-body: iframe scrolling value ]
	iframetransparency='true' [ modal-body: iframe transparency value ]
	iframezoom='1'	[ modal-body: iframe zoom  value ]
	iframewidth='100%'	[ modal-body: iframe width ]
	iframeheight='100%'	[ modal-body: iframe height ] 
	iframemargin='0'	[ modal-body: iframe margin ] 
	iframepadding='0'	[ modal-body: iframe padding ] 
	 
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



