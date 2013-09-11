package extlinkchoice

class ExtlinkchoiceController {
	def extLinkChoiceService
    	def linkchooser() {
				if (params.id) {
					def defaultchoice='_same'
					if (params.id.equals('Modal Popup')) { defaultchoice='_modal' }
					if (params.id.equals('New Window')) { defaultchoice='_new' }
					if (params.id.equals('Multiple Choice')) { defaultchoice='_multi' }
					session.linkchoice=defaultchoice
					redirect(url: request.getHeader('referer'))
				}
		}	
}
