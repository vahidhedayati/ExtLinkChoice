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
			modalBoxConfig(attrs)
			modalIframeConfig(attrs)
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
	

	private void modalBoxConfig(attrs) { 
		/*
		 * 
		$(".modal-body").css('height',$( window ).height()*0.4);
		$(".modal-body").css('width','98%');
		 * 
		 * 
		 */
		
	
	
	
		/*
		 * Attribute calctype
		 *
		 *  Defines how to calculate the modalbox resolution
		 *  
		 *  default = '*' 
		 *  (* means multiply)
		 *  
		 *  default values of height and width are in decimals i.e. 
		 *  0.6 so this works well in this situation
		 *  
		 *  if you wanted you could choose: 
		 *  default = '/'  
		 *  (/ means  divide)
		 *  
		 *  And then provide 2 for height which would = 
		 *  
		 *  (screensize) / 2 = your modalbox height
		 *  
		 *  
		 *  
		 *
		 *
		 */
		if (!attrs.calctype) {
			attrs.calctype='*'
		}
		
		/*
		 * Attribute height
		 *
		 *  Defines actual modalbox css value for the height of modalbox
		 *  default = 0.6
		 *  
		 *  This is worked out by actual page size * 0.6 so define something more suitable 
		 *  that can 
		 *
		 */
		if (!attrs.height) {
			attrs.height='0.6'
		}
		
		/*
		 * Attribute width
		 *
		 *  Defines actual modalbox css value for the width of modalbox
		 *  default = 0.6
		 *
		 *  This is worked out by actual page size * 0.6 so define something more suitable
		 *  that can
		 *
		 */
		if (!attrs.width) {
			attrs.width='0.6'
		}
		
		/*
		 * Attribute bodyheight
		 *
		 *  Defines actual modal-body css value for the height of modal-body height
		 *  default = 0.4
		 *
		 *  This is worked out by actual page size * 0.4 so define something more suitable
		 *  that can
		 *
		 */
		if (!attrs.bodyheight) {
			attrs.bodyheight='0.4'
		}
		
		/*
		 * Attribute bodywidth
		 *
		 *  Defines actual modal-body css value for the height of modal-body width
		 *  default = 98%
		 *
		 *  This is worked out in percentages - change if required
		 *  that can
		 *
		 */
		if (!attrs.bodywidth) {
			attrs.bodywidth='98%'
		}

		/*
		 * Attribute overflow
		 *
		 *  Defines actual modalbox css value for overflow of scroll bars for main modalbox 
		 *  default = hidden
		 *
		 */
		if (!attrs.overflow) {
			attrs.overflow='hidden'
		}
		
	
		/*
		 * Attribute position 
		 *  
		 *  Defines actual modalbox css value for position on the screen 
		 *  default = fixed 
		 * 
		 */
		if (!attrs.position) {
			attrs.position='fixed'
		}
		
		/*
		 * Attribute top
		 *
		 *  Defines actual modalbox css value for top on the screen
		 *  default = 0
		 *
		 */
		if (!attrs.top) {
			attrs.top='0'
		}
		
		/*
		 * Attribute margintop
		 *
		 *  Defines actual modalbox css value for margintop on the screen
		 *  default = 10em
		 *
		 */
		if (!attrs.margintop) {
			attrs.margintop='10em'
		}
		
		/*
		 * Attribute marginright
		 *
		 *  Defines actual modalbox css value for marginright on the screen
		 *  default = 10em
		 *
		 */
		if (!attrs.marginright) {
			attrs.marginright='auto'
		}
		
		/*
		 * Attribute left
		 *
		 *  Defines actual modalbox css value for left on the screen
		 *  default = auto
		 *
		 */
		if (!attrs.left) {
			attrs.left='auto'
		}
		
		/*
		 * Attribute right
		 *
		 *  Defines actual modalbox css value for right on the screen
		 *  default = auto
		 *
		 */
		if (!attrs.right) {
			attrs.right='auto'
		}
	}
	
	
	private void modalIframeConfig(attrs) {
		
		/*
		 * Attribute iframescrolling
		 *
		 *  Defines actual modal-body iframe value for the scrolling
		 *  default = auto

		 *
		 */
		if (!attrs.iframescrolling) {
			attrs.iframescrolling='auto'
		}
		/*
		 * Attribute iframetransparency
		 *
		 *  Defines actual modal-body iframe value for the transparency
		 *  default = true

		 *
		 */
		if (!attrs.iframetransparency) {
			attrs.iframetransparency='true'
		}
		
		
		/*
		 * Attribute iframezoom
		 *
		 *  Defines actual modal-body iframe value for the zoom
		 *  default = 1

		 *
		 */
		if (!attrs.iframezoom) {
			attrs.iframezoom='1'
		}
		/*
		 * Attribute iframewidth
		 *
		 *  Defines actual modal-body iframe value for the width
		 *  default = 100%

		 *
		 */
		if (!attrs.iframewidth) {
			attrs.iframewidth='100%'
		}
		
		/*
		 * Attribute iframeheight
		 *
		 *  Defines actual modal-body iframe value for the height
		 *  default = 100%

		 *
		 */
		if (!attrs.iframeheight) {
			attrs.iframeheight='100%'
		}
		
		/*
		 * Attribute iframemargin
		 *
		 *  Defines actual modal-body iframe value for the margin
		 *  default = 0

		 *
		 */
		if (!attrs.iframemargin) {
			attrs.iframemargin='0'
		}
		
		/*
		 * Attribute iframepadding
		 *
		 *  Defines actual modal-body iframe value for the padding
		 *  default = 0

		 *
		 */
		if (!attrs.iframepadding) {
			attrs.iframepadding='0'
		}
		
		
	}
}
