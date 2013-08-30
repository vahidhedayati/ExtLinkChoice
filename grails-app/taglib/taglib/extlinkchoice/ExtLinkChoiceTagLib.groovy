package extlinkchoice
/*
 * This taglib contains the service call and a method to return user choice as a dynamic list viewable in any gsp so long
 * as end divId of linkPanel and linkChooser are defined and set up properly as per example
 */
class ExtLinkChoiceTagLib {
	static namespace = "extlink"
	def extLinkChoiceService
	def returnLink =  { attrs, body ->
		def link = attrs.remove('link')?.toString()
		def description = attrs.remove('description')?.toString()
		def modalLabel  = attrs.remove('modalLabel')?.toString()
		def resolveit=attrs.remove('resolveit')?.toString()
		if (!modalLabel) modalLabel='Example Link'
		int resolv=0
		if ((resolveit!=null) && (resolveit.matches("[0-9]+"))) { resolv=Integer.parseInt(resolveit) } 
		if(link  && description) {
			def result = extLinkChoiceService.returnLink(link,description,resolv,modalLabel)
			out << "${result}"
			
			
		}
	}
	

	def userPref = { attrs, body ->
		def update = attrs.remove('update')?.toString()
		def updateShow = attrs.remove('updateShow')?.toString()
		if (params.linkchoice!=null) { session.linkchoice=params.linkchoice; }
		out << "<script type='text/javascript'>\n"
		out << " \$(document).ready(function() {\n"
		out << "\$('#" + update+"').hide();\n"
        	out << "\$('#" + updateShow+"').show();\n"
		out << "\$('#" + updateShow+"').click(function(){"
		out << "\$('#" + update+"').slideToggle();"
		out << "});\n"
		out << "var appendit='Link Open type:  - (${session.linkchoice }) - <br/>';\n"
		out << "appendit +='<a href=?linkchoice=_modal>Pop up Modal</a><br/>';\n"
		out << "appendit +='<a href=?linkchoice=_same>Same Window</a><br/>';\n"
		out << "appendit +='<a href=?linkchoice=_new>New Window</a><br/>';\n"
		out << "appendit +='<a href=?linkchoice=_multi>multiplechoice</a><br/>';\n"
		out << "\$('#" + update+"').html(appendit);\n"
		out << "});\n"
		out << "</script>"
		
		
	}
	
}
