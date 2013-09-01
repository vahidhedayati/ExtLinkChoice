package extlinkchoice

/*
 * This service provides Various DNS Lookups which can be useful for any of your other DNS requirements
 * resolveUrlHost strips/resolves a local DNS name and returns the full name within url
 * useful if the links are all internally resolved names lets say site access via vpn
 */

class DnsService {
	
	
			/*
			* resolveUrlHost:  extracts the hostname from within a given url, it then recreates the link with fully qualified domain name
			* Useful if you have local urls such as http://server1/mylink
			* This function will try to resolve server1 against DNS if found it will return:
			* http://servers.yourdomain.com/mylink
			*/
			String resolveUrlHost(String url) {
				String newhost=""
				String linkrest="";
				def protocol=""
				
				if (url.indexOf('https://')>-1) {
					protocol="https://"
					def host1=url.substring(url.indexOf('https://')+8,url.length())
					if (host1.indexOf('/')>-1) {
						newhost=host1.substring(0,host1.indexOf('/'))
						linkrest=host1.substring(host1.indexOf('/'),host1.length())
					}else {
						newhost=host1
					}
					
				}else if (url.indexOf('http://')>-1) {
					protocol="http://"
					def host1=url.substring(url.indexOf('http://')+7,url.length())
					if (host1.indexOf('/')>-1) {
						newhost=host1.substring(0,host1.indexOf('/'))
						linkrest=host1.substring(host1.indexOf('/'),host1.length())
					}else{
						newhost=host1
					}
				}
				def newLink
				if (linkrest.startsWith("/")) { } else { linkrest="/"+linkrest }
				try {
					def inetAddress = InetAddress.getByName(newhost);
					def fqdn=inetAddress.getCanonicalHostName()
					newLink=protocol+fqdn+linkrest
				}catch(Exception e) {
					
					 newLink=protocol+newhost+linkrest
				}
				return newLink
			}
	
			
			
	
			/*
			* resolveHost:simple function to resolve a given name
			*/
			String resolveHost(String host) {
				def inetAddress = InetAddress.getByName(host)
				def fqdn=inetAddress.getCanonicalHostName()
				return fqdn
			}
	
	
			
			/*
			* returnHostName: function to return actual hostname of a given ip/full hostname
			* might come in handy
			*/
			String returnHostName(String host) {
			  def inetAddress = InetAddress.getByName(host)
			  def hname=inetAddress.getHostName()
			  return hname
			}
	
	
			/*
			* returnHostIp: function to return resolvable IP for a given hostname/dns name
			*/
			String returnHostIp(String host) {
			  def inetAddress = InetAddress.getByName(host)
			  def hip=inetAddress.getHostAddress()
			  return hip
			}
}
