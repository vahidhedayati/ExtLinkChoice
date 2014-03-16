package extlinkchoice
/*
 * This taglib contains the service call and a method to return user choice as a dynamic list viewable in any gsp so long
 *as end divId of linkPanel and linkChooser are defined and set up properly as per example
 */
class ExtLinkChoiceTagLib {
	
	static namespace = "extlink"
	
	def extLinkChoiceService
	
	/*
	 * This loads in customised bootstrap.css and default bootstrap.js
	 * If your site already has these then no need to run, otherwise:
	 *
	 * <r:layoutResources />
	 * <extlink:loadbootstrap/>
	 * </head>
	 *
	 *(notice the extra tag loadbootstrap above)
	 *In your main file to run in overall site
	 *OR just call this on a specific gsp page if you have specific use
	 */
	 
	def loadbootstrap= {
		out << g.render(contextPath: pluginContextPath,template: 'loadbootstrap')
	}
	
	
	/*
	 * This loads in customised css for this plugin
	 * If your site already has been bootstrapped then use this
	 *
	 * <r:layoutResources />
	 * <extlink:loadplugincss/>
	 * </head>
	 */
	def loadplugincss= {
		out << g.render(contextPath: pluginContextPath,template: 'loadplugincss')
	}
	
	
	/*
	 * returnLink - shows the link by checking link type and passing it to service
	 * loads modalbox per call, this now has removed the requirement for configuring footer commands etc
	 * this is much cleaner and carries titles descriptions etc to modalbox
	 */
	def returnLink =  { attrs, body ->
		
		if (!attrs.title) {
			attrs.title='MY Modal Page'
		}
		if (!attrs.id) {
			attrs.id='myModal'
		}
		if (!attrs.modalLabel) {
			attrs.modalLabel='myModalLabel'
		}
			
		if (!attrs.choice) {
			attrs.choice='_same'
		}
			
		if ((attrs.resolveit) && (attrs.resolveit.matches("[0-9]+"))) { 
			attrs.resolvit=Integer.parseInt(attrs.resolveit) 
		}
		 
		if (!attrs.resolveit) {
			attrs.resolveit=0
		}
			
		if(attrs.link  && attrs.description) {
			out << g.render(contextPath: pluginContextPath,template: 'loadmodalbox', model: [attrs:attrs])
			def result = extLinkChoiceService.returnLink(attrs.link.toString(),attrs.description.toString(),attrs.resolv ?: 0,attrs.modalLabel.toString(),attrs.choice.toString(),attrs.id.toString(),attrs.title.toString())
			out << "${result}"
		}
		
	}
	

	def selectPref = {attrs ->
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}
		def clazz = ""
		def name = ""
		if (!attrs.controller) {
			attrs.controller= "extlinkchoice"
		}	
		if (!attrs.action) {
			attrs.action= "linkchooser"
		}	
		if (!attrs.value) {
			attrs.value =""
		}	
		if (attrs.class) {
			clazz = " class='${attrs.class}'"
		}	
		if (attrs.name) {
			name = "${attrs.name}"
		}
		else {
			name = "${attrs.id}"
		}
		if (!attrs.noSelection) {
			throwTagError("Tag [noSelection] is missing required attribute")
		}
		if (!attrs.appendValue) {
			attrs.appendValue=''
		}	
		if (!attrs.appendName) {
			attrs.appendName='Values Updated'
		}	
		def primarylist=['Modal Popup','Same Window','New Window','Multiple Choice' ]
		def gsattrs=[ 'id': "${attrs.id}", value: "${attrs.value}", name: name ]
		gsattrs['from'] = primarylist
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value)',onSuccess:''+attrs.id+'Update(data)')}"
		out << g.select(gsattrs)
		out << g.render(contextPath: pluginContextPath,template: 'reloadpage', model: [attrs:attrs])
	}
	

	def userPref = { attrs, body ->
		def update = attrs.remove('update')?.toString()
		def updateShow = attrs.remove('updateShow')?.toString()
		
		if (params.linkchoice)  {
			session.linkchoice=params.linkchoice
		}	
		if (!session.linkchoice) {
			session.linkchoice='_same'
		}	
		out << g.render(contextPath: pluginContextPath,template: 'userpref', model: [update:update,updateShow:updateShow,linkchoice:session.linkchoice])
	}
	
}
