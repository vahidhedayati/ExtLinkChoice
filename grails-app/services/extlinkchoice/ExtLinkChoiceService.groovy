package extlinkchoice


class ExtLinkChoiceService {
	
	
	/*
	 * returnLink takes the link,description and integer of 1 to
	 * resolve or 0 to not resolve the given url
	 */
	String returnLink(String link, String description,int resolveit,String modalLabel,String choice,String id,String title) {
		return finalResult(link,description,choice,resolveit,modalLabel,id,title)
		
	}
		
		
	/*
	 * Wrapper for above services
	 */
	String finalResult(String link, String description,String csetting,int resolveit,String modalLabel,String id,String title) {
		def newLink1=link
		String output
		
		if (resolveit==1) {
			DnsService ds=new DnsService()
			newLink1=ds.resolveUrlHost(link)
		}
		if ( (csetting.equals('')) && ( csetting==null)) { csetting="_same" }
		if (csetting.equals("_new")) {
			output="<div class=linkchoicebutton>"
			output+="<a href='"+newLink1+"' target='_newwindow'>"+description+"</a></div>"
		} else if (csetting.equals("_same")) {
			output="<div class=linkchoicebutton><a href='"+newLink1+"'>"+description+"</a></div>"
		} else if (csetting.equals("_modal")) {
			output="<div class=linkchoicebutton>"
			output+="<a data-toggle='modal' class='btn' href='"+newLink1+"' data-target='#${id}'>"
			output+=description+"</a></div>"
			
		}else {
			output ="<ul id='user_spot'>"
			output +="<li><a data-toggle='modal' class='btn' href='"+newLink1+"' data-target='#${id}'>"
			output +=description+"</a>"
			output +="<ul id='user_spot_links'>"
			output +="<li><a href='"+newLink1+"'>Same Window</a></li>"
			output +="<li><a href='"+newLink1+"' target='_newwindow'>New Tab</a></li>"
			output +="</ul></li></ul>"
			output +="<script type='text/javascript'>\n"
			output +=" \$(document).ready(function() {\n"
			output +="\$('#" + modalLabel+"').html('"+newLink1+" ("+description+")');\n"
			output +="});\n"
			output +="</script>"
		}
		return output
	}
		
	
	/*
	 * 
	 * Return Session information to this service
	
		private HttpSession getSession() {
			return RequestContextHolder.currentRequestAttributes().getSession()
		}
	*/
	 
}
