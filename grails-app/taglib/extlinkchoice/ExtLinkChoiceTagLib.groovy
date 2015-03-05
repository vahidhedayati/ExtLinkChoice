package extlinkchoice

import org.grails.plugins.web.taglib.ApplicationTagLib

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
	 
	def loadbootstrap= {attrs, body ->
		out << g.render(contextPath: pluginContextPath,template: 'loadbootstrap')
		//Further override checks set modalBox config up
		modalBoxConfig(attrs)
		
		//Further override checks set modalBox IFRAME config up
		modalIframeConfig(attrs)
		// Render modalbox for this link
		// -- TODO need to maybe revert back to original method and think of a better way of using the samebox but showing buttons titles etc
		out << g.render(contextPath: pluginContextPath,template: 'loadmodalbox', model: [attrs:attrs])
		
		
	}
	
	
	/*
	 * This loads in customised css for this plugin
	 * If your site already has been bootstrapped then use this
	 *
	 * <r:layoutResources />
	 * <extlink:loadplugincss/>
	 * </head>
	 */
	def loadplugincss= {attrs, body ->
		out << g.render(contextPath: pluginContextPath,template: 'loadplugincss')
		
		//Further override checks set modalBox config up
		modalBoxConfig(attrs)
		
		//Further override checks set modalBox IFRAME config up
		modalIframeConfig(attrs)
		// Render modalbox for this link
		// -- TODO need to maybe revert back to original method and think of a better way of using the samebox but showing buttons titles etc
		out << g.render(contextPath: pluginContextPath,template: 'loadmodalbox', model: [attrs:attrs])
		
		
	}
	
	
	/*
	 * returnLink - shows the link by checking link type and passing it to service
	 * loads modalbox per call, this now has removed the requirement for configuring footer commands etc
	 * this is much cleaner and carries titles descriptions etc to modalbox
	 */
	def returnLink =  { attrs, body ->
		
		// Pick a random num 0-50 to add to id if missing
		def rnum = new Random().nextInt(50);
		
		boolean extlink=true
		
		if ((attrs.controller)&&(attrs.action)) {
			extlink=false
		}
		
		/*
		 * Throw error if no url given as :
		 * 
		 * Attribute: link
		 * 
		 */
		if ((!attrs.link)&&(extlink)) {
			throwTagError("Tag [returnLink] is missing required attribute [link]")
		}
		
		if (extlink==false){
            ApplicationTagLib g = new ApplicationTagLib()
			attrs.link= g.createLink(controller: attrs.controller, action: attrs.action, params:attrs,  absolute: 'true' )
			if (!attrs.link) { 
				throwTagError("Tag [returnLink] could not generate [link] from controller/action")
			}
		} 
		
		/*
		 * Attribute: description
		 * Set a description or title for link
		 * 
		 */
		if (!attrs.description) {
			attrs.description='My URL'
		}
		
		/*
		 * Attribute: title
		 * 
		 * Set the title for your modalbox
		 * 
		 */
		if (!attrs.title) {
			attrs.title=attrs.description
		}
		
		/*
		 * Attribute: id
		 * 
		 * This is the id of your modalbox which also gets set as part of 
		 * modalLabel if not defined already
		 * 
		 */
		if (!attrs.id) {
			attrs.id='myModal'+rnum
		}
		
		
		/*
		 * Attribute modalLabel
		 * 
		 */
		if (!attrs.modalLabel) {
			attrs.modalLabel='myModalLabel'+rnum
		}
			
		/*
		 * Attribute: choice
		 * 
		 * How to show the link choices being:
		 * 
		 * _same 
		 * _newwindow
		 * _modal
		 * _multi or anything that does not match above 3.
		 * 
		 * 
		 * Same takes over same window, new window in a tab or new window depending on browser,
		 * modal in modal box and
		 * multi is multiple choice the user picks where clicking link opens modal and 
		 * hovering over gives choice to open over same or new window
		 * 
		 */
		if (!attrs.choice) {
			attrs.choice='_same'
		}
			
		
		/*
		 * Attribute resolveit
		 * 
		 * resolveit values: 
		 * 0 default 
		 * 1 yes resolve given above link value
		 * 
		 * so if you gave link:
		 * 
		 * http://myhost/logs/log1.txt
		 * 
		 * It would then attempt to resolve myhost and actually present link as:
		 * 
		 * http://myhost.mydomain.com/logs/log1.txt
		 * 
		 * (depending on your DNS or if public dns )
		 * 
		 */
		int resolv=0
		def resolveit=attrs.resolveit
		if ((resolveit!=null) && (resolveit.matches("[0-9]+")))  { resolv=Integer.parseInt(resolveit) } 

		// Now process the link as per user action 
		def result = extLinkChoiceService.returnLink(attrs.link.toString(),attrs.description.toString(),resolv,attrs.modalLabel.toString(),attrs.choice.toString(),attrs.id.toString(),attrs.title.toString())
		
		//Show results on user screen
		out << "${result}"
		
		
	}
	
	/*
	 * Taglib selectPref:
	 * 
	 * Usage:
	 * <extlink:selectPref id='autolinkUpdater' noSelection="['null': 'Choose Link Method']" />
	 * 
	 * This now loads up a select box and on mouse out of the provided options the
	 * action is set in session and page is reverted back to where it was
	 * 
	 * TODO this needs to tidy up - possibly relook at redirect and maybe update div if given
	 * if not then reload page  
	 * 
	 */

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
	
/*
 * 
 * Taglib: userpref 
 * 
 * Example:
 * <extlink:userPref update="linkPanel" updateShow="linkChooser"/>
 * <a href="#" id="linkChooser">Link Choice Chooser</a>
 * <div id="linkPanel" name="linkPanel"></div>
 * 
 * 
 * This then loads a template into the div and shows link to let user have a drop down when selecting choice
 * 
 */
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
		
		/*
		 * Attribute close
		 *
		 *  Defines actual modal-body Close button label
		 *  default = X
		 *
		 */
		if (!attrs.close) {
			attrs.close='X'
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
