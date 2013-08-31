package extlinkchoice

	
import javax.servlet.http.HttpSession
import org.springframework.web.context.request.RequestContextHolder
	
class ExtLinkChoiceService {
	String output
	/*
	 * returnLink takes the link,description and integer of 1 to
	 * resolve or 0 to not resolve the given url
	 */
	String returnLink(String link, String description,int resolveit,String modalLabel) {
		String csetting=getSession().linkchoice
		output=finalResult(link,description,csetting,resolveit,modalLabel)
		return output
	}
		
	/*
	 * This is very similar to above but works with a controller and
	 * requires an extra input of: csetting = current setting
	 */
	String returnControlLink(String link, String description,String csetting,int resolveit,String modalLabel) {
		output=finalResult(link,description,csetting,resolveit,modalLabel)
		return output
	}
		
	/*
	 * Wrapper for above services
	 */
	String finalResult(String link, String description,String csetting,int resolveit,String modalLabel) {
		def newLink1=link
		if (resolveit==1) {
			DnsService ds=new DnsService()
			newLink1=ds.resolveUrlHost(link)
		}
		if ( (csetting.equals('')) && ( csetting==null)) { csetting="_same" }
		if (csetting.equals("_new")) {
			output="<a href='"+newLink1+"' target='_newwindow'>"+description+"</a>"
		} else if (csetting.equals("_same")) {
			output="<a href='"+newLink1+"'>"+description+"</a>"
		} else if (csetting.equals("_modal")) {
			output="<a data-toggle='modal' class='btn' href='"+newLink1+"' data-target='#myModal'>"+description+"</a>"
		}else {
			output ="<ul id='user_spot'>"
			output +="<li><a data-toggle='modal' class='btn' href='"+newLink1+"' data-target='#myModal'>"+description+"</a>"
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
	 * Return Session information to this service
	 */
	private HttpSession getSession() {
		return RequestContextHolder.currentRequestAttributes().getSession()
	}
}
	
