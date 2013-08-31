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
	
	def modalFooter= {
		out << "<div class='modal hide fade' id='myModal' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>\n"
		out << "<div class='modal-header'>\n"
		out << "<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>\n"
		out << "<div id='myModalLabel'><h3></h3></div>\n"
		out << "</div>\n"
		out << "<div class='modal-body'>\n"
		out << "</div>\n"
		out << "</div>\n"
		out << "<script type='text/javascript'>\n"
		out << "\$('a.btn').on('click', function(e) {\n"
		out << "e.preventDefault();\n"
		out << "var url = \$(this).attr('href');\n"
		out << "\$(\".modal-body\").html('<iframe width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowtransparency=\"true\" src=\"'+url+'\"></iframe>');\n"
		out << "});\n"
		out << "</script>\n"	
	}
	
	def userPref = { attrs, body ->
		def update = attrs.remove('update')?.toString()
		def updateShow = attrs.remove('updateShow')?.toString()
		if (params.linkchoice!=null) session.linkchoice=params.linkchoice
		if (session.linkchoice==null) session.linkchoice='_same'
		out << "<script type='text/javascript'>\n"
		out << " \$(document).ready(function() {\n"
		out << "\$('#" + update+"').hide();\n"
        	out << "\$('#" + updateShow+"').show();\n"
		out << "\$('#" + updateShow+"').click(function(){"
		out << "\$('#" + update+"').slideToggle();"
		out << "});\n"
		out << "var appendit='Link Open type:  - (${session.linkchoice }) - <br/>';\n"
		out << "appendit +='<a href=?linkchoice=_modal>Modal: Pop-up</a><br/>';\n"
		out << "appendit +='<a href=?linkchoice=_same>Same Window</a><br/>';\n"
		out << "appendit +='<a href=?linkchoice=_new>New Window</a><br/>';\n"
		out << "appendit +='<a href=?linkchoice=_multi>Multiple Choice</a><br/>';\n"
		out << "\$('#" + update+"').html(appendit);\n"
		out << "});\n"
		out << "</script>"
		
		
	}
	
}
