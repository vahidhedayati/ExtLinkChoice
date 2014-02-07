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
		def choice=attrs.remove('choice')?.toString()
		if (!modalLabel) modalLabel='myModalLabel'
		int resolv=0
		if (!modalLabel) modalLabel=description
		if (!choice) choice='_same'
		if ((resolveit!=null) && (resolveit.matches("[0-9]+"))) { resolv=Integer.parseInt(resolveit) } 
		if (!resolveit) resolveit=resolv
		if(link  && description) {
			def result = extLinkChoiceService.returnLink(link,description,resolv,modalLabel,choice)
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

	def selectPref = {attrs ->
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}
		def clazz = ""
		def name = ""
		if (!attrs.controller) attrs.controller= "extlinkchoice"
		if (!attrs.action) attrs.action= "linkchooser"
		if (!attrs.value) attrs.value =""
		if (attrs.class) clazz = " class='${attrs.class}'"
		if (attrs.name) {
		name = "${attrs.name}"
		}
		else {
		name = "${attrs.id}"
		}
		if (!attrs.noSelection) {
			throwTagError("Tag [noSelection] is missing required attribute")
		}
		if (!attrs.appendValue) attrs.appendValue='null'
		if (!attrs.appendName) attrs.appendName='Values Updated'
		def primarylist=['Modal Popup','Same Window','New Window','Multiple Choice' ]
		def gsattrs=[ 'id': "${attrs.id}", value: "${attrs.value}", name: name ]
		gsattrs['from'] = primarylist
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value)',onSuccess:''+attrs.id+'Update(data)')}"
		out<< g.select(gsattrs)
		out << "\n<script type='text/javascript'>\n"
		out << "function ${attrs.id}Update(data) { \n"
		out << "var newDoc = document.open('text/html', 'replace');\n"
		out << "newDoc.write(data);\n"
		out << "newDoc.close();\n"
		out << "}\n"
		out << "</script>\n"	
	}
	
	 def modalbootstrapFooter= {

                out << """<div class="modal  fade" id="myModal" role="dialog"  aria-hidden="true" >
                <div class="modal-dialog">
                <div class="modal-content"  style="width:60em;height:50em;overflow:auto;">
                <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">x</button>
                <h3></h3>
                </div>

                <div class="modal-body"  style="width:100%;height:100%; ">

                </div></div></div></div>"""


                out << "<script type='text/javascript'>\n"
                out << "\$('a.btn').on('click', function(e) {\n"
                out << "e.preventDefault();\n"
                out << "var url = \$(this).attr('href');\n"
                out << "\$(\".modal-body\").html('<iframe width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowtransparency=\"false\" src=\"'+url+'\"></iframe>');\n"
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
