<div class='modal fade' id="${attrs.id }" tabindex='-1' role='dialog' aria-labelledby="${attrs.modalLabel }" aria-hidden='true'>
  <div class="modal-dialog" style="width:100%;height:100%; ">
      <div class="modal-content">
	<div class='modal-header'>
		<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>
		<div id='myModalLabel'><h2>${attrs?.title}</h2><h3>${attrs?.description}</h3></div>
	</div>
	<div class="modal-body" style="width:100%;height:100%; ">

	</div>
	</div>
	</div>
</div>
		
			
<g:javascript>
	$('a.btn').on('click', function(e) {
		e.preventDefault();
		var url = $(this).attr('href');
	 
 		$(".modal").css('position','fixed');
  		$(".modal").css('top','0');
  		$(".modal").css('margin-top','10em');
  		$(".modal").css('left','auto');
  		$(".modal").css('right','auto');
		$(".modal").css('margin-right','auto');
		$(".modal").css('height',$( window ).height()*0.6);
		$(".modal").css('width',$( window ).width()*0.6);
		$(".modal-body").css('height',$( window ).height()*0.5);
		$(".modal-body").css('width',$( window ).width()*0.5);
		$(".modal-body").html('<iframe width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency="true" src="'+url+'"></iframe>');
	});
	
</g:javascript>
