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
		URL aURL = new URL(url)
		String protocol = aURL.protocol ?: 'http'
		String authority = aURL.authority ?: ''
		String host = aURL.host ?: ''
		String port = aURL.port ?: ''
		String path = aURL.path ?: ''
		String query = aURL.query ?: ''
		String filename = aURL.file ?: ''
		String ref = aURL.ref ?: ''
		if (port && (port == ':-1')) {
			port="${port}"
		}else{
			port=''
		}
		return protocol+"://"+resolveHost(host)+port+filename+ref
	}

	/*
	 * resolveHost:simple function to resolve a given name
	 */
	String resolveHost(String host) {
		def result=host
		try {
			def inetAddress = InetAddress?.getByName(host)
			if (inetAddress) {
				def fqdn=inetAddress?.getCanonicalHostName()
				if  (fqdn) {
					result=fqdn
				}
			}
		}catch(Exception e) {
			log.error "Error occured resolveHost "+e
		}
		return result
	}



	/*
	 * returnHostName: function to return actual hostname of a given ip/full hostname
	 * might come in handy
	 */
	String returnHostName(String host) {
		def result=host
		try {
			def inetAddress = InetAddress?.getByName(host)
			if (inetAddress) {
				def hname=inetAddress?.getHostName()
				if  (hname) {
					result=hname
				}
			}
		}catch(Exception e) {
			log.error "Error occured resolveHost "+e
		}
		return result
	}


	/*
	 * returnHostIp: function to return resolvable IP for a given hostname/dns name
	 */
	String returnHostIp(String host) {
		def result=host
		try {
			def inetAddress = InetAddress?.getByName(host)
			if (inetAddress) {
				def hip=inetAddress?.getHostAddress()
				if  (hip) {
					result=hip
				}
			}
		}catch(Exception e) {
			log.error "Error occured resolveHost "+e
		}
		return result
	}
}
