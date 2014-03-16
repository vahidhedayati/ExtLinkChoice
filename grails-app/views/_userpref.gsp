<g:javascript>
	$(document).ready(function() {
		$('#${update}').hide();
        $('#${updateShow}').show();
		$('#${updateShow}').click(function(){
			$('#${update}').slideToggle();
		});
		
		var appendit='Link Open type:  - (${linkchoice }) - <br/>';
		appendit +='<a href=?linkchoice=_modal>Modal: Pop-up</a><br/>';
		appendit +='<a href=?linkchoice=_same>Same Window</a><br/>';
		appendit +='<a href=?linkchoice=_new>New Window</a><br/>';
		appendit +='<a href=?linkchoice=_multi>Multiple Choice</a><br/>';
		$('#${update}').html(appendit);
	});
</g:javascript>